package dormitory_student_management.management.controller;

import dormitory_student_management.management.service.StudentDormitoryAssignmentService;
import dormitory_student_management.management.service.StudentDormitoryDepartureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/student-dormitory")
public class StudentDormitoryController {
    private final StudentDormitoryAssignmentService studentDormitoryAssignmentService;
    private final StudentDormitoryDepartureService studentDormitoryDepartureService;

    @Autowired
    public StudentDormitoryController(StudentDormitoryAssignmentService studentDormitoryAssignmentService, StudentDormitoryDepartureService studentDormitoryDepartureService) {
        this.studentDormitoryAssignmentService = studentDormitoryAssignmentService;
        this.studentDormitoryDepartureService = studentDormitoryDepartureService;
    }

    @PostMapping("/assign/{studentId}")
    public String assignDormitory(@PathVariable int studentId, @RequestBody Map<String, Integer> requestBody) {
        try {
            // 기숙사 배정 및 퇴사일 설정
            studentDormitoryAssignmentService.assignDormitory(studentId);

            Integer days = requestBody.get("days");
            if (days == null) {
                throw new IllegalArgumentException("퇴사일 옵션이 누락되었습니다.");
            }
            studentDormitoryDepartureService.setDepartureDate(studentId, days);

            return "학번 " + studentId + "번 학생의 기숙사 배정과 퇴사일 설정이 완료되었습니다.";
        } catch (Exception e) {
            return "작업 중 오류 발생: " + e.getMessage();
        }
    }
}