package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            List<String> httpLines = extractLine(bufferedReader);
            String httpRequestInfo = getHttpRequestInfo(httpLines);
            String[] infos = httpRequestInfo.split(" ");

            String fullUrl = infos[1];
            String url = getUrl(fullUrl);
            Map<String, String> queryString = HttpRequestUtils.parseQueryString(getQueryString(fullUrl));
            System.out.println(queryString);

            if (queryString.size() > 0) {
                User user = new User(
                        queryString.get("userId"),
                        queryString.get("password"),
                        queryString.get("name"),
                        queryString.get("email")
                );

                System.out.println(user);
            }

            byte[] body = getBody(url) != null ? getBody(url) : "Hello World".getBytes();

            DataOutputStream dos = new DataOutputStream(out);
            response200Header(dos, body.length);
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private static byte[] getBody(String url) throws IOException {
        System.out.println("url = " + url);
        return Files.readAllBytes(new File("./webapp" + url).toPath());
    }

    private static String getUrl(String fullUrl) {
        int delimiterIndex = fullUrl.indexOf("?");
        if (delimiterIndex == -1) {
            return fullUrl;
        }
        return fullUrl.substring(0, delimiterIndex);
    }

    private String getQueryString(String fullUrl) {
        int delimiterIndex = fullUrl.indexOf("?");
        if (delimiterIndex == -1) {
            return null;
        }
        return fullUrl.substring(delimiterIndex + 1);
    }

    private static String getHttpRequestInfo(List<String> httpLines) {
        return httpLines.get(0);
    }

    private List<String> extractLine(BufferedReader bufferedReader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line = null;
        while (!"".equals(line)) {
            line = bufferedReader.readLine();
            lines.add(line);
        }
        return lines;
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
