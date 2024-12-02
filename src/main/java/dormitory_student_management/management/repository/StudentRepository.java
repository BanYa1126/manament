package dormitory_student_management.management.repository;

import dormitory_student_management.management.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // 특정 방에 배정된 학생 조회
    List<Student> findByDormitoryRoomNumber(Integer roomNumber);
}
