package dormitory_student_management.management.controller;

import dormitory_student_management.management.service.DormitoryProcedureCaller;
import dormitory_student_management.management.service.DormitoryDepartureDateSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/dormitory")
public class DormitoryController {

    private final DormitoryProcedureCaller dormitoryProcedureCaller;
    private final DormitoryDepartureDateSetter dormitoryDepartureDateSetter;

    @Autowired
    public DormitoryController(DormitoryProcedureCaller dormitoryProcedureCaller, DormitoryDepartureDateSetter dormitoryDepartureDateSetter) {
        this.dormitoryProcedureCaller = dormitoryProcedureCaller;
        this.dormitoryDepartureDateSetter = dormitoryDepartureDateSetter;
    }

    @PostMapping("/assign/{studentId}")
    public String assignDormitory(@PathVariable int studentId) {
        try {
            // 학번을 기반으로 프로시저 호출
            dormitoryProcedureCaller.assignDormitory(studentId);

            return "학번 " + studentId + "번 학생의 기숙사 배정이 완료되었습니다.";
        } catch (Exception e) {
            return "기숙사 배정 중 오류 발생: " + e.getMessage();
        }
    }

    @PostMapping("/departure/{studentId}")
    public String setDepartureDate(@PathVariable int studentId, @RequestBody Map<String, Integer> requestBody) {
        Integer days = requestBody.get("days");
        if (days == null) {
            throw new IllegalArgumentException("퇴사일 옵션이 누락되었습니다.");
        }
        // 주입된 서비스 인스턴스를 통해 메서드 호출
        dormitoryDepartureDateSetter.setDepartureDate(studentId, days);
        return "학번 " + studentId + "번 학생의 퇴사일이 " + days + "일로 설정되었습니다.";
    }
}