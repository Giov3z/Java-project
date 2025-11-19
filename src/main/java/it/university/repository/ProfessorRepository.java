package it.university.repository;

import it.university.model.Professor;
import it.university.repository.impl.AbstractInMemoryRepository;

public class ProfessorRepository extends AbstractInMemoryRepository<Integer, Professor> {
    @Override
    protected Integer extractId(Professor entity) {
        return entity.getId();
    }
}
