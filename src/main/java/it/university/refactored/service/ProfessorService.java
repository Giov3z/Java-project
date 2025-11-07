package it.university.refactored.service;

import it.university.model.Professor;
import it.university.refactored.repository.Repository;
import it.university.refactored.EmptyResultException;
import java.util.List;

/** Refactored Professor service (constructor injection + empty-result exception). */
public class ProfessorService {
    private final Repository<Professor> repository;

    public ProfessorService(Repository<Professor> repository) {
        this.repository = repository;
    }

    public void add(Professor p) { repository.save(p); }

    public List<Professor> list() {
        List<Professor> all = repository.findAll();
        if (all.isEmpty()) throw new EmptyResultException("Nessun professore presente");
        return all;
    }
}
