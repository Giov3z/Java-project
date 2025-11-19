package it.university.repository;

import it.university.model.Student;
import it.university.repository.impl.AbstractInMemoryRepository;

/**
 * Repository in-memory per Student che sfrutta l'implementazione generica.
 */
public class StudentRepository extends AbstractInMemoryRepository<Integer, Student> {
    @Override
    protected Integer extractId(Student entity) {
        return entity.getId();
    }
}
