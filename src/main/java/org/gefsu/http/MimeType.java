package org.gefsu.http;

public enum MimeType {
    PLAINTEXT, HTML, CSS, JS, JSON, JPEG, PNG, BINARY;

    @Override
    public String toString() {
        return
            switch (this) {
                case PLAINTEXT -> "text/plain";
                case HTML -> "text/html";
                case CSS -> "text/css";
                case JS -> "text/javascript";
                case JSON -> "text/json";
                case JPEG -> "image/jpeg";
                case PNG -> "image/png";
                case BINARY -> "application/octet-stream";
        };
    }
}
