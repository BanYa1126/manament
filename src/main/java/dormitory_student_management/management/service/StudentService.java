package dormitory_student_management.management.service;

import dormitory_student_management.management.domain.Student;
import dormitory_student_management.management.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudentsByRoomNumber(Integer roomNumber) {
        return studentRepository.findByDormitoryRoomNumber(roomNumber);
    }
}