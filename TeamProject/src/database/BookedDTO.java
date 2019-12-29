public class BookedDTO extends DB_DTO{
    private String studentID;
    private String studentName;
    private int isProject;//true =1, false = 0
    private int maxPeople;
    private String date;
    private String building;
    private String roomNumber;

    @Override
    public String getBuilding() {
        return building;
    }

    @Override
    public void setBuilding(String building) {
        this.building = building;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
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

    @Override
    public int getIsProject() {
        return isProject;
    }

    @Override
    public void setIsProject(int isProject) {
        this.isProject = isProject;
    }

    @Override
    public int getMaxPeople() {
        return maxPeople;
    }

    @Override
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
