package controller;

import http.HttpRequest;
import http.HttpResponse;

public abstract class AbstractController implements Controller {

    abstract void doGet(HttpRequest request, HttpResponse response);

    abstract void doPost(HttpRequest request, HttpResponse response);
}
