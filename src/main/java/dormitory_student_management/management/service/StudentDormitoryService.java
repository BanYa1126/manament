package dormitory_student_management.management.service;

import dormitory_student_management.management.repository.StudentDormitoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentDormitoryService {
    private final StudentDormitoryRepository studentDormitoryRepository;

    public StudentDormitoryService(StudentDormitoryRepository studentDormitoryRepository) {
        this.studentDormitoryRepository = studentDormitoryRepository;
    }

    @Transactional
    public void setDepartureDate(int studentId, int days) {
        studentDormitoryRepository.assignDormitoryByProcedure(studentId);
        studentDormitoryRepository.setDepartureDate(studentId, days);
    }
}