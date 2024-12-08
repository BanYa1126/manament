package dormitory_student_management.management.repository;

import dormitory_student_management.management.domain.AccessRecord;
import dormitory_student_management.management.domain.Administrator;
import dormitory_student_management.management.domain.Student;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JDBCAccessRecordRepository implements AccessRecordRepository {

    private final DataSource dataSource;

    public JDBCAccessRecordRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<AccessRecord> findByStudentId(String studentId) {
        String query = "SELECT 출입.시간, 출입.상태, 출입.외박여부, 출입.학번, 출입.관리자번호, " +
                "학생.이름 AS 학생이름, 관리자.이름 AS 관리자이름 " +
                "FROM 출입 " +
                "JOIN 학생 ON 출입.학번 = 학생.학번 " +
                "JOIN 관리자 ON 출입.관리자번호 = 관리자.관리자번호 " +
                "WHERE 출입.학번 = ?";
        return executeQuery(query, preparedStatement -> preparedStatement.setString(1, studentId));
    }

    @Override
    public List<AccessRecord> findByTimeRange(Timestamp startTime, Timestamp endTime) {
        String query = "SELECT 출입.시간, 출입.상태, 출입.외박여부, 출입.학번, 출입.관리자번호, " +
                "학생.이름 AS 학생이름, 관리자.이름 AS 관리자이름 " +
                "FROM 출입 " +
                "JOIN 학생 ON 출입.학번 = 학생.학번 " +
                "JOIN 관리자 ON 출입.관리자번호 = 관리자.관리자번호 " +
                "WHERE 출입.시간 BETWEEN ? AND ?";
        return executeQuery(query, preparedStatement -> {
            preparedStatement.setTimestamp(1, startTime);
            preparedStatement.setTimestamp(2, endTime);
        });
    }

    private List<AccessRecord> executeQuery(String query, QueryPreparer preparer) {
        List<AccessRecord> accessRecords = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparer.prepare(preparedStatement);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    AccessRecord record = new AccessRecord();
                    record.setTime(resultSet.getTimestamp("시간"));
                    record.setStatus(resultSet.getString("상태"));
                    record.setOvernightStatus(resultSet.getBoolean("외박여부"));

                    Student student = new Student();
                    student.setStudentId(resultSet.getInt("학번"));
                    student.setName(resultSet.getString("학생이름"));

                    record.setStudent(student);

                    Administrator administrator = new Administrator();
                    administrator.setAdminId(resultSet.getInt("관리자번호"));
                    administrator.setName(resultSet.getString("관리자이름"));

                    record.setAdministrator(administrator);

                    accessRecords.add(record);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 조회 중 오류 발생", e);
        }
        return accessRecords;
    }

    @FunctionalInterface
    private interface QueryPreparer {
        void prepare(PreparedStatement preparedStatement) throws SQLException;
    }
}