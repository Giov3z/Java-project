package it.university.model;

public class Course {
    private int id;
    private String name;
    private int credits;
    private Integer professorId; // optional assignment

    public Course(int id, String name, int credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getCredits() { return credits; }
    public Integer getProfessorId() { return professorId; }
    public void setProfessorId(Integer professorId) { this.professorId = professorId; }

    @Override
    public String toString() {
        return "Course{" + id + ", " + name + ", " + credits + " CFU, prof=" + professorId + "}";
    }
}
