public class UserDTO {
    private String studentName;
    private String studentID;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    @Override
    public String toString() {
        return "userDTO{" +
                "studentName='" + studentName + '\'' +
                ", studentID='" + studentID + '\'' +
                '}';
    }
}
