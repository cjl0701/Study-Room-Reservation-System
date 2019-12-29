import java.sql.*;
import java.util.ArrayList;


public class DB_DAO{
    //String jdbcDriver = "com.mysql.jdbc.Driver";
    String jdbcUrl = "jdbc:mysql://127.0.0.1/javadb?serverTimezone=UTC";
    Connection conn;

    PreparedStatement pstmt;
    ResultSet resultSet;

    //mData는 현재 예약된 모든 목록을 저장하는 ArrayList, getAll메소드에 의해 저장
    ArrayList<DB_DTO> mData = new ArrayList<>();
    //bookedListByClient는 해당 사용자가 예약한 목록을 저장하는 ArrayList, getBookedListByClient메소드에 의해 저장
    ArrayList<DB_DTO> bookedListByClient = new ArrayList<>();
    //bookAvailableList는 예약 가능한 목록을 저장하는 ArrayList, getBookAvailableList메소드에 의해 저장
    ArrayList<DB_DTO> bookAvailableList = new ArrayList<>();


    String sql;

    //다음 실행될 저장을 위해 List를 초기화시키는 메소드
    void clearmData(){ mData.clear();}
    void clearBookedListByClient(){bookedListByClient.clear();}
    void clearBookAvailableList(){bookAvailableList.clear();}

    DB_DAO(){ }

    //LostAndFound 테이블 구조
    //name whereFound registeredTime comment

