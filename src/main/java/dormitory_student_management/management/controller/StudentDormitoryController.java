package dormitory_student_management.management.controller;

import dormitory_student_management.management.service.StudentDormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
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
    public ResponseEntity<String> assignDormitory(@PathVariable int studentId, @RequestBody Map<String, Integer> requestBody) {
        try {
            Integer days = requestBody.get("days");

            // 프로시저 호출 및 마지막 메시지 반환
            String lastMessage = studentDormitoryService.assignDormitory(studentId);

            // 퇴사일 업데이트
            studentDormitoryService.updateDepartureDate(studentId, days);

            // 마지막 메시지와 성공 응답 반환
            return ResponseEntity.ok(lastMessage);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("오류 발생: " + e.getMessage());
        }
    }
}