package it.university.service;

import it.university.model.Enrollment;
import it.university.repository.EnrollmentRepository;
import java.util.List;

public class EnrollmentService {
    private EnrollmentRepository repo = new EnrollmentRepository();

    public void enrollStudent(Enrollment e) { repo.save(e); }
    public List<Enrollment> list() { 
        if (repo.findAll().isEmpty()){
            System.out.println("Nessuna iscrizione trovata");
        }
        return repo.findAll(); 
    }
    public boolean isEnrolled(int studentId, int courseId) { return repo.exists(studentId, courseId); }
}
