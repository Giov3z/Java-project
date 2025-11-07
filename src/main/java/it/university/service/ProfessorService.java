package it.university.service;

import it.university.model.Professor;
import it.university.repository.ProfessorRepository;
import java.util.List;

public class ProfessorService {
    private ProfessorRepository repository = new ProfessorRepository();

    public void add(Professor p) { repository.save(p); }
    public List<Professor> list() { 
        if (repository.findAll().isEmpty()){
            System.out.println("Nessun professore trovato");
        }
        return repository.findAll(); 
    }
}
