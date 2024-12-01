package dormitory_student_management.management.controller;

import dormitory_student_management.management.domain.Dormitory;
import dormitory_student_management.management.service.DormitoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DormitoryViewController {

    private final DormitoryService dormitoryService;

    public DormitoryViewController(DormitoryService dormitoryService) {
        this.dormitoryService = dormitoryService;
    }

    @GetMapping("/dormitories")
    public Map<String, Object> getDormitoryStatus() {
        List<Dormitory> dormitories = dormitoryService.getAllDormitories();
        Map<Integer, List<Integer>> studentAssignments = dormitoryService.getStudentAssignments();

        System.out.println("Dormitories: " + dormitories);
        System.out.println("Student Assignments: " + studentAssignments);

        return Map.of(
                "dormitories", dormitories,
                "studentAssignments", studentAssignments
        );
    }
}