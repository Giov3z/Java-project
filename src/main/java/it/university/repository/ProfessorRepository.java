package it.university.repository;

import it.university.model.Professor;
import java.util.*;

public class ProfessorRepository {
    private Map<Integer, Professor> data = new HashMap<>();

    public void save(Professor p) { data.put(p.getId(), p); }
    public Professor findById(int id) { return data.get(id); }
    public List<Professor> findAll() { return new ArrayList<>(data.values()); }
}
