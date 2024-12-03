package dormitory_student_management.management.repository;

import dormitory_student_management.management.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // 특정 방에 배정된 학생 조회
    List<Student> findByDormitoryRoomNumber(Integer roomNumber);

    // 학번으로 특정 학생 정보 조회
    @Query("SELECT s FROM Student s " +
            "LEFT JOIN FETCH s.dormitory d " +
            "WHERE s.studentId = :studentId AND s.entryDate IS NOT NULL")
    List<Student> findStudentWithDetailsByStudentId(@Param("studentId") Integer studentId);

    // 주소 기반으로 특정 학생 정보 조회
    @Query("SELECT DISTINCT s FROM Student s " +
            "LEFT JOIN FETCH s.dormitory d " +
            "WHERE LOWER(s.address) LIKE LOWER(CONCAT('%', :address, '%')) AND s.entryDate IS NOT NULL")
    List<Student> findStudentsWithDetailsByAddress(@Param("address") String address);

    // 특정 방 번호의 룸메이트 조회
    @Query("SELECT s FROM Student s WHERE s.dormitory.roomNumber = :roomNumber AND s.studentId <> :studentId")
    List<Student> findRoommatesByRoomNumber(@Param("roomNumber") Integer roomNumber,
                                            @Param("studentId") Integer studentId);
}