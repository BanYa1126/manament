package dormitory_student_management.management.service;

import dormitory_student_management.management.domain.Student;
import dormitory_student_management.management.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // Constructor for dependency injection
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // 특정 방 번호에 배정된 학생 목록 조회
    public List<Student> getStudentsByRoomNumber(Integer roomNumber) {
        return studentRepository.findByDormitoryRoomNumber(roomNumber);
    }

    // 학번으로 특정 학생 정보 조회
    public List<Student> getStudentDetailsByStudentId(Integer studentId) {
        return studentRepository.findStudentWithDetailsByStudentId(studentId);
    }

    // 주소 기반으로 특정 학생 정보 조회
    public List<Student> getStudentsByAddress(String address) {
        return studentRepository.findStudentsWithDetailsByAddress(address.trim().toLowerCase());
    }

    // 특정 방 번호의 룸메이트 조회
    public List<Student> getRoommates(Integer roomNumber, Integer studentId) {
        return studentRepository.findRoommatesByRoomNumber(roomNumber, studentId);
    }
}