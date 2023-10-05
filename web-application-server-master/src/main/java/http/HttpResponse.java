package http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private final OutputStream os;
    private String httpStatusCode;
    private String httpStatusMessage;
    private Map<String, String> headers = new HashMap<>();


    public HttpResponse(OutputStream os) {
        this.os = os;
    }

    public void forward(String path) throws IOException {
        this.httpStatusCode = "200";
        this.httpStatusMessage = "OK";

        addHeader("Content-Length", String.valueOf(path.length()));
        makeHttp();
        appendBody(path);
    }

    public void sendRedirect(String path) throws IOException {
        this.httpStatusCode = "302";
        this.httpStatusMessage = "Found";

        addHeader("Location", path);

        makeHttp();
    }

    public void addHeader(String headerKey, String headerValue) {
        headers.put(headerKey, headerValue);
    }

    private void makeHttp() throws IOException {
        String responseLine = "HTTP/1.1 " + httpStatusCode + " " + httpStatusMessage + "\r\n";
        String contentType = "Content-Type: text/html\r\n";
        os.write(responseLine.getBytes());
        os.write(contentType.getBytes());
        for (Map.Entry<String, String> header : headers.entrySet()) {
            String key = header.getKey();
            String value = header.getValue();
            String line = key + ": " + value + "\r\n";
            os.write(line.getBytes());
        }
        os.write("\r\n".getBytes());
    }

    private void appendBody(String path) throws IOException {
        os.write(path.getBytes());
    }
}
