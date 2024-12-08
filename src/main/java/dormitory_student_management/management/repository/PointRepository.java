package dormitory_student_management.management.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;

@Repository
public class PointRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PointRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String recordPoints(int studentId, int points) {
        String procedureCall = "{CALL 상벌점_기록_프로시져(?, ?)}";
        String lastMessage = null;

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             CallableStatement enableDbmsOutput = connection.prepareCall("BEGIN DBMS_OUTPUT.ENABLE(1000000); END;");
             CallableStatement callProcedure = connection.prepareCall(procedureCall)) {

            // DBMS_OUTPUT 활성화
            enableDbmsOutput.execute();

            // 프로시저 호출
            callProcedure.setInt(1, studentId);
            callProcedure.setInt(2, points);
            callProcedure.execute();

            // DBMS_OUTPUT 메시지 읽기
            lastMessage = getLastDbmsOutputMessage(connection);

        } catch (Exception e) {
            throw new RuntimeException("상벌점 기록 중 오류 발생: " + e.getMessage(), e);
        }

        return lastMessage != null ? lastMessage : "출력된 메시지가 없습니다.";
    }

    private String getLastDbmsOutputMessage(Connection connection) throws Exception {
        String lastMessage = null;
        try (CallableStatement readDbmsOutput = connection.prepareCall(
                "DECLARE " +
                        "    line VARCHAR2(4000); " +
                        "    status INTEGER; " +
                        "BEGIN " +
                        "    LOOP " +
                        "        DBMS_OUTPUT.GET_LINE(line, status); " +
                        "        EXIT WHEN status != 0; " +
                        "        ? := line; " +
                        "    END LOOP; " +
                        "END;")) {

            readDbmsOutput.registerOutParameter(1, java.sql.Types.VARCHAR);
            while (true) {
                readDbmsOutput.execute();
                String line = readDbmsOutput.getString(1);
                if (line == null) break;
                lastMessage = line; // 마지막 메시지만 저장
            }
        }
        return lastMessage;
    }
}