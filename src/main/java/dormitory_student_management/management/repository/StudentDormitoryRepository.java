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

    // 퇴사일 설정 메서드
    public void setDepartureDate(int studentId, int days) {
        String updateStudentQuery = "UPDATE 학생 " +
                "SET 퇴사일 = 입사일 + NUMTODSINTERVAL(?, 'DAY') " +
                "WHERE 학번 = ?";

        int rowsAffected = jdbcTemplate.update(updateStudentQuery, days, studentId);
        if (rowsAffected == 0) {
            throw new RuntimeException("학번 " + studentId + "번 학생을 찾을 수 없습니다.");
        }

        System.out.println("학번 " + studentId + "번 학생의 퇴사일이 성공적으로 설정되었습니다.");
    }

    // 상벌점 조회 메서드
    public int getPenaltyScore(int studentId) {
        String query = "SELECT s.점수 FROM 학생 p " +
                "JOIN 상벌점 s ON p.학번 = s.학번 " +
                "WHERE p.학번 = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{studentId}, Integer.class);
        } catch (Exception e) {
            throw new RuntimeException("학번 " + studentId + "번 학생의 상벌점 정보를 가져오는 중 오류 발생: " + e.getMessage(), e);
        }
    }
}