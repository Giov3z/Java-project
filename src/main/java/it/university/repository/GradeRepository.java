package it.university.repository;

import it.university.model.Grade;
import it.university.repository.impl.AbstractInMemoryRepository;

public class GradeRepository extends AbstractInMemoryRepository<String, Grade> {
    @Override
    protected String extractId(Grade entity) {
        return entity.getStudentId() + ":" + entity.getCourseId();
    }
}
