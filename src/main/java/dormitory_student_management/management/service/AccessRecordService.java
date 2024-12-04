package dormitory_student_management.management.service;

import dormitory_student_management.management.domain.AccessRecord;
import dormitory_student_management.management.repository.AccessRecordRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AccessRecordService {
    private final AccessRecordRepository accessRecordRepository;

    public AccessRecordService(AccessRecordRepository accessRecordRepository) {
        this.accessRecordRepository = accessRecordRepository;
    }

    public List<AccessRecord> getRecordsByStudentId(String studentId) {
        return accessRecordRepository.findByStudentId(studentId);
    }

    public List<AccessRecord> getRecordsByStudentIdAndTimeRange(String studentId, Timestamp startTime, Timestamp endTime) {
        return accessRecordRepository.findByStudentIdAndTimeRange(studentId, startTime, endTime);
    }
}