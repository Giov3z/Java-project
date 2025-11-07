package it.university.repository;

import it.university.model.Student;
import java.util.*;

public class StudentRepository {
    private Map<Integer, Student> students = new HashMap<>();

    public void save(Student s) { students.put(s.getId(), s); }
    public Student findById(int id) { return students.get(id); }
    public List<Student> findAll() { return new ArrayList<>(students.values()); }
}
