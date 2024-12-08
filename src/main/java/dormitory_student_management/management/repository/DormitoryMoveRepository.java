package dormitory_student_management.management.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;
import org.springframework.stereotype.Repository;

@Repository
public class DormitoryMoveRepository {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe"; // Oracle DB URL
    private static final String DB_USER = "admin"; // DB 사용자명
    private static final String DB_PASSWORD = "1234"; // DB 비밀번호

    public String callDormitoryMoveProcedure(int studentId) throws Exception {
        Connection connection = null;
        CallableStatement callableStatement = null;
        String lastMessage = null;

        try {
            // 데이터베이스 연결
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // DBMS_OUTPUT 활성화
            try (CallableStatement enableDbmsOutput = connection.prepareCall("BEGIN DBMS_OUTPUT.ENABLE(1000000); END;")) {
                enableDbmsOutput.execute();
            }

            // 프로시저 호출
            String sql = "{CALL 기숙사_방이동_프로시져(?)}";
            callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, studentId);
            callableStatement.execute();

            // DBMS_OUTPUT 메시지 읽기
            lastMessage = getLastDbmsOutputMessage(connection);

        } catch (Exception e) {
            throw new Exception("기숙사 방 이동 프로시저 호출 중 오류 발생: " + e.getMessage());
        } finally {
            // 자원 정리
            if (callableStatement != null) callableStatement.close();
            if (connection != null) connection.close();
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