package it.university.repository;

import it.university.model.Course;
import java.util.*;

public class CourseRepository {
    private Map<Integer, Course> data = new HashMap<>();

    public void save(Course c) { data.put(c.getId(), c); }
    public Course findById(int id) { return data.get(id); }
    public List<Course> findAll() { return new ArrayList<>(data.values()); }
}
