package dormitory_student_management.management.controller;

import dormitory_student_management.management.domain.AccessRecord;
import dormitory_student_management.management.service.AccessRecordService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/getStudentInfo")
public class AccessRecordController {
    private final AccessRecordService accessRecordService;

    public AccessRecordController(AccessRecordService accessRecordService) {
        this.accessRecordService = accessRecordService;
    }

    @GetMapping
    public List<List<Object>> getAccessRecords(
            @RequestParam String id,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime
    ) {

        Timestamp startTimestamp = null;
        Timestamp endTimestamp = null;

        try {
            if (startTime != null) {
                startTimestamp = Timestamp.valueOf(LocalDateTime.parse(startTime));
            }
            if (endTime != null) {
                endTimestamp = Timestamp.valueOf(LocalDateTime.parse(endTime));
            }
        } catch (DateTimeParseException e) {
            throw new RuntimeException("시간 형식이 잘못되었습니다. 'YYYY-MM-DDTHH:mm' 형식을 사용하세요.", e);
        }

        List<AccessRecord> records = (startTimestamp != null && endTimestamp != null)
                ? accessRecordService.getRecordsByStudentIdAndTimeRange(id, startTimestamp, endTimestamp)
                : accessRecordService.getRecordsByStudentId(id);

        if (records == null || records.isEmpty()) {
            return new ArrayList<>();
        }

        System.out.println("Records found: " + records.size());
        return records.stream()
                .map(record -> {
                    List<Object> recordData = new ArrayList<>();
                    recordData.add(record.getTime() != null ? record.getTime().toString() : null);
                    recordData.add(record.getStatus());
                    recordData.add(record.getOvernightStatus());
                    recordData.add(record.getStudent() != null ? record.getStudent().getStudentId().toString() : null);
                    recordData.add(record.getStudent() != null ? record.getStudent().getName() : null);
                    recordData.add(record.getAdministrator() != null ? record.getAdministrator().getAdminId().toString() : null);
                    recordData.add(record.getAdministrator() != null ? record.getAdministrator().getName() : null);
                    return recordData;
                })
                .collect(Collectors.toList());
    }
}