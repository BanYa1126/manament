package dormitory_student_management.management.repository;

import dormitory_student_management.management.domain.AccessRecord;

import java.sql.Timestamp;
import java.util.List;

public interface AccessRecordRepository {
    List<AccessRecord> findByStudentId(String studentId);
    List<AccessRecord> findByStudentIdAndTimeRange(String studentId, Timestamp startTime, Timestamp endTime);
}