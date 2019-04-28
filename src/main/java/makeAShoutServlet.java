
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import ai.ArtificialIntelligence;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;

public class makeAShoutServlet extends HttpServlet {
    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString;
        ArtificialIntelligence artificialIntelligence = new ArtificialIntelligence();
        Cell cell = mapper.readValue(httpServletRequest.getReader(), Cell.class);
        System.out.println("cell state = " + cell.getState());
        System.out.println("cell x = " + cell.getX());
        System.out.println("cell y = " + cell.getY());

        // Здесь применить метод реагирования на выстрел

        Entity result = artificialIntelligence.checkTheResultOfPlayerShout(cell);

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