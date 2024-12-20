package dormitory_student_management.management.domain;

import jakarta.persistence.*;

@Entity
@Table(name="관리자")
public class Administrator {

    @Id
    @Column(name="관리자번호")
    private Integer adminId;
    @Column(name="이름")
    private String name;
    @Column(name="나이")
    private Integer age;
    @Column(name="입사년도")
    private Integer yearOfEntry;
    @Column(name="근무년수")
    private Integer yearsOfService;
    @Column(name="사무실전화번호")
    private String officePhoneNumber;
    @Column(name="사무실이메일")
    private String officeEmail;
    @Column(name="근무형태")
    private String workType;
    @Column(name="근무시간대")
    private String workShift;

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getYearOfEntry() {
        return yearOfEntry;
    }

    public void setYearOfEntry(Integer yearOfEntry) {
        this.yearOfEntry = yearOfEntry;
    }

    public Integer getYearsOfService() {
        return yearsOfService;
    }

    public void setYearsOfService(Integer yearsOfService) {
        this.yearsOfService = yearsOfService;
    }

    public String getOfficePhoneNumber() {
        return officePhoneNumber;
    }

    public void setOfficePhoneNumber(String officePhoneNumber) {
        this.officePhoneNumber = officePhoneNumber;
    }

    public String getOfficeEmail() {
        return officeEmail;
    }

    public void setOfficeEmail(String officeEmail) {
        this.officeEmail = officeEmail;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getWorkShift() {
        return workShift;
    }

    public void setWorkShift(String workShift) {
        this.workShift = workShift;
    }
}