package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            String line = br.readLine();
            log.debug("request line : {}", line);

            if (line == null) {
                return;
            }

            String[] tokens = line.split(" ");

            while (!line.equals("")) {
                line = br.readLine();
                log.debug("header : {}", line);
            }

            DataOutputStream dos = new DataOutputStream(out);
            byte[] body = Files.readAllBytes(new File("./webapp" + tokens[1]).toPath());
            response200Header(dos, body.length);
            responseBody(dos, body);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void setRequestInfoMap(List<String> httpLines) {
        for (int i = 1; i < httpLines.size() - 1; i++) {
            String text = httpLines.get(i);
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
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
