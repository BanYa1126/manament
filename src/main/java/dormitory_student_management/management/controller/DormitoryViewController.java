package dormitory_student_management.management.controller;

import dormitory_student_management.management.domain.Dormitory;
import dormitory_student_management.management.domain.Student;
import dormitory_student_management.management.service.AdministratorViewService;
import dormitory_student_management.management.service.DormitoryService;
import dormitory_student_management.management.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DormitoryViewController {

    private final DormitoryService dormitoryService;
    private final StudentService studentService;
    private final AdministratorViewService administratorViewService;

    @Autowired
    public DormitoryViewController(DormitoryService dormitoryService, StudentService studentService, AdministratorViewService administratorViewService) {
        this.dormitoryService = dormitoryService;
        this.studentService = studentService;
        this.administratorViewService = administratorViewService;
    }

    @GetMapping("/start")
    public String showDormitoryStatusPage(Model model) {
        List<Dormitory> dormitories = dormitoryService.getAllDormitories();

        // 총원 계산
        int totalAssignedPeople = dormitories.stream()
                .mapToInt(Dormitory::getAssignedPeople)
                .sum();

        // 학생 정보를 방 번호별로 저장
        Map<Integer, List<Student>> studentsByRoom = new HashMap<>();
        for (Dormitory dormitory : dormitories) {
            studentsByRoom.put(dormitory.getRoomNumber(),
                    studentService.getStudentsByRoomNumber(dormitory.getRoomNumber()));
        }

        // 오늘의 당직자 가져오기
        String dutyManager;
        try {
            dutyManager = administratorViewService.getTodayDutyManager();
        } catch (Exception e) {
            dutyManager = "정보 없음"; // 예외 발생 시 기본 메시지
        }

        // 모델에 데이터 추가
        model.addAttribute("dormitories", dormitories);
        model.addAttribute("studentsByRoom", studentsByRoom);
        model.addAttribute("totalAssignedPeople", totalAssignedPeople); // 총원 추가
        model.addAttribute("dutyManager", dutyManager); // 당직자 추가

        return "start";
    }
}