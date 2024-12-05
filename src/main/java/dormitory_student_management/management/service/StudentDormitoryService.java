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
        int penaltyScore = studentDormitoryRepository.getPenaltyScore(studentId);

        if (penaltyScore <= -15) {
            throw new IllegalArgumentException("학번 " + studentId + "번 학생은 상벌점이 -15 이하이므로 입사가 불가능합니다.");
        }

        studentDormitoryRepository.assignDormitoryByProcedure(studentId);
        studentDormitoryRepository.setDepartureDate(studentId, days);
    }
}