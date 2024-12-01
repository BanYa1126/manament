package dormitory_student_management.management;

import dormitory_student_management.management.service.DormitoryProcedureCaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    @Bean
    public DormitoryProcedureCaller dormitoryProcedureCaller(JdbcTemplate jdbcTemplate) {
        return new DormitoryProcedureCaller(jdbcTemplate);
    }
}