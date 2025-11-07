package it.university.refactored.repository;

import it.university.model.Enrollment;
import java.util.*;

/** Refactored Enrollment repository (in-memory). */
public class EnrollmentRepository implements Repository<Enrollment> {
    private final List<Enrollment> store = new ArrayList<>();

    @Override
    public void save(Enrollment e) {
        store.add(e);
    }

    @Override
    public List<Enrollment> findAll() {
        return new ArrayList<>(store);
    }
}
