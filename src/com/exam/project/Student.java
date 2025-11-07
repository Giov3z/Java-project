package com.exam.project;

/**
 * Student class demonstrating Object-Oriented Programming concepts
 * Includes encapsulation, constructors, and methods
 */
public class Student {
    private String name;
    private int studentId;
    private double gpa;
    
    /**
     * Constructor to create a new Student
     * @param name student's name
     * @param studentId student's ID number
     * @param gpa student's grade point average
     */
    public Student(String name, int studentId, double gpa) {
        this.name = name;
        this.studentId = studentId;
        setGpa(gpa);  // Use setter to validate GPA
    }
    
    /**
     * Gets the student's name
     * @return student name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the student's name
     * @param name new name for the student
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the student ID
     * @return student ID
     */
    public int getStudentId() {
        return studentId;
    }
    
    /**
     * Sets the student ID
     * @param studentId new student ID
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
    
    /**
     * Gets the student's GPA
     * @return GPA value
     */
    public double getGpa() {
        return gpa;
    }
    
    /**
     * Sets the student's GPA with validation
     * @param gpa new GPA value (must be between 0.0 and 4.0)
     */
    public void setGpa(double gpa) {
        if (gpa < 0.0 || gpa > 4.0) {
            System.out.println("Warning: GPA should be between 0.0 and 4.0");
            this.gpa = Math.max(0.0, Math.min(4.0, gpa));
        } else {
            this.gpa = gpa;
        }
    }
    
    /**
     * Checks if student has honors (GPA >= 3.5)
     * @return true if student has honors, false otherwise
     */
    public boolean hasHonors() {
        return gpa >= 3.5;
    }
    
    /**
     * String representation of the Student
     * @return formatted string with student information
     */
    @Override
    public String toString() {
        return String.format("Student[name=%s, id=%d, gpa=%.2f, honors=%s]", 
                           name, studentId, gpa, hasHonors() ? "Yes" : "No");
    }
}
