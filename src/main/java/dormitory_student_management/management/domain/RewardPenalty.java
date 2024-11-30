package dormitory_student_management.management.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "상벌점")
public class RewardPenalty {

    @Id
    @Column
    private Integer studentId;
    private Integer points;

    @OneToOne
    @MapsId // studentId를 Student의 기본키로 매핑
    @JoinColumn // 외래키 설정
    private Student student;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}