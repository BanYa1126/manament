package dormitory_student_management.management.controller;

import dormitory_student_management.management.domain.AccessRecord;
import dormitory_student_management.management.service.AccessRecordService;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
            @RequestParam(required = false) Timestamp startTime,
            @RequestParam(required = false) Timestamp endTime
    ) {
        List<AccessRecord> records;
        if (startTime != null && endTime != null) {
            records = accessRecordService.getRecordsByStudentIdAndTimeRange(id, startTime, endTime);
        } else {
            records = accessRecordService.getRecordsByStudentId(id);
        }

        if (records == null || records.isEmpty()) {
            return List.of();
        }

        return records.stream()
                .map(record -> (List<Object>) List.of(
                        record.getTime() != null ? record.getTime().toString() : null,
                        record.getStatus(),
                        record.getOvernightStatus(),
                        record.getStudent() != null ? record.getStudent().getStudentId() : null,
                        record.getStudent() != null ? record.getStudent().getName() : null,
                        record.getAdministrator() != null ? record.getAdministrator().getAdminId() : null,
                        record.getAdministrator() != null ? record.getAdministrator().getName() : null
                ))
                .collect(Collectors.toList());
    }
}