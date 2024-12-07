package dormitory_student_management.management.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class StudentDormitoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public StudentDormitoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void assignDormitoryByProcedure(int studentId) {
        String procedureCall = "{CALL 기숙사_입사_프로시져(?)}";
        try {
            jdbcTemplate.update(procedureCall, studentId);
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException) {
                SQLException sqlException = (SQLException) cause;

                // SQL 오류 캡처
                throw new RuntimeException("트리거 오류 발생: SQL 코드=" + sqlException.getErrorCode() +
                        ", 메시지=" + sqlException.getMessage(), sqlException);
            }
            throw new RuntimeException("기숙사 배정 프로시저 호출 중 알 수 없는 오류 발생: " + e.getMessage(), e);
        }
    }

    public void setDepartureDate(int studentId, int days) {
        String updateQuery = "UPDATE 학생 " +
                "SET 퇴사일 = 입사일 + NUMTODSINTERVAL(?, 'DAY') " +
                "WHERE 학번 = ?";
        try {
            int rowsAffected = jdbcTemplate.update(updateQuery, days, studentId);
            if (rowsAffected == 0) {
                throw new RuntimeException("학번 " + studentId + "번 학생을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof SQLException) {
                SQLException sqlException = (SQLException) cause;

                // SQL 오류 캡처
                throw new RuntimeException("SQL 오류 발생: 코드=" + sqlException.getErrorCode() +
                        ", 메시지=" + sqlException.getMessage(), sqlException);
            }
            throw new RuntimeException("퇴사일 설정 중 알 수 없는 오류 발생: " + e.getMessage(), e);
        }
    }
}