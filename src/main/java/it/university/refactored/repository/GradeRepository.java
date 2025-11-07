package it.university.refactored.repository;

import it.university.model.Grade;
import java.util.*;

/** Refactored Grade repository (in-memory). */
public class GradeRepository implements Repository<Grade> {
    private final List<Grade> store = new ArrayList<>();

    @Override
    public void save(Grade g) {
        store.add(g);
    }

    @Override
    public List<Grade> findAll() {
        return new ArrayList<>(store);
    }
}
