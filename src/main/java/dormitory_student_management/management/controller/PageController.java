package dormitory_student_management.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    // Student Admission 페이지
    @GetMapping("/student-admission")
    public String showStudentAdmissionPage() {
        return "studentAdmission"; // templates/studentAdmission.html
    }

    // View Student 페이지
    @GetMapping("/view-student")
    public String showViewStudentPage() {
        return "viewStudent"; // templates/viewStudent.html
    }

    // Reward and Punishment 페이지
    @GetMapping("/reward-punishment")
    public String showRewardPunishmentPage() {
        return "rewardPunishment"; // templates/rewardPunishment.html
    }

    // Info Change 페이지
    @GetMapping("/info-change")
    public String showInfoChangePage() {
        return "infoChange"; // templates/infoChange.html
    }
}