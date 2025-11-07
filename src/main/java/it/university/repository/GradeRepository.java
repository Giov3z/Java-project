package it.university.repository;

import it.university.model.Grade;
import java.util.*;

public class GradeRepository {
    private List<Grade> grades = new ArrayList<>();

    public void save(Grade g) { grades.add(g); }
    public List<Grade> findAll() { return grades; }
}
