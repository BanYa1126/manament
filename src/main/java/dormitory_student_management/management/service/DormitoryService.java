package dormitory_student_management.management.service;

import dormitory_student_management.management.domain.Dormitory;
import dormitory_student_management.management.domain.Student;
import dormitory_student_management.management.repository.DormitoryRepository;
import dormitory_student_management.management.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DormitoryService {

    private final DormitoryRepository dormitoryRepository;
    private final StudentRepository studentRepository;

    public DormitoryService(DormitoryRepository dormitoryRepository, StudentRepository studentRepository) {
        this.dormitoryRepository = dormitoryRepository;
        this.studentRepository = studentRepository;
    }

    public List<Dormitory> getAllDormitories() {
        return dormitoryRepository.findAll();
    }

    public Map<Integer, List<Integer>> getStudentAssignments() {
        return studentRepository.findAll().stream()
                .filter(student -> student.getDormitory() != null)
                .collect(Collectors.groupingBy(
                        student -> student.getDormitory().getRoomNumber(),
                        Collectors.mapping(Student::getStudentId, Collectors.toList())
                ));
    }
}