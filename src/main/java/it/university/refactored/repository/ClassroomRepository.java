package it.university.refactored.repository;

import it.university.model.Classroom;
import java.util.*;

/** Refactored Classroom repository (in-memory). */
public class ClassroomRepository implements Repository<Classroom> {
    private final Map<String, Classroom> store = new LinkedHashMap<>();


    @Override
    public void save(Classroom c) {
        // il modello originale espone getCode(), quindi usiamo quello come chiave
        store.put(c.getCode(), c);
    }

    public Classroom findByRoom(String room) {
        return store.get(room);
    }

    @Override
    public List<Classroom> findAll() {
        return new ArrayList<>(store.values());
    }
}
