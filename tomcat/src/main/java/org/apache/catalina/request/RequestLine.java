package org.apache.catalina.request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.catalina.http.HttpMethod;

public class RequestLine {

    private final HttpMethod httpMethod;
    private final String path;
    private final String versionOfProtocol;
    private final QueuyParam queryParams;

    public RequestLine(String headerLines) {
        String[] requestLineEntries = headerLines.split(" ");

        this.httpMethod = HttpMethod.valueOf(requestLineEntries[0]);
        this.queryParams = new QueuyParam(requestLineEntries[1]);
        this.path = mapPath(requestLineEntries[1]);
        this.versionOfProtocol = requestLineEntries[2];
    }


    private String mapPath(String path) {
        if (queryParams.hasQueryParam()) {
            int queryStringIndex = path.indexOf("?");
            return path.substring(0, queryStringIndex);
        }
        return path;
    }

    public boolean isMethod(HttpMethod httpMethod) {
        return this.httpMethod == httpMethod;
    }

    public String getContentType() {
        try {
            return Files.probeContentType(new File(path).toPath());
        } catch (IOException e) {
            throw new IllegalArgumentException("Not found path");
        }
    }

    public boolean isStaticRequest() {
        try {
            return Files.probeContentType(Paths.get(path)) != null;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean hasQueryParam() {
        return queryParams.hasQueryParam();
    }

    public String getQueryParam(String paramName) {
        return queryParams.getQueryParam(paramName);
    }

    public String getPath() {
        return path;
    }
}
