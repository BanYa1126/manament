package dormitory_student_management.management.service;

import dormitory_student_management.management.repository.DormitoryMoveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DormitoryMoveService {

    private final DormitoryMoveRepository dormitoryMoveRepository;

    public DormitoryMoveService(DormitoryMoveRepository dormitoryMoveRepository) {
        this.dormitoryMoveRepository = dormitoryMoveRepository;
    }

    @Transactional
    public String processDormitoryMove(int studentId) throws Exception {
        return dormitoryMoveRepository.callDormitoryMoveProcedure(studentId);
    }
}