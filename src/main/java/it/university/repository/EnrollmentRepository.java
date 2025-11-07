package it.university.repository;

import it.university.model.Enrollment;
import java.util.*;

public class EnrollmentRepository {
    private List<Enrollment> enrollments = new ArrayList<>();

    public void save(Enrollment e) { enrollments.add(e); }
    public List<Enrollment> findAll() { return enrollments; }

    public boolean exists(int studentId, int courseId) {
        return enrollments.stream().anyMatch(en -> en.getStudentId() == studentId && en.getCourseId() == courseId);
    }
}
