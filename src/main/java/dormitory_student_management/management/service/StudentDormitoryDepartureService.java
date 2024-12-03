package dormitory_student_management.management.service;

import dormitory_student_management.management.repository.StudentDormitoryRepository2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentDormitoryDepartureService {
    private final StudentDormitoryRepository2 studentDormitoryRepository2;

    public StudentDormitoryDepartureService(StudentDormitoryRepository2 studentDormitoryRepository2) {
        this.studentDormitoryRepository2 = studentDormitoryRepository2;
    }

    public void setDepartureDate(int studentId, int days) {
        studentDormitoryRepository2.setDepartureDate(studentId, days);
    }
}