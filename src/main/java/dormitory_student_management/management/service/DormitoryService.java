package dormitory_student_management.management.service;

import dormitory_student_management.management.domain.Dormitory;
import dormitory_student_management.management.repository.Dormitory.DormitoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class DormitoryService {

    private final DormitoryRepository dormitoryRepository;

    @Autowired
    public DormitoryService(DormitoryRepository dormitoryRepository) {
        this.dormitoryRepository = dormitoryRepository;
    }

    public List<Dormitory> getAllDormitories() {
        return dormitoryRepository.findAll();
    }
}
