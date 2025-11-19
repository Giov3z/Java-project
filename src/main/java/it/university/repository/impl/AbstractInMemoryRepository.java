package it.university.repository.impl;

import it.university.repository.Repository;

import java.util.*;

/**
 * Implementazione generica in-memory basata su Map.
 * Le sottoclassi devono definire come ottenere la chiave dall'entità.
 */
public abstract class AbstractInMemoryRepository<I, T> implements Repository<I, T> {
    private final Map<I, T> store = new HashMap<>();

    @Override
    public void save(T entity) {
        store.put(extractId(entity), entity);
    }

    @Override
    public Optional<T> findById(I id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(store.values());
    }

    protected abstract I extractId(T entity);
}
