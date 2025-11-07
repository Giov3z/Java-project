package it.university.refactored.repository;

import it.university.model.Course;
import java.util.*;

/** Refactored Course repository (in-memory). */
public class CourseRepository implements Repository<Course> {
    private final Map<Integer, Course> store = new HashMap<>();

    @Override
    public void save(Course c) {
        store.put(c.getId(), c);
    }

    public Course findById(int id) {
        return store.get(id);
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(store.values());
    }
}
