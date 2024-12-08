package dormitory_student_management.management.controller;

import dormitory_student_management.management.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/points")
public class PointController {

    private final PointService pointService;

    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @PostMapping
    public String addPoints(@RequestBody Map<String, Object> requestData) {
        try {
            // JSON에서 값 추출
            int studentId = (int) requestData.get("studentId");
            String pointType = (String) requestData.get("pointType");
            int points = (int) requestData.get("points");

            // 점수 타입에 따라 + 또는 - 설정
            int adjustedPoints = "reward".equalsIgnoreCase(pointType) ? points : -points;

            // 상벌점 처리 서비스 호출 및 메시지 반환
            return pointService.recordPoints(studentId, adjustedPoints);

        } catch (Exception e) {
            return "점수 처리 중 오류 발생: " + e.getMessage();
        }
    }
}