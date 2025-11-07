package it.university.refactored.service;

import it.university.model.Enrollment;
import it.university.refactored.repository.Repository;
import it.university.refactored.EmptyResultException;
import java.util.List;

/** Refactored Enrollment service. */
public class EnrollmentService {
    private final Repository<Enrollment> repository;

    public EnrollmentService(Repository<Enrollment> repository) {
        this.repository = repository;
    }

    public void enrollStudent(Enrollment e) { repository.save(e); }

    public List<Enrollment> list() {
        List<Enrollment> all = repository.findAll();
        if (all.isEmpty()) throw new EmptyResultException("Nessuna iscrizione presente");
        return all;
    }
}
