package dormitory_student_management.management.service;

import dormitory_student_management.management.repository.StudentDormitoryOutRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentDormitoryOutService {
    private final StudentDormitoryOutRepository studentDormitoryOutRepository;

    public StudentDormitoryOutService(StudentDormitoryOutRepository studentDormitoryOutRepository) {
        this.studentDormitoryOutRepository = studentDormitoryOutRepository;
    }

    @Transactional
    public void processDeparture(int studentId) {
        studentDormitoryOutRepository.callDepartureProcedure(studentId);
    }
}
