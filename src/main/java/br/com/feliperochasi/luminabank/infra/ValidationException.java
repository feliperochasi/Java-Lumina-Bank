package br.com.feliperochasi.luminabank.infra;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
