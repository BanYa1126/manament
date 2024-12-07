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

            // 기숙사 배정 프로시저 호출
            studentDormitoryService.assignDormitory(studentId);

            // 퇴사일 업데이트
            studentDormitoryService.updateDepartureDate(studentId, days);

            // 성공 응답
            return ResponseEntity.ok("학번 " + studentId + "번 학생의 기숙사 배정과 퇴사일 설정이 완료되었습니다.");
        } catch (DataAccessException e) {
            // DataAccessException 내부의 SQLException 추출
            Throwable cause = e.getCause();
            if (cause instanceof SQLException) {
                return handleSQLException((SQLException) cause);
            }
            return ResponseEntity.status(500).body("알 수 없는 데이터베이스 오류 발생: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("예기치 못한 오류 발생: " + e.getMessage());
        }
    }

    private ResponseEntity<String> handleSQLException(SQLException sqlException) {
        // SQL 오류 코드에 따른 처리
        switch (sqlException.getErrorCode()) {
            case 20001:
                return ResponseEntity.badRequest().body("입사 거부: 상벌점 기준을 충족하지 못했습니다.");
            default:
                return ResponseEntity.status(500).body("트리거 오류 발생: " + sqlException.getMessage());
        }
    }
}