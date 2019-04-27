
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;

public class makeAShoutServlet extends HttpServlet {
    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {

    //    Cell cell = new Cell(Integer.parseInt(httpServletRequest.getParameter("state")), Integer.parseInt(httpServletRequest.getParameter("x")),Integer.parseInt(httpServletRequest.getParameter("y")));

        ObjectMapper mapper = new ObjectMapper();
        Cell cell = mapper.readValue(httpServletRequest.getReader(), Cell.class);
        System.out.println("cell state = " + cell.getState());
        System.out.println("cell x = " + cell.getX());
        System.out.println("cell y = " + cell.getY());

        // Здесь применить метод реагирования на выстрел
        cell.setState(2);
        String jsonString = mapper.writeValueAsString(cell);

        httpServletResponse.setContentType("application/json");

        PrintWriter writer = httpServletResponse.getWriter();
        try {
            writer.println(jsonString);
        } finally {
            writer.close();
        }
    }
}