package it.university.refactored;

import it.university.model.*;
import it.university.refactored.repository.*;
import it.university.refactored.service.*;

/**
 * Esempio duplicato del Main che utilizza le classi refactorizzate.
 *
 * Scopo: permettere il confronto diretto tra versione originale e refactor,
 * mantenendo intatti i file originali.
 */
public class MainAppRefactored {
    public static void main(String[] args) {
        // Creazione dei repository refactorizzati
        StudentRepository studentRepo = new StudentRepository();
        ProfessorRepository professorRepo = new ProfessorRepository();
        CourseRepository courseRepo = new CourseRepository();
        ClassroomRepository classroomRepo = new ClassroomRepository();
        EnrollmentRepository enrollmentRepo = new EnrollmentRepository();
        GradeRepository gradeRepo = new GradeRepository();

        // Iniezione manuale (constructor injection)
        StudentService studentService = new StudentService(studentRepo);
        ProfessorService professorService = new ProfessorService(professorRepo);
        CourseService courseService = new CourseService(courseRepo, courseRepo);
        ClassroomService classroomService = new ClassroomService(classroomRepo);
        EnrollmentService enrollmentService = new EnrollmentService(enrollmentRepo);
        GradeService gradeService = new GradeService(gradeRepo);

        // Il comportamento ora è: i service lanciano EmptyResultException se non ci sono risultati.
        // Il Main si occupa di catturare l'eccezione e di decidere come informare l'utente.
        try {
            System.out.println("Studenti:");
            studentService.list().forEach(System.out::println);
        } catch (EmptyResultException e) {
            System.out.println("[Refactored] " + e.getMessage());
        }

        studentService.registerStudent(new Student(1, "Alice", "alice@mail.com"));
        studentService.registerStudent(new Student(2, "Bob", "bob@mail.com"));

        try {
            System.out.println("Studenti:");
            studentService.list().forEach(System.out::println);
        } catch (EmptyResultException e) {
            System.out.println("[Refactored] " + e.getMessage());
        }

        try {
            System.out.println("\nProfessori:");
            professorService.list().forEach(System.out::println);
        } catch (EmptyResultException e) {
            System.out.println("[Refactored] " + e.getMessage());
        }

        professorService.add(new Professor(1, "Dr. Rossi", "Informatica"));

        try {
            System.out.println("\nProfessori:");
            professorService.list().forEach(System.out::println);
        } catch (EmptyResultException e) {
            System.out.println("[Refactored] " + e.getMessage());
        }

        // Corsi
        try {
            System.out.println("\nCorsi:");
            courseService.list().forEach(System.out::println);
        } catch (EmptyResultException e) {
            System.out.println("[Refactored] " + e.getMessage());
        }

        courseService.createCourse(new Course(1, "Programmazione", 9));
        courseService.createCourse(new Course(2, "Basi di Dati", 6));
        courseService.assignProfessor(1, 1);

        try {
            System.out.println("\nCorsi:");
            courseService.list().forEach(System.out::println);
        } catch (EmptyResultException e) {
            System.out.println("[Refactored] " + e.getMessage());
        }

        // Aule
        try {
            System.out.println("\nAule:");
            classroomService.list().forEach(System.out::println);
        } catch (EmptyResultException e) {
            System.out.println("[Refactored] " + e.getMessage());
        }

        classroomService.add(new Classroom("A101", 30));

        try {
            System.out.println("\nAule:");
            classroomService.list().forEach(System.out::println);
        } catch (EmptyResultException e) {
            System.out.println("[Refactored] " + e.getMessage());
        }

        // Iscrizioni
        try {
            System.out.println("\nIscrizioni:");
            enrollmentService.list().forEach(System.out::println);
        } catch (EmptyResultException e) {
            System.out.println("[Refactored] " + e.getMessage());
        }

        enrollmentService.enrollStudent(new Enrollment(1,1));
        enrollmentService.enrollStudent(new Enrollment(2,1));

        try {
            System.out.println("\nIscrizioni:");
            enrollmentService.list().forEach(System.out::println);
        } catch (EmptyResultException e) {
            System.out.println("[Refactored] " + e.getMessage());
        }

        // Voti
        try {
            System.out.println("\nVoti:");
            gradeService.list().forEach(System.out::println);
        } catch (EmptyResultException e) {
            System.out.println("[Refactored] " + e.getMessage());
        }

        gradeService.add(new Grade(1,1,28));

        try {
            System.out.println("\nVoti:");
            gradeService.list().forEach(System.out::println);
        } catch (EmptyResultException e) {
            System.out.println("[Refactored] " + e.getMessage());
        }
    }
}
