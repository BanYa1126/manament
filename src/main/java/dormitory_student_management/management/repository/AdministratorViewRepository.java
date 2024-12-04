package dormitory_student_management.management.repository;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

@Repository
public class AdministratorViewRepository {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe"; // DB URL
    private static final String DB_USER = "admin"; // DB 사용자 이름
    private static final String DB_PASSWORD = "1234"; // DB 비밀번호

    public Optional<String> findTodayDutyManager() throws Exception {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String sql = "SELECT 이름 FROM 관리자 WHERE 당직 = 1";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return Optional.ofNullable(resultSet.getString("이름"));
            } else {
                return Optional.empty();
            }

        } finally {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        }
    }
}
