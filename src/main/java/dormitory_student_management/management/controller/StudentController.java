package dormitory_student_management.management.controller;

import dormitory_student_management.management.domain.Student;
import dormitory_student_management.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // 학번으로 학생 정보 조회 (JSON 응답)
    @GetMapping("/search/by-id")
    public List<Student> getStudentDetailsByStudentId(@RequestParam Integer studentId) {
        List<Student> students = studentService.getStudentDetailsAndRewardsByStudentId(studentId);
        if (!students.isEmpty()) {
            Student student = students.get(0);
            if (student.getDormitory() != null) {
                List<Student> roommates = studentService.getRoommates(student.getDormitory().getRoomNumber(), studentId);
                student.getDormitory().setRoommates(roommates);
            }
        }
        return students;
    }

    // 주소로 학생 정보 조회 (JSON 응답)
    @GetMapping("/search/by-address")
    public List<Student> getStudentsByAddress(@RequestParam String address) {
        List<Student> students = studentService.getStudentsWithDetailsAndRewardsByAddress(address);
        for (Student student : students) {
            if (student.getDormitory() != null) {
                List<Student> roommates = studentService.getRoommates(student.getDormitory().getRoomNumber(), student.getStudentId());
                student.getDormitory().setRoommates(roommates);
            }
        }
        return students;
    }
}