package com.exam.project;

/**
 * Main class for the exam project
 * This demonstrates basic Java programming concepts
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Java Exam Project!");
        System.out.println("================================");
        
        // Demonstrate Calculator usage
        Calculator calc = new Calculator();
        System.out.println("\nCalculator Demo:");
        System.out.println("10 + 5 = " + calc.add(10, 5));
        System.out.println("10 - 5 = " + calc.subtract(10, 5));
        System.out.println("10 * 5 = " + calc.multiply(10, 5));
        System.out.println("10 / 5 = " + calc.divide(10, 5));
        
        // Demonstrate Student class usage
        System.out.println("\nStudent Demo:");
        Student student1 = new Student("John Doe", 12345, 3.8);
        Student student2 = new Student("Jane Smith", 12346, 3.9);
        
        System.out.println(student1);
        System.out.println(student2);
        
        System.out.println("\nHigher GPA: " + 
            (student1.getGpa() > student2.getGpa() ? student1.getName() : student2.getName()));
    }
}
