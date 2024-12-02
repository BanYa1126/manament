//package dormitory_student_management.management.controller;
//
//import dormitory_student_management.management.domain.Student;
//import dormitory_student_management.management.service.StudentService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class StudentController {
//
//    private final StudentService studentService;
//
//    public StudentController(StudentService studentService) {
//        this.studentService = studentService;
//    }
//
//    // 방 번호가 있는 학생 조회
//    @GetMapping("/students-with-dormitory")
//    public List<Student> getStudentsWithDormitory() {
//        return studentService.getStudentsWithDormitory();
//    }
//}
