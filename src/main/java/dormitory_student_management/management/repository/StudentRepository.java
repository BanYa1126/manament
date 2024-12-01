package dormitory_student_management.management.repository;

import dormitory_student_management.management.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // 방 번호가 있는 학생만 조회
    @Query("SELECT s FROM Student s WHERE s.dormitory IS NOT NULL")
    List<Student> findStudentsWithDormitory();
}