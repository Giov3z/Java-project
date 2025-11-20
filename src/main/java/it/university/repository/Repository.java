package it.university.repository;

import java.util.List;
import java.util.Optional;

/**
 * Contratto minimo per repository di dominio.
 *
 * @param <I> tipo dell'identificativo
 * @param <T> tipo dell'entità
 */
public interface Repository<I, T> {
    void save(T entity);
    Optional<T> findById(I id);
    List<T> findAll();
}
