package it.university.service;

import it.university.model.Course;
import it.university.repository.CourseRepository;
import java.util.List;

public class CourseService {
    private CourseRepository repo = new CourseRepository();

    public void createCourse(Course c) { repo.save(c); }
    public void assignProfessor(Course c, int professorId) { c.setProfessorId(professorId); }
    public List<Course> list() { 
        if (repo.findAll().isEmpty()){
            System.out.println("Nessun corso trovato");
        }
        return repo.findAll(); 
    }
}
