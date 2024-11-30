package dormitory_student_management.management.domain;

import jakarta.persistence.*;

@Entity
@Table
public class Dormitory {

    @Id
    @Column
    private Integer roomNumber;
    private Integer assignedPeople;

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
