package org.gefsu.http.exception;

public class BadRequestException extends Exception {

    @Override
    public String toString() {
        return "Invalid HTTP request!";
    }

}
