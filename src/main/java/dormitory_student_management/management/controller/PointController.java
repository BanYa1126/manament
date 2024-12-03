package dormitory_student_management.management.controller;

import dormitory_student_management.management.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, Object>> addPoints(@RequestBody Map<String, Object> requestData) {
        try {
            // JSON에서 값 추출
            int studentId = (int) requestData.get("studentId");
            String pointType = (String) requestData.get("pointType");
            int points = (int) requestData.get("points");

            // 점수 타입에 따라 + 또는 - 설정
            int adjustedPoints = "reward".equalsIgnoreCase(pointType) ? points : -points;

            // 상벌점 처리 서비스 호출
            pointService.recordPoints(studentId, adjustedPoints);

            // 성공 응답 반환
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "학번 " + studentId + "번 학생의 " +
                            ("reward".equalsIgnoreCase(pointType) ? "상점" : "벌점") +
                            " " + points + "점이 성공적으로 처리되었습니다."
            ));
        } catch (Exception e) {
            // 오류 응답 반환
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "점수 처리 중 오류 발생: " + e.getMessage()
            ));
        }
    }
}