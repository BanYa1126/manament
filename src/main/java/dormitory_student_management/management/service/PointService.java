package dormitory_student_management.management.service;

import dormitory_student_management.management.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    private final PointRepository pointRepository;

    @Autowired
    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public String recordPoints(int studentId, int points) {
        return pointRepository.recordPoints(studentId, points);
    }
}