package it.university.service;

import it.university.model.Enrollment;
import it.university.repository.impl.EnrollmentRepositoryImpl;
import it.university.repository.Repository;

import java.util.List;

public class EnrollmentService extends AbstractService<String, Enrollment> {

    private final EnrollmentRepositoryImpl enrollmentRepository;

    public EnrollmentService(Repository<String, Enrollment> repository, EnrollmentRepositoryImpl enrollmentRepository) {
        super(repository);
        this.enrollmentRepository = enrollmentRepository;
    }

    public void enrollStudent(Enrollment enrollment) {
        save(enrollment);
    }

    @Override
    public List<Enrollment> list() {
        return super.list();
    }

    public boolean isEnrolled(int studentId, int courseId) {
        return enrollmentRepository.exists(studentId, courseId);
    }

    @Override
    protected String emptyMessage() {
        return "Nessuna iscrizione presente";
    }
}
