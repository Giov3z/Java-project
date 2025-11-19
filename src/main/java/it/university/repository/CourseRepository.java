package it.university.repository;

import it.university.model.Course;
import it.university.repository.impl.AbstractInMemoryRepository;

import java.util.Optional;

public class CourseRepository extends AbstractInMemoryRepository<Integer, Course> {
    @Override
    protected Integer extractId(Course entity) {
        return entity.getId();
    }

    @Override
    public Optional<Course> findById(Integer id) {
        return super.findById(id);
    }
}
