package it.university.repository;

import it.university.model.Classroom;
import java.util.*;

public class ClassroomRepository {
    private Map<String, Classroom> data = new HashMap<>();

    public void save(Classroom c) { data.put(c.getCode(), c); }
    public Classroom findByCode(String code) { return data.get(code); }
    public List<Classroom> findAll() { return new ArrayList<>(data.values()); }
}
