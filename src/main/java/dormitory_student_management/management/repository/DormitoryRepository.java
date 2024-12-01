package dormitory_student_management.management.repository;

import dormitory_student_management.management.domain.Dormitory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DormitoryRepository extends JpaRepository<Dormitory, Integer> {
}