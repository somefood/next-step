package core.mvc.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JsonView implements View {

    @Override
    public void render(HttpServletRequest request, HttpServletResponse response) throws Exception {
    }
}
