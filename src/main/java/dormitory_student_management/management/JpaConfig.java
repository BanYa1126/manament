package dormitory_student_management.management;

import dormitory_student_management.management.repository.StudentRepository;
import dormitory_student_management.management.service.StudentService;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class JpaConfig {

    private final EntityManager entityManager;

    public JpaConfig(@Lazy EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Bean
    public StudentService studentService(StudentRepository studentRepository) {
        return new StudentService(studentRepository);
    }
}
