package controller;

import db.DataBase;
import http.HttpRequest;
import http.HttpResponse;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class LoginController extends AbstractController {

    @Override
    void doGet(HttpRequest request, HttpResponse response) throws IOException {

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        User user = DataBase.findUserById(userId);

        if (user == null) {
            response.forward("/user/login_failed.html");
            return;
        }

        if (!user.getPassword().equals(password)) {
            response.forward("/user/login_failed.html");
            return;
        }

        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");
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
