package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import util.HttpRequestUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Map;

public class ListUserController extends AbstractController {

    @Override
    void doGet(HttpRequest request, HttpResponse response) throws IOException {
        String cookieHeader = request.getHeader("Cookie");
        Map<String, String> cookies = HttpRequestUtils.parseCookies(cookieHeader);
        boolean logined = Boolean.parseBoolean(cookies.get("logined"));
        if (!logined) {
            response.sendRedirect("/user/login.html");
            return;
        }

        Collection<User> users = DataBase.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1'>");
        for (User user : users) {
            sb.append("<tr>");
            sb.append("<td>").append(user.getUserId()).append("</td>");
            sb.append("<td>").append(user.getName()).append("</td>");
            sb.append("<td>").append(user.getEmail()).append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");

        response.forward(sb.toString());
    }

    @Override
    void doPost(HttpRequest request, HttpResponse response) {

    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        if (request.getMethod().equalsIgnoreCase("GET")) {
            doGet(request, response);
        } else if (request.getMethod().equalsIgnoreCase("POST")) {
            doPost(request, response);
        }
    }
}
