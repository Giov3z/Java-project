package it.university.refactored.service;

import it.university.model.Course;
import it.university.refactored.repository.Repository;
import it.university.refactored.repository.CourseRepository;
import it.university.refactored.EmptyResultException;
import java.util.List;

/**
 * Refactored Course service.
 * - Riceve un CourseRepository concreto quando necessario (per operazioni di ricerca-specifiche)
 * - Le operazioni leggono/scrivono tramite l'interfaccia Repository quando possibile.
 */
public class CourseService {
    private final Repository<Course> repository;
    private final CourseRepository concreteRepo;

    public CourseService(Repository<Course> repository, CourseRepository concreteRepo) {
        this.repository = repository;
        this.concreteRepo = concreteRepo;
    }

    public void createCourse(Course c) { repository.save(c); }

    /** Assegna il professore al corso specificando l'id del professor. */
    public void assignProfessor(int courseId, int professorId) {
        Course c = concreteRepo.findById(courseId);
        if (c != null) {
            c.setProfessorId(professorId);
            // save to keep consistent behaviour
            repository.save(c);
        }
    }

    public List<Course> list() {
        List<Course> all = repository.findAll();
        if (all.isEmpty()) throw new EmptyResultException("Nessun corso presente");
        return all;
    }
}
