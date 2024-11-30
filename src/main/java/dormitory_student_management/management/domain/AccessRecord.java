package dormitory_student_management.management.domain;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "출입")
public class AccessRecord {

    @Id
    @Column(name = "시간")
    private Timestamp time;
    private String status;
    private Boolean overnightStatus;
    @ManyToOne
    @JoinColumn
    private Student student;
    @ManyToOne
    @JoinColumn(name = "관리자번호")
    private Administrator administrator;

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getOvernightStatus() {
        return overnightStatus;
    }

    public void setOvernightStatus(Boolean overnightStatus) {
        this.overnightStatus = overnightStatus;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }
}