package dormitory_student_management.management.controller;

import dormitory_student_management.management.service.DormitoryProcedureCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/dormitory")
public class DormitoryController {

    private final DormitoryProcedureCaller dormitoryProcedureCaller;

    @Autowired
    public DormitoryController(DormitoryProcedureCaller dormitoryProcedureCaller) {
        this.dormitoryProcedureCaller = dormitoryProcedureCaller;
    }

    @PostMapping("/assign/{studentId}")
    public String assignDormitory(@PathVariable int studentId, @RequestBody Map<String, String> requestData) {
        try {
            String departureDate = requestData.get("departureDate");

            // 프로시저 호출 (학번과 퇴사일 처리)
            dormitoryProcedureCaller.assignDormitory(studentId, departureDate);

            return "학번 " + studentId + "번 학생의 기숙사 배정이 완료되었습니다.";
        } catch (Exception e) {
            return "기숙사 배정 중 오류 발생: " + e.getMessage();
        }
    }
}