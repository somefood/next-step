package http;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private String url;
    private String method;
    private String path;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> parameters;

    public HttpRequest(InputStream in) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        String line = br.readLine();
        log.info("첫 번째 line: {}", line);

        if (line == null) {
            return;
        }

        String[] requestLine = line.split(" ");
        setMethod(requestLine);
        log.debug("method: {}", this.method);

        setUrl(requestLine);
        log.debug("url: {}", this.url);

        setPath();
        log.debug("path: {}", this.path);

        setHeaders(line, br);
        setParameter(br);
    }

    private void setUrl(String[] requestLine) {
        this.url = requestLine[1];
    }

    private void setMethod(String[] requestLine) {
        this.method = requestLine[0];
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return this.path;
    }

    public String getHeader(String headerName) {
        return this.headers.get(headerName);
    }

    public String getParameter(String parameterName) {
        return this.parameters.get(parameterName);
    }

    private void setPath() {
        if (this.method.equalsIgnoreCase("GET")) {
            this.path = this.url.substring(0, this.url.indexOf("?"));
        }

        if (this.method.equalsIgnoreCase("POST")) {
            this.path = this.url;
        }
    }

    private void setHeaders(String line, BufferedReader br) throws IOException {
        while (!line.equals("")) {
            log.debug("header : {}", line);
            line = br.readLine();
            if (Strings.isNullOrEmpty(line)) {
                return;
            }
            String[] headerTokens = line.split(":");
            this.headers.put(headerTokens[0], headerTokens[1].trim());
        }
    }

    public void setParameter(BufferedReader br) throws IOException {
        if (this.method.equals("GET")) {
            String queryString = this.url.substring(this.url.indexOf("?") + 1);
            this.parameters = HttpRequestUtils.parseQueryString(queryString);
        }

        if (this.method.equals("POST")) {
            String contentLength = this.headers.get("Content-Length");
            String requestBody = IOUtils.readData(br, Integer.parseInt(contentLength));
            this.parameters = HttpRequestUtils.parseQueryString(requestBody);
        }
    }
}
