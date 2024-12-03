package dormitory_student_management.management.repository;

import dormitory_student_management.management.domain.Dormitory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DormitoryRepository {
    private final EntityManager em;

    public DormitoryRepository(EntityManager em) {
        this.em = em;
    }

    // Dormitory 엔티티 저장
    public Dormitory save(Dormitory dormitory) {
        em.persist(dormitory);
        return dormitory;
    }

    // Dormitory 목록 조회
    public List<Dormitory> findAll() {
        return em.createQuery("SELECT d FROM Dormitory d", Dormitory.class).getResultList();
    }
}
