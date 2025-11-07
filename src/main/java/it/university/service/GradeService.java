package it.university.service;

import it.university.model.Grade;
import it.university.repository.GradeRepository;
import java.util.List;

public class GradeService {
    private GradeRepository repo = new GradeRepository();

    public void add(Grade g) { repo.save(g); }
    public List<Grade> list() {
        if (repo.findAll().isEmpty()){
            System.out.println("Nessun voto registrato");
        } 
        return repo.findAll(); 
    }
}
