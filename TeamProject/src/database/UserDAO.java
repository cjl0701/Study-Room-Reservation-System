import java.sql.*;

public class UserDAO {
    //String jdbcDriver = "com.mysql.jdbc.Driver";
    String jdbcUrl = "jdbc:mysql://127.0.0.1/javadb?serverTimezone=UTC";
    Connection conn;

    PreparedStatement pstmt;
    ResultSet resultSet;

    //패스워드 암호화
    //select sha2(값, 256)
    //Primary Key is studentID(학번)
    public void join(String ID, String password, String studentName, String studentID){
        connectDB();

        String sql = "insert into user(ID, password, studentName, studentID) values(?, sha2(?, 256), ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, ID);
            pstmt.setString(2, password);
            pstmt.setString(3, studentName);
            pstmt.setString(4, studentID);

            int k = pstmt.executeUpdate();

            if(k != 1) {
                System.out.println("회원가입 실패..");
                throw new JoinFailException("Join Failed...");
            }
            System.out.println("회원가입 성공!");
            closeDB();
        } catch (SQLException e) {
            e.printStackTrace();
            closeDB();
        } catch (JoinFailException e) {
            e.printStackTrace();
            closeDB();
        }

    }
    //로그인이 성공하면 true 아니면 false 반환
    public boolean login(String ID, String password){
        connectDB();

        String sql = "select * from user where ID = ? and password = sha2(?, 256)";
            try {
                pstmt = conn.prepareStatement(sql);

                pstmt.setString(1, ID);
                pstmt.setString(2, password);

                resultSet = pstmt.executeQuery();

            if(resultSet.next()){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //DB에 커넥트
    void connectDB(){
        try {
            //JDBC 드라이버 로드
            //Class.forName(jdbcDriver);

            //데이터베이스 연결
            conn = DriverManager.getConnection(jdbcUrl, "root", "1234");
            //System.out.println("DB에 연결되었습니다!");
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
}
