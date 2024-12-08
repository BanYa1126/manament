package dormitory_student_management.management.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class StudentDormitoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public StudentDormitoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String assignDormitoryByProcedure(int studentId) {
        String procedureCall = "{CALL 기숙사_입사_프로시져(?)}";
        String lastMessage = null;

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             CallableStatement enableDbmsOutput = connection.prepareCall("BEGIN DBMS_OUTPUT.ENABLE(1000000); END;");
             CallableStatement callProcedure = connection.prepareCall(procedureCall);
             CallableStatement readDbmsOutput = connection.prepareCall(
                     "DECLARE " +
                             "    line VARCHAR2(4000); " +
                             "    status INTEGER; " +
                             "BEGIN " +
                             "    LOOP " +
                             "        DBMS_OUTPUT.GET_LINE(line, status); " +
                             "        EXIT WHEN status != 0; " +
                             "        ? := line; " +
                             "    END LOOP; " +
                             "END;"
             )) {

            // DBMS_OUTPUT 활성화
            enableDbmsOutput.execute();

            // 프로시저 호출
            callProcedure.setInt(1, studentId);
            callProcedure.execute();

            // DBMS_OUTPUT 읽기
            readDbmsOutput.registerOutParameter(1, java.sql.Types.VARCHAR);
            while (true) {
                readDbmsOutput.execute();
                String line = readDbmsOutput.getString(1);
                if (line == null) break;
                lastMessage = line; // 마지막 메시지만 저장
            }

        } catch (Exception e) {
            throw new RuntimeException("기숙사 배정 프로시저 실행 중 오류 발생: " + e.getMessage(), e);
        }

        return lastMessage != null ? lastMessage : "출력된 메시지가 없습니다.";
    }

    public void setDepartureDate(int studentId, int days) {
        String updateQuery = "UPDATE 학생 " +
                "SET 퇴사일 = 입사일 + NUMTODSINTERVAL(?, 'DAY') " +
                "WHERE 학번 = ?";
        jdbcTemplate.update(updateQuery, days, studentId);
    }
}