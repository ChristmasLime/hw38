package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;


    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public Student createStud(Student student) {
        logger.info("Run method createStud ");
        return studentRepository.save(student);
    }

    public Student findStud(Long id) {
        logger.info("Run method findStud ");
        return studentRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public Student editStud(Student student) {
        logger.info("Run method editStud ");
        return studentRepository.save(student);
    }

    public void deleteStud(Long id) {
        logger.info("Run method deleteStud ");
        studentRepository.deleteById(id);

    }

    public Collection<Student> getAllStud() {
        logger.info("Run method getAllStud ");
        return studentRepository.findAll();
    }

    public Collection<Student> getStudByAge(int age) {
        logger.info("Run method getStudByAge ");
        return studentRepository.getStudByAge(age);
    }

    public Collection<Student> getStudentsAgeBetween(int minAge, int maxAge) {
        logger.info("Run method getStudentsAgeBetween ");
        return studentRepository.findStudByAgeBetween(minAge, maxAge);
    }


    public Collection<Student> getByFacultyId(Long id) {
        logger.info("Run method getByFacultyId ");
        return facultyRepository.findById(id)
                .map(Faculty::getStudents)
                .orElseThrow(NoSuchElementException::new);
    }

    public Long getCountOfStudents() {
        logger.info("Run method getCountOfStudents ");
        return studentRepository.getCountOfStudents();
    }

    public Double getAverageAgeOfStudents() {
        logger.info("Run method getAverageAgeOfStudents ");
        return studentRepository.getAverageAgeOfStudents();
    }

    public List<Student> findLastFiveStudents() {
        logger.info("Run method findLastFiveStudents ");
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Student> studentsPage = studentRepository.findLastFiveStudents(pageRequest);
        return studentsPage.getContent();
    }

    public List<String> getNames(char firstSymbol) {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .filter(name->Character.toLowerCase(name.charAt(0))
                        ==Character.toLowerCase(firstSymbol))
                .collect(Collectors.toList());
    }

    public double getAveregeAge() {
        return studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElseThrow(NoSuchElementException::new);
    }
}


