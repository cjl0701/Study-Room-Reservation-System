public class MyMain {
    public static void main(String[] args) {
        DB_DAO db = new DB_DAO();
        DB_DTO tmp = new DB_DTO();




        db.getBookAvailableList("2019-12-27 16:00:00",1, 1);


        db.getAll();

        db.getBookedByClient("100", "Kim");

    }
}
