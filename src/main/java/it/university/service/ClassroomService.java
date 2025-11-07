package it.university.service;

import it.university.model.Classroom;
import it.university.repository.ClassroomRepository;
import java.util.List;

public class ClassroomService {
    private ClassroomRepository repo = new ClassroomRepository();

    public void add(Classroom c) { repo.save(c); }
    public List<Classroom> list() { 
        if (repo.findAll().isEmpty()){
            System.out.println("Nessuna aula trovata");
        }
        return repo.findAll(); 
    }
}
