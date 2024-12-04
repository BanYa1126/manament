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

    // 상벌점 데이터를 포함한 학생 정보 조회
    @Query("SELECT s FROM Student s " +
            "LEFT JOIN FETCH s.dormitory d " +
            "LEFT JOIN FETCH s.rewardPenalty rp " +  // 상벌점 연관 데이터 가져오기
            "WHERE s.studentId = :studentId AND s.entryDate IS NOT NULL")
    List<Student> findStudentWithDetailsAndRewardsByStudentId(@Param("studentId") Integer studentId);

    // 주소 기반으로 상벌점 포함 조회
    @Query("SELECT DISTINCT s FROM Student s " +
            "LEFT JOIN FETCH s.dormitory d " +
            "LEFT JOIN FETCH s.rewardPenalty rp " +  // 상벌점 연관 데이터 가져오기
            "WHERE LOWER(s.address) LIKE LOWER(CONCAT('%', :address, '%')) AND s.entryDate IS NOT NULL")
    List<Student> findStudentsWithDetailsAndRewardsByAddress(@Param("address") String address);

    // 특정 방 번호의 룸메이트 조회
    @Query("SELECT s FROM Student s WHERE s.dormitory.roomNumber = :roomNumber AND s.studentId <> :studentId")
    List<Student> findRoommatesByRoomNumber(@Param("roomNumber") Integer roomNumber,
                                            @Param("studentId") Integer studentId);
}