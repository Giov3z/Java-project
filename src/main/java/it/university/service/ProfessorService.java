package it.university.service;

import it.university.model.Professor;
import it.university.repository.Repository;

public class ProfessorService extends AbstractService<Integer, Professor> {

    public ProfessorService(Repository<Integer, Professor> repository) {
        super(repository);
    }

    public void add(Professor professor) {
        save(professor);
    }

    @Override
    protected String emptyMessage() {
        return "Nessun professore presente";
    }
}
