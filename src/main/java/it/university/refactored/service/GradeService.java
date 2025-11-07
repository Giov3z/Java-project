package it.university.refactored.service;

import it.university.model.Grade;
import it.university.refactored.repository.Repository;
import it.university.refactored.EmptyResultException;
import java.util.List;

/** Refactored Grade service. */
public class GradeService {
    private final Repository<Grade> repository;

    public GradeService(Repository<Grade> repository) {
        this.repository = repository;
    }

    public void add(Grade g) { repository.save(g); }

    public List<Grade> list() {
        List<Grade> all = repository.findAll();
        if (all.isEmpty()) throw new EmptyResultException("Nessun voto presente");
        return all;
    }
}
