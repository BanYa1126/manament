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

    @GetMapping("/out-Of-Student")
    public String showOutOfStudentPage() {
        // outOfStudent.html 반환
        return "outOfStudent";
    }

    @GetMapping("/move-Student")
    public String showmoveStudentPage() {
        // outOfStudent.html 반환
        return "moveStudent";
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
    @GetMapping("/view-time-record")
    public String showInfoChangePage() {
        return "viewTimeRecord"; // templates/viewTimeRecord.html
    }
}