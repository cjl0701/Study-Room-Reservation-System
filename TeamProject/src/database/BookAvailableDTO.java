public class BookAvailableDTO extends  DB_DTO {
    //이 클래스는 예약가능한 데이터를 가져올때 사용하는 클래스
    private String building;
    private String roomNumber;
    private int isProject;//true =1, false = 0
    private int maxPeople;

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

    @Override
    public int getIsProject() {
        return isProject;
    }

    @Override
    public void setIsProject(int isProejct) {
        this.isProject = isProejct;
    }

    @Override
    public int getMaxPeople() {
        return maxPeople;
    }

    @Override
    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.building + " ");
        sb.append(this.roomNumber + " ");
        sb.append(this.isProject+" ");
        sb.append(this.maxPeople+" ");
        return sb.toString();
    }
}
