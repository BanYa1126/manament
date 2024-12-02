package dormitory_student_management.management.repository.Dormitory;

import dormitory_student_management.management.domain.Dormitory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DormitoryRepository {
    Dormitory save(Dormitory dormitory);
    List<Dormitory> findAll();
}
