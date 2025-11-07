package it.university.model;

public class Grade {
    private int studentId;
    private int courseId;
    private int value;

    public Grade(int studentId, int courseId, int value) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.value = value;
    }

    public int getStudentId() { return studentId; }
    public int getCourseId() { return courseId; }
    public int getValue() { return value; }

    @Override
    public String toString() {
        return "Grade{student=" + studentId + ", course=" + courseId + ", value=" + value + "}";
    }
}
