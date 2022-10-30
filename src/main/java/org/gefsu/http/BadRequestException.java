package org.gefsu.http;

public class BadRequestException extends Exception {

    @Override
    public String toString() {
        return "Invalid HTTP request!";
    }

}
