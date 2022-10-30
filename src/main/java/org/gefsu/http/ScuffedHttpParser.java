package org.gefsu.http;

import java.io.IOException;
import java.io.InputStream;

// Scuffed HTTP Parser 0.1.0
// At this moment it only parses the start line of an HTTP message
// and ignores everything else (every header and body)
public class ScuffedHttpParser {

    public static ScuffedHttpRequest parseRequest(InputStream is)
        throws IOException {

        var startLine = parseStartLine(is);

        var method = parseMethod(startLine);
        var resource = parseResource(startLine);

        return new ScuffedHttpRequest(method, resource);
    }

    private static String parseStartLine(InputStream is)
        throws IOException {

        int b;
        var sb = new StringBuilder();

        while ((b = is.read()) >= 0) {
            if (b == '\r') {
                int next = is.read();
                if (next < 0 || next == '\n')
                    break;
                else {
                    throw new RuntimeException("Balls");
                }
            } else if (b == '\n') {
                throw new RuntimeException("Balls");
            } else {
                char c = (char) b;
                if (charIsAllowed(c))
                    sb.append(c);
                else
                    throw new RuntimeException("Balls");
            }
        }
        return sb.toString();
    }

    private static boolean charIsAllowed(char c) {
        return (c >= 0x28 && c <= 0x7D) || c == 0x20;
    }

    private static ScuffedHttpMethod parseMethod(String startLine) {
        var firstSpace = startLine.indexOf(' ');

        return
            switch (startLine.substring(0, firstSpace)) {
                case "GET" -> ScuffedHttpMethod.GET;
                case "HEAD" -> ScuffedHttpMethod.HEAD;
                case "POST" -> ScuffedHttpMethod.POST;
                case "PUT" -> ScuffedHttpMethod.PUT;
                case "DELETE" -> ScuffedHttpMethod.DELETE;
                case "CONNECT" -> ScuffedHttpMethod.CONNECT;
                case "OPTIONS" -> ScuffedHttpMethod.OPTIONS;
                case "TRACE" -> ScuffedHttpMethod.TRACE;
                default -> throw new RuntimeException("Balls");
            };
    }

    private static String parseResource(String startLine) {
        var firstSpace = startLine.indexOf(' ');
        var lastSpace = startLine.lastIndexOf(' ');

        return startLine.substring(firstSpace+1, lastSpace);
    }

}
