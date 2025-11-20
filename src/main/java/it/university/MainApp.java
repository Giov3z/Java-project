package it.university;

import it.university.common.EmptyResultException;
import it.university.model.*;
import it.university.repository.*;
import it.university.repository.impl.EnrollmentRepositoryImpl;
import it.university.service.*;

import java.util.List;
import java.util.function.Consumer;

public class MainApp {
    public static void main(String[] args) {
        StudentRepository studentRepository = new StudentRepository();
        ProfessorRepository professorRepository = new ProfessorRepository();
        CourseRepository courseRepository = new CourseRepository();
        ClassroomRepository classroomRepository = new ClassroomRepository();
        EnrollmentRepositoryImpl enrollmentRepository = new EnrollmentRepositoryImpl();
        GradeRepository gradeRepository = new GradeRepository();

        StudentService studentService = new StudentService(studentRepository);
        ProfessorService professorService = new ProfessorService(professorRepository);
        CourseService courseService = new CourseService(courseRepository, courseRepository);
        ClassroomService classroomService = new ClassroomService(classroomRepository);
        EnrollmentService enrollmentService = new EnrollmentService(enrollmentRepository, enrollmentRepository);
        GradeService gradeService = new GradeService(gradeRepository);

        printSection("Studenti", studentService::list);

        studentService.registerStudent(new Student(1, "Alice", "alice@mail.com"));
        studentService.registerStudent(new Student(2, "Bob", "bob@mail.com"));

        printSection("Studenti", studentService::list);

        printSection("Professori", professorService::list);

        professorService.add(new Professor(1, "Dr. Rossi", "Informatica"));
        printSection("Professori", professorService::list);

        printSection("Corsi", courseService::list);

        courseService.createCourse(new Course(1, "Programmazione", 9));
        courseService.createCourse(new Course(2, "Basi di Dati", 6));
        courseService.assignProfessor(1, 1);

        printSection("Corsi", courseService::list);

        printSection("Aule", classroomService::list);

        classroomService.add(new Classroom("A101", 30));

        printSection("Aule", classroomService::list);

        printSection("Iscrizioni", enrollmentService::list);

        enrollmentService.enrollStudent(new Enrollment(1,1));
        enrollmentService.enrollStudent(new Enrollment(2,1));

        printSection("Iscrizioni", enrollmentService::list);

        printSection("Voti", gradeService::list);

        gradeService.add(new Grade(1,1,28));

        printSection("Voti", gradeService::list);
    }

    private static <T> void printSection(String title, SupplierWithEmptyException<List<T>> supplier) {
        System.out.println("\n" + title + ":");
        try {
            supplier.get().forEach(System.out::println);
        } catch (EmptyResultException e) {
            System.out.println(e.getMessage());
        }
    }

    @FunctionalInterface
    private interface SupplierWithEmptyException<T> {
        T get() throws EmptyResultException;
    }
}
