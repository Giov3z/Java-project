package it.university.service;

import it.university.model.Student;
import it.university.repository.StudentRepository;
import java.util.List;

public class StudentService {
    private StudentRepository repository = new StudentRepository();

    public void registerStudent(Student s) { repository.save(s); }
    public List<Student> list() { 
        if (repository.findAll().isEmpty()){
            System.out.println("Nessuno studente");
        }
        return repository.findAll(); 
    }
}
