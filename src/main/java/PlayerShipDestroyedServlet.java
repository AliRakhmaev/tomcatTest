import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import ai.ArtificialIntelligence;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;

public class PlayerShipDestroyedServlet extends HttpServlet{
    /**
     * Принимаем результат нашего выстрела. Гарантированно придёт корабль, так как в случае попадания/промаха по ячейке ответ придёт на другой сервлет
     * Ответ на данный запрос не предустмотрен
     */
    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString;
        ArtificialIntelligence artificialIntelligence;
        ShipDTO shipDTO = mapper.readValue(httpServletRequest.getReader(), ShipDTO.class);

        // Проверяем, новый это игрок или существующий и применяем соответсвующие действия
        if(ArtificialIntelligence.hashMapOfUsers.containsKey(shipDTO.getUserId())){ // игрок нам знаком, он будет взаимодейстовать со своим ИИ
            artificialIntelligence = ArtificialIntelligence.hashMapOfUsers.get(shipDTO.getUserId());
        }
        else{                                                                       // игрок новый, создаём новый объект ИИ и добавляем новый элемент в hashMap
            artificialIntelligence = new ArtificialIntelligence();
            ArtificialIntelligence.hashMapOfUsers.put(shipDTO.getUserId(), artificialIntelligence);
        }

        if(shipDTO.getState() == 2){ // Ждёмс от кожаного признания нашего превосходства). Просто удаляем эту игру
            ArtificialIntelligence.hashMapOfUsers.remove(shipDTO.getUserId());
        }
        else { // Игрок ещё держится, но здоровье у его флотилии уже не то)
            // Преобразуем DTO в нормальную клетку
            Ship ship = new Ship(shipDTO.getCells(), shipDTO.getState());
            artificialIntelligence.analyseTheResultOfAIShoutAtUserShips(ship);
        }
    }
}
