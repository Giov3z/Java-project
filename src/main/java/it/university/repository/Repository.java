package it.university.repository;

import java.util.List;

/**
 * Contract minimo per repository in-memory.
 *
 * Per semplicità espone solo le operazioni usate nell'esercizio: save e findAll.
 * Questo consente ai service di dipendere dall'interfaccia e non dall'implementazione concreta.
 */
public interface Repository<T> {
    void save(T t);
    List<T> findAll();
}