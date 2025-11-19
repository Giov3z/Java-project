package it.university.service;

import it.university.model.Student;
import it.university.repository.Repository;

public class StudentService extends AbstractService<Integer, Student> {

    public StudentService(Repository<Integer, Student> repository) {
        super(repository);
    }

    public void registerStudent(Student student) {
        save(student);
    }

    @Override
    protected String emptyMessage() {
        return "Nessuno studente presente";
    }
}
