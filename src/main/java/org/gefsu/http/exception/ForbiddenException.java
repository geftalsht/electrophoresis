package org.gefsu.http.exception;

public class ForbiddenException extends Exception {

    @Override
    public String toString() {
        return "Forbidden!";
    }

}
