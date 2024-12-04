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
    public StudentDormitoryService studentDormitoryService() {
        return new StudentDormitoryService(studentDormitoryRepository());
    }

    @Bean
    public StudentDormitoryOutRepository studentDormitoryOutRepository() {
        return new StudentDormitoryOutRepository(jdbcTemplate());
    }

    @Bean
    public StudentDormitoryOutService studentDormitoryOutService(StudentDormitoryOutRepository studentDormitoryOutRepository) {
        return new StudentDormitoryOutService(studentDormitoryOutRepository());
    }

    @Bean
    public AdministratorViewRepository administratorViewRepository() {
        return new AdministratorViewRepository();
    }

    @Bean
    public AdministratorViewService administratorViewService(AdministratorViewRepository administratorViewRepository) {
        return new AdministratorViewService(administratorViewRepository);
    }
}
