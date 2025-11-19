package it.university.service;

import it.university.model.Course;
import it.university.repository.CourseRepository;
import it.university.repository.Repository;

public class CourseService extends AbstractService<Integer, Course> {

    private final CourseRepository courseRepository;

    public CourseService(Repository<Integer, Course> repository, CourseRepository courseRepository) {
        super(repository);
        this.courseRepository = courseRepository;
    }

    public void createCourse(Course course) {
        save(course);
    }

    public void assignProfessor(int courseId, int professorId) {
        courseRepository.findById(courseId)
                .ifPresent(course -> {
                    course.setProfessorId(professorId);
                    save(course);
                });
    }

    @Override
    protected String emptyMessage() {
        return "Nessun corso presente";
    }
}
