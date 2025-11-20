package it.university.common;

/**
 * Eccezione lanciata quando una query non produce risultati.
 * I servizi la usano per demandare al caller la gestione delle "empty response".
 */
public class EmptyResultException extends RuntimeException {
    public EmptyResultException(String message) {
        super(message);
    }
}
