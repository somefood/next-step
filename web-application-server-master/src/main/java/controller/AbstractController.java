package controller;

import http.HttpRequest;
import http.HttpResponse;

import java.io.IOException;

public abstract class AbstractController implements Controller {

    abstract void doGet(HttpRequest request, HttpResponse response) throws IOException;

    abstract void doPost(HttpRequest request, HttpResponse response) throws IOException;
}
