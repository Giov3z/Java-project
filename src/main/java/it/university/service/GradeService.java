package it.university.service;

import it.university.model.Grade;
import it.university.repository.Repository;

public class GradeService extends AbstractService<String, Grade> {

    public GradeService(Repository<String, Grade> repository) {
        super(repository);
    }

    public void add(Grade grade) {
        save(grade);
    }

    @Override
    protected String emptyMessage() {
        return "Nessun voto presente";
    }
}
