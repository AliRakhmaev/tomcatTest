import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import ai.ArtificialIntelligence;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;

public class AIStrikesBackServlet extends HttpServlet{
    /**
     * Ничего не принимаем, нам говорят сделать выстрел, мы по стратегиям ищем наилучший вариант и отправляем координаты выстрела как ответ
     */
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ArtificialIntelligence artificialIntelligence;
        String jsonString;

        // получаем параметр id
        String userId = httpServletRequest.getParameter("userId");

        // Проверяем, новый это игрок или существующий и применяем соответсвующие действия
        if(ArtificialIntelligence.hashMapOfUsers.containsKey(userId)){ // игрок нам знаком, он будет взаимодейстовать со своим ИИ
            artificialIntelligence = ArtificialIntelligence.hashMapOfUsers.get(userId);
        }
        else{                                                                       // игрок новый, создаём новый объект ИИ и добавляем новый элемент в hashMap
            artificialIntelligence = new ArtificialIntelligence();
            ArtificialIntelligence.hashMapOfUsers.put(userId, artificialIntelligence);
        }

        Cell result = artificialIntelligence.makeAShoutAtPlayerShips();
        jsonString = mapper.writeValueAsString(result);

        httpServletResponse.setContentType("application/json");

        PrintWriter writer = httpServletResponse.getWriter();
        try {
            writer.println(jsonString);
        } finally {
            writer.close();
        }
    }

    /**
     * Принимаем результат нашего выстрела. Гарантированно придёт ячейка, так как в случае уничтожения корабля ответ придёт на другой сервлет
     * Ответ на данный запрос не предустмотрен
     */
    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString;
        ArtificialIntelligence artificialIntelligence;
        CellDTO cellDTO = mapper.readValue(httpServletRequest.getReader(), CellDTO.class);

        // Проверяем, новый это игрок или существующий и применяем соответсвующие действия
        if(ArtificialIntelligence.hashMapOfUsers.containsKey(cellDTO.getUserId())){ // игрок нам знаком, он будет взаимодейстовать со своим ИИ
            artificialIntelligence = ArtificialIntelligence.hashMapOfUsers.get(cellDTO.getUserId());
        }
        else{                                                                       // игрок новый, создаём новый объект ИИ и добавляем новый элемент в hashMap
            artificialIntelligence = new ArtificialIntelligence();
            ArtificialIntelligence.hashMapOfUsers.put(cellDTO.getUserId(), artificialIntelligence);
        }

        // Преобразуем DTO в нормальную клетку
        Cell cell = new Cell(cellDTO.getState(), cellDTO.getX(),cellDTO.getY());

        artificialIntelligence.analyseTheResultOfAIShoutAtUserShips(cell);
    }
}
