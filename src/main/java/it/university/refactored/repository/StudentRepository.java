package it.university.refactored.repository;

import it.university.model.Student;
import java.util.*;

/**
 * Copia refactorizzata del repository studenti.
 * Implementazione in-memory semplice: Map id -> Student.
 * Nota: questa classe è duplicata rispetto all'originale ma si trova in un package diverso
 * per permettere il confronto tra le due versioni senza interferire.
 */
public class StudentRepository implements Repository<Student> {
    private final Map<Integer, Student> store = new HashMap<>();

    @Override
    public void save(Student s) {
        store.put(s.getId(), s);
    }

    public Student findById(int id) {
        return store.get(id);
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(store.values());
    }
}
