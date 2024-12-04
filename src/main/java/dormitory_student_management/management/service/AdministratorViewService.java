package dormitory_student_management.management.service;

import dormitory_student_management.management.repository.AdministratorViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorViewService {

    private final AdministratorViewRepository administratorViewRepository;

    @Autowired
    public AdministratorViewService(AdministratorViewRepository administratorViewRepository) {
        this.administratorViewRepository = administratorViewRepository;
    }

    public String getTodayDutyManager() throws Exception {
        return administratorViewRepository.findTodayDutyManager()
                .orElse("정보 없음"); // 당직자가 없을 경우 기본 메시지 반환
    }
}
