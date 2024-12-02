package dormitory_student_management.management.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="기숙사")
public class Dormitory {

    @Id
    @Column(name="방번호")
    private Integer roomNumber;
    @Column(name="배정인원")
    private Integer assignedPeople;

    // 기본 생성자
    public Dormitory() {
    }

    // 파라미터가 있는 생성자
    public Dormitory(Integer roomNumber, Integer assignedPeople) {
        this.roomNumber = roomNumber;
        this.assignedPeople = assignedPeople;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getAssignedPeople() {
        return assignedPeople;
    }

    public void setAssignedPeople(Integer assignedPeople) {
        this.assignedPeople = assignedPeople;
    }
}