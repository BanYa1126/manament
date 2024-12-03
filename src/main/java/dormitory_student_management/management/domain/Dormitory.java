package dormitory_student_management.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="기숙사")
public class Dormitory {

    @Id
    @Column(name="방번호")
    private Integer roomNumber;
    @Column(name="배정인원")
    private Integer assignedPeople;

    // 룸메이트 필드 추가
    @Transient // 데이터베이스에 저장되지 않음
    @JsonIgnoreProperties({"dormitory"})
    private List<Student> roommates;

    // Getter 및 Setter
    public List<Student> getRoommates() {
        return roommates;
    }

    public void setRoommates(List<Student> roommates) {
        this.roommates = roommates;
    }
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