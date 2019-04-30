
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import ai.ArtificialIntelligence;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;

public class MakeAShoutServlet extends HttpServlet {
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

        Entity result = artificialIntelligence.checkTheResultOfPlayerShout(cell, cellDTO.getUserId());

        // Проверяем что же нам выдал метод. Аль ячейка аль кораблик
        if(result instanceof Cell){
            jsonString = mapper.writeValueAsString((Cell)result);
        }
        else{
            jsonString = mapper.writeValueAsString((Ship)result);
        }

        httpServletResponse.setContentType("application/json");

        PrintWriter writer = httpServletResponse.getWriter();
        try {
            writer.println(jsonString);
        } finally {
            writer.close();
        }
    }
}