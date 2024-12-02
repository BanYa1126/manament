package dormitory_student_management.management.service;

import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class DormitoryDepartureDateSetter {

    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521/XE";
    private static final String JDBC_USER = "admin";
    private static final String JDBC_PASSWORD = "1234";

    public void setDepartureDate(int studentId, int days) {
        // 퇴사일 설정 SQL 쿼리
        String updateStudentQuery = "UPDATE 학생 " +
                "SET 퇴사일 = DATE_ADD(입사일, INTERVAL ? DAY) " +
                "WHERE 학번 = ?";

        Connection connection = null;

        try {
            // 데이터베이스 연결
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // 트랜잭션 시작
            connection.setAutoCommit(false);

            // 퇴사일 설정
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateStudentQuery)) {
                preparedStatement.setInt(1, days);       // 퇴사일 간격 (days)
                preparedStatement.setInt(2, studentId); // 학번

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected == 0) {
                    throw new SQLException("학번 " + studentId + "번 학생을 찾을 수 없습니다.");
                }
            }

            // 커밋
            connection.commit();
            System.out.println("학번 " + studentId + "번 학생의 퇴사일이 성공적으로 설정되었습니다.");

        } catch (SQLException e) {
            e.printStackTrace();

            // 롤백
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }

            throw new RuntimeException("퇴사일 설정 중 오류 발생: " + e.getMessage(), e);

        } finally {
            // 연결 닫기
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }
}
