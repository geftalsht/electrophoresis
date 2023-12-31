package org.gefsu.http;

import java.io.InputStream;
import java.util.Optional;

import static org.gefsu.util.OptionalUtils.lift;

// Scuffed HTTP Parser 0.1.0
// At this moment it only parses the start line of an HTTP message
// and ignores everything else (every header and body)
public class HttpParser {
    public static Optional<HttpRequest> parseRequest(InputStream is) {
        return lift(() -> {
            var startLine = parseStartLine(is);
            var method = parseMethod(startLine);
            var resource = parseResource(startLine);

            return new HttpRequest(method, resource);
        });
    }

    private static String parseStartLine(InputStream is)
        throws Exception {

        int b;
        var sb = new StringBuilder();

        while ((b = is.read()) >= 0) {
            if (b == '\r') {
                int next = is.read();
                if (next < 0 || next == '\n')
                    break;
                else {
                    throw new Exception();
                }
            } else if (b == '\n') {
                throw new Exception();
            } else {
                char c = (char) b;
                if (charIsAllowed(c))
                    sb.append(c);
                else
                    throw new Exception();
            }
        }
        return sb.toString();
    }

    private static boolean charIsAllowed(char c) {
        return (c >= 0x28 && c <= 0x7D) || c == 0x20;
    }

    private static HttpMethod parseMethod(String startLine)
        throws Exception {

       var firstSpace = startLine.indexOf(' ');

       return switch (startLine.substring(0, firstSpace)) {
           case "GET" -> HttpMethod.GET;
           case "HEAD" -> HttpMethod.HEAD;
           case "POST" -> HttpMethod.POST;
           case "PUT" -> HttpMethod.PUT;
           case "DELETE" -> HttpMethod.DELETE;
           case "CONNECT" -> HttpMethod.CONNECT;
           case "OPTIONS" -> HttpMethod.OPTIONS;
           case "TRACE" -> HttpMethod.TRACE;
           default -> throw new Exception();
       };
    }

    private static String parseResource(String startLine) {
        var firstSpace = startLine.indexOf(' ');
        var lastSpace = startLine.lastIndexOf(' ');
        return startLine.substring(firstSpace+1, lastSpace);
    }
}
