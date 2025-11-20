package it.university.service;

import it.university.model.Classroom;
import it.university.repository.Repository;

public class ClassroomService extends AbstractService<String, Classroom> {

    public ClassroomService(Repository<String, Classroom> repository) {
        super(repository);
    }

    public void add(Classroom classroom) {
        save(classroom);
    }

    @Override
    protected String emptyMessage() {
        return "Nessuna aula presente";
    }
}
