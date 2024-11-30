package dormitory_student_management.management.controller;

import dormitory_student_management.management.service.DormitoryProcedureCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dormitory")
public class DormitoryController {

    private final DormitoryProcedureCaller dormitoryProcedureCaller;

    @Autowired
    public DormitoryController(DormitoryProcedureCaller dormitoryProcedureCaller) {
        this.dormitoryProcedureCaller = dormitoryProcedureCaller;
    }

    // 기숙사 입사 프로시저 호출
    @PostMapping("/assign/{studentId}")
    public String assignDormitory(@PathVariable int studentId) {
        try {
            dormitoryProcedureCaller.assignDormitory(studentId);
            return "학번 " + studentId + "번 학생의 기숙사 배정이 완료되었습니다.";
        } catch (Exception e) {
            return "기숙사 배정 중 오류 발생: " + e.getMessage();
        }
    }
}