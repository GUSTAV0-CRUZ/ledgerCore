package dev.cruzs.gustavo.service_bancary.history.domain.exceptions;

public class NotFoundHistoryException extends RuntimeException {
    public NotFoundHistoryException(String message) {
        super(message);
    }
}
