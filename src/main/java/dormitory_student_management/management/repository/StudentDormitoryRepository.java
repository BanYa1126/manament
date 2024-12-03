package dormitory_student_management.management.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class StudentDormitoryRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDormitoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 기숙사 배정 프로시저 호출
    public void assignDormitoryByProcedure(int studentId) {
        String procedureCall = "{CALL 기숙사_입사_프로시져(?)}";
        try {
            jdbcTemplate.update(procedureCall, studentId);
        } catch (Exception e) {
            throw new RuntimeException("기숙사 배정 프로시저 호출 중 오류 발생 - 학번: " + studentId + ", 오류: " + e.getMessage(), e);
        }
    }
}