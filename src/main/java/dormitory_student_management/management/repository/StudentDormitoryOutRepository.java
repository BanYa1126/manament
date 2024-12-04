package dormitory_student_management.management.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDormitoryOutRepository {
    private final JdbcTemplate jdbcTemplate;

    public StudentDormitoryOutRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 기숙사 퇴실 프로시저 호출
    public void callDepartureProcedure(int studentId) {
        String procedureCall = "{CALL 기숙사_퇴실_프로시져(?)}";
        try {
            jdbcTemplate.update(procedureCall, studentId);
        } catch (Exception e) {
            throw new RuntimeException("기숙사 퇴실 프로시저 호출 중 오류 발생 - 학번: " + studentId + ", 오류: " + e.getMessage(), e);
        }
    }
}
