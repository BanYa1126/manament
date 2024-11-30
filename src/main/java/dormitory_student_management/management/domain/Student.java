package dormitory_student_management.management.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Student {

    @Id
    @Column
    private Integer studentId;
    private String name;
    private String address;
    private String contact;
    private LocalDate entryDate;
    private LocalDate exitDate;
    private Boolean accessStatus;
    private Integer overnightCount;
    @ManyToOne
    @JoinColumn
    private Dormitory dormitory;

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