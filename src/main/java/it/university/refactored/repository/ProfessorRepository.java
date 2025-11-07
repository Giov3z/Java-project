package it.university.refactored.repository;

import it.university.model.Professor;
import java.util.*;

/** Refactored Professor repository (in-memory). */
public class ProfessorRepository implements Repository<Professor> {
    private final Map<Integer, Professor> store = new HashMap<>();

    @Override
    public void save(Professor p) {
        store.put(p.getId(), p);
    }

    public Professor findById(int id) {
        return store.get(id);
    }

    @Override
    public List<Professor> findAll() {
        return new ArrayList<>(store.values());
    }
}
