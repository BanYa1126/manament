package dormitory_student_management.management;

import dormitory_student_management.management.repository.*;
import dormitory_student_management.management.service.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final EntityManager entityManager;
    private final DataSource dataSource;

    @Autowired
    public SpringConfig(EntityManager entityManager, DataSource dataSource) {
        this.entityManager = entityManager;
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DormitoryService dormitoryService() {
        return new DormitoryService(dormitoryRepository());
    }

    @Bean
    public DormitoryRepository dormitoryRepository() {
        return new DormitoryRepository(entityManager);
    }

    @Bean
    public PointService pointService() {
        return new PointService(pointRepository());
    }

    @Bean
    public PointRepository pointRepository() {
        return new PointRepository(jdbcTemplate());
    }

    @Bean
    public StudentDormitoryRepository studentDormitoryRepository() {
        return new StudentDormitoryRepository(jdbcTemplate());
    }

    @Bean
    public StudentDormitoryRepository2 studentDormitoryRepository2() {
        return new StudentDormitoryRepository2(jdbcTemplate());
    }

    @Bean
    public StudentDormitoryAssignmentService studentDormitoryService() {
        return new StudentDormitoryAssignmentService(studentDormitoryRepository());
    }

    @Bean
    public StudentDormitoryDepartureService StudentDormitoryDepartureService() {
        return new StudentDormitoryDepartureService(studentDormitoryRepository2());
    }
}
