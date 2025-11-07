package it.university.refactored;

/**
 * Eccezione lanciata quando una query di servizio ritorna una collezione vuota.
 *
 * Scopo: separare la logica di dominio dal modo in cui l'app comunica "nessun risultato".
 * I servizi refactorizzati lanciano questa eccezione; il Main (o il caller) la cattura
 * e decide come presentare l'informazione all'utente.
 */
public class EmptyResultException extends RuntimeException {
    public EmptyResultException(String message) {
        super(message);
    }
}
