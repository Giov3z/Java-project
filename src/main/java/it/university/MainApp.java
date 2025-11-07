package it.university;

import it.university.model.*;
import it.university.service.*;

public class MainApp {
    public static void main(String[] args) {
        StudentService studentService = new StudentService();
        ProfessorService professorService = new ProfessorService();
        CourseService courseService = new CourseService();
        ClassroomService classroomService = new ClassroomService();
        EnrollmentService enrollmentService = new EnrollmentService();
        GradeService gradeService = new GradeService();
        
        System.out.println("Studenti:");
        studentService.list().forEach(System.out::println);

        studentService.registerStudent(new Student(1, "Alice", "alice@mail.com"));
        studentService.registerStudent(new Student(2, "Bob", "bob@mail.com"));
        
        System.out.println("Studenti:");
        studentService.list().forEach(System.out::println);

        System.out.println("\nProfessori:");
        professorService.list().forEach(System.out::println);

        professorService.add(new Professor(1, "Dr. Rossi", "Informatica"));
        System.out.println("\nProfessori:");
        professorService.list().forEach(System.out::println);


        System.out.println("\nCorsi:");
        courseService.list().forEach(System.out::println);

        courseService.createCourse(new Course(1, "Programmazione", 9));
        courseService.createCourse(new Course(2, "Basi di Dati", 6));
        courseService.assignProfessor(courseService.list().get(0), 1);

        System.out.println("\nCorsi:");
        courseService.list().forEach(System.out::println);

        System.out.println("\nAule:");
        classroomService.list().forEach(System.out::println);


        classroomService.add(new Classroom("A101", 30));

        System.out.println("\nAule:");
        classroomService.list().forEach(System.out::println);

        System.out.println("\nIscrizioni:");
        enrollmentService.list().forEach(System.out::println);

        enrollmentService.enrollStudent(new Enrollment(1,1));
        enrollmentService.enrollStudent(new Enrollment(2,1));

        System.out.println("\nIscrizioni:");
        enrollmentService.list().forEach(System.out::println);



        System.out.println("\nVoti:");
        gradeService.list().forEach(System.out::println);

        gradeService.add(new Grade(1,1,28));

        System.out.println("\nVoti:");
        gradeService.list().forEach(System.out::println);


        

    }
}
