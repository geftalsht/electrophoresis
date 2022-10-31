package org.gefsu.http.exception;

public class NotFoundException extends Exception {

    @Override
    public String toString() {
        return "Resource not found!";
    }

}
