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

    public void callDormitoryMoveProcedure(int studentId) throws Exception {
        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            // 데이터베이스 연결
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // 프로시저 호출 준비
            String sql = "{CALL 기숙사_방이동_프로시져(?)}";
            callableStatement = connection.prepareCall(sql);

            // 입력 파라미터 설정
            callableStatement.setInt(1, studentId);

            // 프로시저 실행
            callableStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("기숙사 방 이동 프로시저 호출 중 오류 발생: " + e.getMessage());
        } finally {
            // 자원 정리
            if (callableStatement != null) {
                callableStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}

