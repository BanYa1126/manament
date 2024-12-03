package dormitory_student_management.management.service;

import dormitory_student_management.management.repository.StudentDormitoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentDormitoryAssignmentService {
    private final StudentDormitoryRepository studentDormitoryRepository;

    public StudentDormitoryAssignmentService(StudentDormitoryRepository studentDormitoryRepository) {
        this.studentDormitoryRepository = studentDormitoryRepository;
    }

    public void assignDormitory(int studentId) {
        studentDormitoryRepository.assignDormitoryByProcedure(studentId);
    }
}