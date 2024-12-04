package dormitory_student_management.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="학생")
public class Student {

    @Id
    @Column(name="학번")
    private Integer studentId;

    @Column(name="이름")
    private String name;

    @Column(name="주소")
    private String address;

    @Column(name="연락처")
    private String contact;

    @Column(name="입사일")
    private LocalDate entryDate;

    @Column(name="퇴사일")
    private LocalDate exitDate;

    @Column(name="출입여부")
    private Boolean accessStatus;

    @Column(name="외박횟수")
    private Integer overnightCount;

    @ManyToOne
    @JoinColumn(name="방번호")
    @JsonIgnoreProperties({"students"})
    private Dormitory dormitory;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"student"})
    private RewardPenalty rewardPenalty; // 상벌점 필드 추가

    // Getters and Setters
    public RewardPenalty getRewardPenalty() {
        return rewardPenalty;
    }

    public void setRewardPenalty(RewardPenalty rewardPenalty) {
        this.rewardPenalty = rewardPenalty;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDate getExitDate() {
        return exitDate;
    }

    public void setExitDate(LocalDate exitDate) {
        this.exitDate = exitDate;
    }

    public Boolean getAccessStatus() {
        return accessStatus;
    }

    public void setAccessStatus(Boolean accessStatus) {
        this.accessStatus = accessStatus;
    }

    public Integer getOvernightCount() {
        return overnightCount;
    }

    public void setOvernightCount(Integer overnightCount) {
        this.overnightCount = overnightCount;
    }

    public Dormitory getDormitory() {
        return dormitory;
    }

    public void setDormitory(Dormitory dormitory) {
        this.dormitory = dormitory;
    }
}