package dormitory_student_management.management.controller;

import dormitory_student_management.management.service.DormitoryMoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student-dormitory")
public class DormitoryMoveController {

    private final DormitoryMoveService dormitoryMoveService;

    @Autowired
    public DormitoryMoveController(DormitoryMoveService dormitoryMoveService) {
        this.dormitoryMoveService = dormitoryMoveService;
    }

    @PostMapping("/move/{studentId}")
    public String moveDormitory(@PathVariable int studentId) {
        try {
            dormitoryMoveService.processDormitoryMove(studentId); // 서비스 호출
            return "학번 " + studentId + "번 학생의 기숙사 방 이동이 완료되었습니다.";
        } catch (Exception e) {
            return "기숙사 방 이동 처리 중 오류 발생: " + e.getMessage();
        }
    }
}