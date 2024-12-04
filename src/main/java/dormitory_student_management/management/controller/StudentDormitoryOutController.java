package dormitory_student_management.management.controller;

import dormitory_student_management.management.service.StudentDormitoryOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student-dormitory")
public class StudentDormitoryOutController {
    private final StudentDormitoryOutService studentDormitoryOutService;

    @Autowired
    public StudentDormitoryOutController(StudentDormitoryOutService studentDormitoryOutService) {
        this.studentDormitoryOutService = studentDormitoryOutService;
    }

    @PostMapping("/depart/{studentId}")
    public String departDormitory(@PathVariable int studentId) {
        try {
            studentDormitoryOutService.processDeparture(studentId);
            return "학번 " + studentId + "번 학생의 기숙사 퇴실 처리가 완료되었습니다.";
        } catch (Exception e) {
            return "기숙사 퇴실 처리 중 오류 발생: " + e.getMessage();
        }
    }
}
