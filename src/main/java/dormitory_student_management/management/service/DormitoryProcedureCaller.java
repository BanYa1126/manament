package dormitory_student_management.management.service;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;

@Service
public class DormitoryProcedureCaller {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    // JDBC를 사용하여 기숙사 입사 프로시저 호출
    public void assignDormitory(int studentId) {
        Connection connection = null;
        CallableStatement callableStatement = null;

        try {
            // Oracle DB 연결
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // 프로시저 호출 준비
            String procedureCall = "{CALL 기숙사_입사_프로시져(?)}";
            callableStatement = connection.prepareCall(procedureCall);

            // 매개변수 설정
            callableStatement.setInt(1, studentId);

            // 프로시저 실행
            callableStatement.execute();
            System.out.println("프로시저 호출 성공: 학번 " + studentId + "번 학생의 기숙사 배정 완료.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("프로시저 호출 실패: " + e.getMessage());
        } finally {
            try {
                if (callableStatement != null) callableStatement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}