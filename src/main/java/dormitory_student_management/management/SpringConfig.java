package dormitory_student_management.management;

import dormitory_student_management.management.repository.Dormitory.DormitoryRepository;
import dormitory_student_management.management.repository.Dormitory.JPADormitoryRepository;
import dormitory_student_management.management.service.DormitoryService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final EntityManager entityManager;

    @Autowired
    public SpringConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    public DormitoryRepository dormitoryRepository() {
        return new JPADormitoryRepository(entityManager);
    }

    @Bean
    public DormitoryService dormitoryService() {
        return new DormitoryService(dormitoryRepository());
    }
}