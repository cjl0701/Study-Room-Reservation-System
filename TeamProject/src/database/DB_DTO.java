import java.sql.Date;

public class DB_DTO {
    //모든정보를 담을때 쓰는 클래스
    private String studentID;
    private String studentName;
    private int isProject;//true =1, false = 0
    private int maxPeople;
    private String date;
    private String building;
    private String roomNumber;


    public String getBuilding() {
        return building;
    }


    public void setBuilding(String building) {
        this.building = building;
    }


    public String getRoomNumber() {
        return roomNumber;
    }


    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }


    public int getIsProject() {
        return isProject;
    }

    public void setIsProject(int isProject) {
        this.isProject = isProject;
    }

    public int getMaxPeople() {
        return maxPeople;
    }


    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.building+" ");
        sb.append(this.roomNumber+" ");
        sb.append(this.studentID+" ");
        sb.append(this.studentName+" ");
        sb.append(this.date+" ");
        sb.append(this.isProject+" ");
        sb.append(this.maxPeople+" ");

        return sb.toString();
    }

}
