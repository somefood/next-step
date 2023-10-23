package core.mvc.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.Model;
import core.mvc.View;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class JsonView implements View {

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Map<String, Object> maps = model.getMaps();
        Object result = maps.getOrDefault("result", null);

        out.print(objectMapper.writeValueAsString(result));
    }
}
