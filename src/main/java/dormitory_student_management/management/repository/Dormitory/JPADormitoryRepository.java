package dormitory_student_management.management.repository.Dormitory;

import dormitory_student_management.management.domain.Dormitory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JPADormitoryRepository implements DormitoryRepository {
    private final EntityManager em;

    @Autowired
    public JPADormitoryRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Dormitory save(Dormitory dormitory) {
        em.persist(dormitory);
        return dormitory;
    }

    public List<Dormitory> findAll() {
        return em.createQuery("select m from Dormitory m", Dormitory.class)
                .getResultList();
    }
}