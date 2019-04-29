import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("The game is end");
            ArtificialIntelligence.hashMapOfUsers.remove(shipDTO.getUserId());
        }
        else { // Игрок ещё держится, но здоровье у его флотилии уже не то)
            // Преобразуем DTO в нормальную клетку
            List<Cell> cellsForThisShip = new ArrayList<>();

            for(int i = 0; i < shipDTO.getCells().size(); i++){
                cellsForThisShip.add(new Cell(shipDTO.getCells().get(i).getState(), shipDTO.getCells().get(i).getX(), shipDTO.getCells().get(i).getY()));
            }

            Ship ship = new Ship(cellsForThisShip, shipDTO.getState());
            artificialIntelligence.analyseTheResultOfAIShoutAtUserShips(ship);
        }
    }
}
