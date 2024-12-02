package dormitory_student_management.management.controller;

import dormitory_student_management.management.domain.Dormitory;
import dormitory_student_management.management.service.DormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class DormitoryViewController {

    private final DormitoryService dormitoryService;

    @Autowired
    public DormitoryViewController(DormitoryService dormitoryService) {
        this.dormitoryService = dormitoryService;
    }

    @GetMapping("/start")
    public String showDormitoryStatusPage(Model model) {
        List<Dormitory> dormitories = dormitoryService.getAllDormitories();
        model.addAttribute("dormitories", dormitories);
        return "start"; // templates/start.html
    }
}