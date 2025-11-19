package it.university.repository;

import it.university.model.Classroom;
import it.university.repository.impl.AbstractInMemoryRepository;

public class ClassroomRepository extends AbstractInMemoryRepository<String, Classroom> {
    @Override
    protected String extractId(Classroom entity) {
        return entity.getCode();
    }
}
