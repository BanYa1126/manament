package dormitory_student_management.management.service;

import dormitory_student_management.management.repository.StudentDormitoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class StudentDormitoryService {
    private final StudentDormitoryRepository studentDormitoryRepository;

    public StudentDormitoryService(StudentDormitoryRepository studentDormitoryRepository) {
        this.studentDormitoryRepository = studentDormitoryRepository;
    }

    @Transactional(rollbackFor = SQLException.class)
    public void assignDormitory(int studentId) {
        studentDormitoryRepository.assignDormitoryByProcedure(studentId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateDepartureDate(int studentId, int days) {
        studentDormitoryRepository.setDepartureDate(studentId, days);
    }
}