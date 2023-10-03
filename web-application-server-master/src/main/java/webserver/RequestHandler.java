package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.*;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    private Map<String, String> httpRequestMap = new HashMap<>();

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
            System.out.println(httpLines);
            String httpRequestInfo = getHttpRequestInfo(httpLines);
            setRequestInfoMap(httpLines);
            System.out.println(httpRequestMap);

            String[] infos = httpRequestInfo.split(" ");

            String httpMethod = infos[0];
            String fullUrl = infos[1];
            String url = getUrl(fullUrl);

            if (httpMethod.equalsIgnoreCase("GET")) {

                if (url.startsWith("/user/list")) {

                    DataOutputStream dos = new DataOutputStream(out);

                    Map<String, String> cookie = HttpRequestUtils.parseCookies(httpRequestMap.get("Cookie"));

                    if (Boolean.parseBoolean(cookie.getOrDefault("logined", "false"))) {
                        StringBuilder stringBuilder = new StringBuilder();

                        stringBuilder.append("<ll>");
                        Collection<User> users = DataBase.findAll();
                        for (User user : users) {
                            stringBuilder.append("<ul>");
                            stringBuilder.append(user.getUserId());
                            stringBuilder.append("</ul>");
                        }
                        stringBuilder.append("</li>");

                        byte[] body = stringBuilder.toString().getBytes();
                        response200Header(dos, body.length);
                        responseBody(dos, body);
                        return;
                    } else {
                        byte[] body = "<script>window.location.href='http://localhost:8080/index.html'</script>".getBytes();
                        response200Header(dos, body.length);
                        responseBody(dos, body);
                        return;
                    }
                }

                byte[] body = getBody(url) != null ? getBody(url) : "Hello World".getBytes();

                DataOutputStream dos = new DataOutputStream(out);
                response200Header(dos, body.length);
                responseBody(dos, body);
                return;
            }


            if (httpMethod.equalsIgnoreCase("POST") && url.startsWith("/user/create")) {
                int contentLength = getContentLength();
                String requestBody = IOUtils.readData(bufferedReader, contentLength);
                Map<String, String> queryString = HttpRequestUtils.parseQueryString(requestBody);
                System.out.println(queryString);

                if (queryString.size() > 0) {
                    String userId = queryString.get("userId");
                    String password = queryString.get("password");
                    String name = queryString.get("name");
                    String email = queryString.get("email");
                    User user = new User(
                            userId,
                            password,
                            name,
                            email
                    );
                    System.out.println(user);
                    DataBase.addUser(user);
                }

                byte[] body = new byte[0];

                DataOutputStream dos = new DataOutputStream(out);
                response302Header(dos, "http://localhost:8080/index.html", null);
                responseBody(dos, body);
                return;
            }

            if (httpMethod.equalsIgnoreCase("POST") && url.startsWith("/user/login")) {
                int contentLength = getContentLength();
                String requestBody = IOUtils.readData(bufferedReader, contentLength);
                Map<String, String> queryString = HttpRequestUtils.parseQueryString(requestBody);
                DataOutputStream dos = new DataOutputStream(out);

                byte[] body;

                String userId = queryString.get("userId");
                String password = queryString.get("password");

                User findUser = DataBase.findUserById(userId);

                if (!findUser.getPassword().equals(password)) {
                    body = "<script>window.location.href='http://localhost:8080/user/login_failed.html'</script>".getBytes();
                    response200Header(
                            dos,
                            body.length,
                            "Set-Cookie: logined=false"
                    );
                    responseBody(dos, body);
                    return;
                }

                body = "<script>window.location.href='http://localhost:8080/index.html'</script>".getBytes();
                response200Header(
                        dos,
                        body.length,
                        "Set-Cookie: logined=true"
                );
                responseBody(dos, body);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void setRequestInfoMap(List<String> httpLines) {
        for (int i = 1; i < httpLines.size() - 1; i++) {
            String text = httpLines.get(i);
            System.out.println("text = " + text);
            String[] tokens = text.split(": ");
            httpRequestMap.put(tokens[0], tokens[1]);
        }
    }

    private int getContentLength() {
        return Integer.parseInt(httpRequestMap.get("Content-Length"));
    }

    private static byte[] getBody(String url) throws IOException {
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

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String... headers) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            for (String header : headers) {
                dos.writeBytes(header + "\r\n");
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location, String... headers) {
        try {
            dos.writeBytes("HTTP/1.1 302 Found \r\n");
            dos.writeBytes("Location: "+ location + "\r\n");

            for (String header : headers) {
                dos.writeBytes(header + "\r\n");
            }

            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.writeBytes(httpRequestMap.get("Accept") + "\r\n");
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
