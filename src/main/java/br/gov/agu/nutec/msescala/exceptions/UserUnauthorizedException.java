package br.gov.agu.nutec.msescala.exceptions;

public class UserUnauthorizedException extends RuntimeException {

    public UserUnauthorizedException(String message) {
        super(message);
    }
}
