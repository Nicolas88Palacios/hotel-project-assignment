// package hotelproject;

// import java.sql.*;
// import java.util.Scanner;

// public class UpgradeRoom {

//     static Statement sqlStatement = null;
//     static Connection connection = null;
//     static JDBC jdbc = new JDBC();
//     static Scanner scanner = new Scanner(System.in);
//     static ResultSet rs = null;

//     public void upgradeRoom()throws SQLException {
       
//         try ( Connection connection = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUser(), jdbc.getPassword())) {

//             sqlStatement = connection.createStatement();

//             System.out.println("\nType id for the guest you want to have room uppgraded");

//             int guestId = scanner.nextInt();
//             scanner.nextLine();

//             rs = sqlStatement.executeQuery("SELECT * FROM booking WHERE guest_id = '" + guestId + "'");

//             System.out.println("\nType in current room number");
//             int roomNumber = scanner.nextInt();
//             scanner.nextLine();

//             System.out.println("\nType in new room number");
//             int newRoomNumber = scanner.nextInt();

//             // kanske måste lägga till booking_id, guest_id, start_date, end_date???
//             sqlStatement.executeUpdate(
//                     "  update booking set room_info_id =" + newRoomNumber + " where room_info_id = " + roomNumber );

//             System.out.println("Room has been uppgraded!");
//         } catch(Exception e) {
//             System.out.println(e);
//         }
//     }
//     }

