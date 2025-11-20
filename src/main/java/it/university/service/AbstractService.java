package it.university.service;

import it.university.common.EmptyResultException;
import it.university.repository.Repository;

import java.util.List;

/**
 * Service astratto con logica condivisa: dipendenza da Repository e gestione EmptyResult.
 */
public abstract class AbstractService<I, T> {
    private final Repository<I, T> repository;

    protected AbstractService(Repository<I, T> repository) {
        this.repository = repository;
    }

    protected Repository<I, T> getRepository() {
        return repository;
    }

    public void save(T entity) {
        repository.save(entity);
    }

    public List<T> list() {
        List<T> all = repository.findAll();
        if (all.isEmpty()) {
            throw new EmptyResultException(emptyMessage());
        }
        return all;
    }

    protected abstract String emptyMessage();
}
