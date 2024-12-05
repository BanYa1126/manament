package dormitory_student_management.management.controller;

import dormitory_student_management.management.service.StudentDormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/student-dormitory")
public class StudentDormitoryController {
    private final StudentDormitoryService studentDormitoryService;

    @Autowired
    public StudentDormitoryController(StudentDormitoryService studentDormitoryService) {
        this.studentDormitoryService = studentDormitoryService;
    }

    @PostMapping("/assign/{studentId}")
    public String assignDormitory(@PathVariable int studentId, @RequestBody Map<String, Integer> requestBody) {
        try {
            Integer days = requestBody.get("days");
            if (days == null) {
                throw new IllegalArgumentException("퇴사일 옵션이 누락되었습니다.");
            }
            studentDormitoryService.setDepartureDate(studentId, days);

            return "학번 " + studentId + "번 학생의 기숙사 배정과 퇴사일 설정이 완료되었습니다.";
        } catch (IllegalArgumentException e) {
            return "입사 거부 : " + e.getMessage();
        } catch (Exception e) {
            return "작업 중 오류 발생: " + e.getMessage();
        }
    }
}