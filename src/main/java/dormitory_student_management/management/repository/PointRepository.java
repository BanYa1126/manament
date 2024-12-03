package dormitory_student_management.management.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PointRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PointRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void recordPoints(int studentId, int points) {
        String procedureCall = "{CALL 상벌점_기록_프로시져(?, ?)}";

        jdbcTemplate.execute(procedureCall, (CallableStatementCallback<Void>) callableStatement -> {
            callableStatement.setInt(1, studentId); // 첫 번째 매개변수: 학번
            callableStatement.setInt(2, points);    // 두 번째 매개변수: 점수 (+ 또는 -)
            callableStatement.execute();
            return null;
        });
    }
}
