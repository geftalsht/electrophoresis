package org.gefsu.http;

public enum MimeType {
    PLAINTEXT, HTML, CSS, JSON, JPEG, PNG, ARBITRARY;

    @Override
    public String toString() {
        return (
            switch (this) {
                case PLAINTEXT -> "text/plain";
                case HTML -> "text/html";
                case CSS -> "text/css";
                case JSON -> "text/json";
                case JPEG -> "text/jpeg";
                case PNG -> "text/png";
                case ARBITRARY -> "application/octet-stream";
            });
    }
}
