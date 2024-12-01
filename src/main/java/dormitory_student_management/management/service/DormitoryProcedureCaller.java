package dormitory_student_management.management.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.stereotype.Service;

@Service
public class DormitoryProcedureCaller {

    private final JdbcTemplate jdbcTemplate;

    // JdbcTemplate 주입
    public DormitoryProcedureCaller(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 기숙사 입사 프로시저 호출
    public void assignDormitory(int studentId) {
        try {
            String procedureCall = "{CALL 기숙사_입사_프로시져(?)}";

            // CallableStatementCallback 사용
            jdbcTemplate.execute(procedureCall, (CallableStatementCallback<Void>) callableStatement -> {
                callableStatement.setInt(1, studentId);
                callableStatement.execute();
                System.out.println("프로시저 호출 성공: 학번 " + studentId + "번 학생의 기숙사 배정 완료.");
                return null;
            });

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("프로시저 호출 실패: " + e.getMessage());
        }
    }
}