    //분실물 등록 메서드
    //분실물의 이름, 찾은 장소, 분실물에대한 코멘트
    public void registerLost(String name, String whereFound, String comment){
        connectDB();

        String sql = "insert into LostAndFound(name, whereFound, comment) values(?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setString(2, whereFound);
            pstmt.setString(3, comment);

            pstmt.executeUpdate();
            closeDB();
        } catch (SQLException e) {
            e.printStackTrace();
            closeDB();
        }

    }

    //분실물 삭제 메서드
    //파라미터로 registeredTime을 넣으면 된다.
    public void deleteLost(String time){
        connectDB();
        String sql = "delete from lostandfound where registeredTime = date_format(?, '%Y-%m-%d %H:%i:%s')";
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, time);
            pstmt.executeUpdate();
            closeDB();
        } catch (SQLException e) {
            e.printStackTrace();
            closeDB();
        }

    }



    //현재 시간과 예약된 시간을 비교하여 2시간이 넘었다면 예약 목록에서 삭제한다.
    //db연결할때마다 실행되므로 따로 실행할필요없음.
    public void clearBookedList(){
        String sql = "delete from bookinglist where date_add(date, interval 2 hour) < now()";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            //System.out.println("clearBookedList() 실행 완료");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //파라미터로 studentID, studentName을 넣으면 해당 학생이 예약한 목록을 반환한다.
    //목록은 bookedListByClient 리스트에 저장된다.
    public void getBookedByClient(String studentID, String studentName){
        connectDB();
        String sql = "select * from bookinglist where studentName =  ? and studentID = ?";
        try{
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, studentName);
            pstmt.setString(2, studentID);

            resultSet = pstmt.executeQuery();
            clearBookedListByClient();
            DB_DTO tmp;
            while(resultSet.next()){
                tmp=new DB_DTO();
                tmp.setBuilding(resultSet.getString("building"));
                tmp.setStudentName(resultSet.getString("studentName"));
                tmp.setStudentID(resultSet.getString("studentID"));
                tmp.setIsProject(resultSet.getInt("isProject"));
                tmp.setRoomNumber(resultSet.getString("roomNumber"));
                tmp.setMaxPeople(resultSet.getInt("maxPeople"));
                tmp.setDate(resultSet.getString("date"));

                bookedListByClient.add(tmp);
            }
            System.out.println();
            System.out.println("## studentID : "+studentID+" studentName : "+studentName +" 으로 예약한 정보 ##");
            System.out.println(bookedListByClient);
            System.out.println();
            //System.out.println("bookedListByClient 저장 완료");
            closeDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //현재 예약된 모든 목록을 보여준다.
    //mData 리스트에 저장된다.
    //이 메소드는 관리자용
    public void getAll(){
        connectDB();
        sql = "select * from bookinglist";

        try{
            pstmt = conn.prepareStatement(sql);
            resultSet = pstmt.executeQuery();
            clearmData();
            DB_DTO tmp;
            while(resultSet.next()) {
                tmp = new DB_DTO();
                tmp.setBuilding(resultSet.getString("building"));
                tmp.setRoomNumber(resultSet.getString("roomNumber"));
                tmp.setIsProject(resultSet.getInt("isProject"));
                tmp.setStudentID(resultSet.getString("studentID"));
                tmp.setStudentName(resultSet.getString("studentName"));
                tmp.setMaxPeople(resultSet.getInt("maxPeople"));
                tmp.setDate(resultSet.getString("date"));
                mData.add(tmp);
            }
            System.out.println("## 모든 예약 정보 ##");
            System.out.println(mData);
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //시간은 반드시 년-월-일 시간:분:초 형식으로 스트링으로 저장 ex) "2019-12-12 15:22:39"
    //설정한 날짜및시간, maxPeople, isProject를 파라미터로 지정
    //설정된 날짜및시간, maxPeople, isProject에 예약가능한 목록 반환
    //데이터는 bookAvailableList 리스트에 저장된다.
    public void getBookAvailableList(String date, int maxPeople, int isProject){
/*
select  B.building ,B.roomNumber, B.isProject, B.maxPeople
from bookinglist A right join roomlist B on A.roomNumber = B.roomNumber and
A.date = date_format('2019-12-27 14:00:00', '%Y-%m-%d %H:%i:%s')
where A.roomNumber is null
and B.isProject = ? and B.maxPeople = ?
 */

        connectDB();
        String sql = "select  B.building ,B.roomNumber, B.isProject, B.maxPeople\n" +
                "from bookinglist A right join roomlist B on A.roomNumber = B.roomNumber and\n" +
                "A.date = date_format(?, '%Y-%m-%d %H:%i:%s')\n" +
                "where A.roomNumber is null\n" +
                "and B.isProject = ? and B.maxPeople >= ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, date);
            pstmt.setInt(2, isProject);
            pstmt.setInt(3, maxPeople);

            resultSet = pstmt.executeQuery();
            clearBookAvailableList();
            DB_DTO tmp;
            while(resultSet.next()){
                tmp = new BookAvailableDTO();
                tmp.setBuilding(resultSet.getString("building"));
                tmp.setRoomNumber(resultSet.getString("roomNumber"));
                tmp.setIsProject(resultSet.getInt("isProject"));
                tmp.setMaxPeople(resultSet.getInt("maxPeople"));
                bookAvailableList.add(tmp);
            }
            System.out.println();
            System.out.println("## "+date+"에 예약가능한 목록 isProject = "+isProject+" maxPeople = "+maxPeople+" ##");
            System.out.println(bookAvailableList);
            System.out.println();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //DB_DTO클래스에 정보를 저장한뒤 파라미터로 넘겨주면 해당 정보를 예약함
    //예약이 되었으면 true 아니면 false반환
    public boolean book(DB_DTO tmp){
        connectDB();
        sql = "insert into bookinglist(building, roomNumber, studentID, studentName, isProject, maxPeople, date) values(?,?,?,?,?,?,?,?)";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, tmp.getBuilding());
            pstmt.setString(2, tmp.getRoomNumber());
            pstmt.setString(3, tmp.getStudentID());
            pstmt.setString(4, tmp.getStudentName());
            pstmt.setInt(5, tmp.getIsProject());
            pstmt.setInt(6, tmp.getMaxPeople());
            pstmt.setString(7, tmp.getDate());

            pstmt.executeUpdate();
            System.out.println(tmp+"예약에 성공했습니다.");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("예약 실패");
            return false;
        }

    }
    //예약을 취소하는 메서드
    //building, roomnumber, studentid, studentname이 모두 일치하면 예약취소 및 데이터 삭제
    //하나라도 데이터가 맞지않을시 에러
    public void bookingCancel(String building, String roomNumber, String studentID, String studentName){
        connectDB();
        sql = "delete from bookinglist where building = ? and roomNumber = ? and studentID = ? and studentName = ?";

        try {
            pstmt= conn.prepareStatement(sql);

            pstmt.setString(1, "building");
            pstmt.setString(2, "roomNumber");
            pstmt.setString(3, "studentID");
            pstmt.setString(4, "studentName");
            pstmt.executeUpdate();
            System.out.println("예약이 취소되었습니다.");
        } catch (Exception e) {
            System.out.println("예약 취소가 실패했습니다.");
            e.printStackTrace();
        }
    }
    //DB에 커넥트
    void connectDB(){
        try {
            //JDBC 드라이버 로드
            //Class.forName(jdbcDriver);

            //데이터베이스 연결
            conn = DriverManager.getConnection(jdbcUrl, "root", "1234");
            //System.out.println("DB에 연결되었습니다!");
            clearBookedList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //DB 닫기
    void closeDB(){
        try {
            if(pstmt != null) {
                pstmt.close();
            }
            if(resultSet !=null) {
                resultSet.close();
            }
            if(conn != null) {
                conn.close();
            }
            System.out.println("DB 연결이 종료되었습니다!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //밑에꺼 쓰지마세요
    //////////////////////////////////////////////////
    //Deprecated
    //예약 테이블에서 2시간이 경과한 예약목록을 삭제
    //데몬 설정해야됨
   /* @Override
    public void run() {
        connectDB();
        while(true){
            System.out.println("시작");
            clear_mData();
            getAll();

            deleteData();

            try {
                //10분 단위로 쓰레드 실행
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/
}
