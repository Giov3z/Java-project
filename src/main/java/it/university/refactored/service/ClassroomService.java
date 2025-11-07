package it.university.refactored.service;

import it.university.model.Classroom;
import it.university.refactored.repository.Repository;
import it.university.refactored.EmptyResultException;
import java.util.List;

/** Refactored Classroom service. */
public class ClassroomService {
    private final Repository<Classroom> repository;

    public ClassroomService(Repository<Classroom> repository) {
        this.repository = repository;
    }

    public void add(Classroom c) { repository.save(c); }

    public List<Classroom> list() {
        List<Classroom> all = repository.findAll();
        if (all.isEmpty()) throw new EmptyResultException("Nessuna aula presente");
        return all;
    }
}
