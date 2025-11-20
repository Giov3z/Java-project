package it.university.repository.impl;

import it.university.model.Enrollment;
import it.university.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository specifico per Enrollment basato su List poiché la chiave è composta.
 */
public class EnrollmentRepositoryImpl implements Repository<String, Enrollment> {
    private final List<Enrollment> store = new ArrayList<>();

    @Override
    public void save(Enrollment entity) {
        if (store.stream().noneMatch(e -> e.getStudentId() == entity.getStudentId() && e.getCourseId() == entity.getCourseId())) {
            store.add(entity);
        }
    }

    @Override
    public Optional<Enrollment> findById(String key) {
        return store.stream()
                .filter(e -> (e.getStudentId() + ":" + e.getCourseId()).equals(key))
                .findFirst();
    }

    @Override
    public List<Enrollment> findAll() {
        return new ArrayList<>(store);
    }

    public boolean exists(int studentId, int courseId) {
        return store.stream().anyMatch(e -> e.getStudentId() == studentId && e.getCourseId() == courseId);
    }
}
