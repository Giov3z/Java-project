package it.university.refactored.service;

import it.university.model.Student;
import it.university.refactored.repository.Repository;
import it.university.refactored.EmptyResultException;
import java.util.List;

/**
 * Service refactorizzato per gli Student.
 * - Dipende dall'interfaccia Repository<T> per facilitare testing e sostituzione dell'implementazione.
 * - Non effettua side-effect (stampe). Se non ci sono studenti lancia EmptyResultException:
 *   la responsabilità di presentare il messaggio all'utente resta al caller.
 */
public class StudentService {
    private final Repository<Student> repository;

    public StudentService(Repository<Student> repository) {
        this.repository = repository;
    }

    public void registerStudent(Student s) {
        repository.save(s);
    }

    public List<Student> list() {
        List<Student> all = repository.findAll();
        if (all.isEmpty()) throw new EmptyResultException("Nessuno studente presente");
        return all;
    }
}
