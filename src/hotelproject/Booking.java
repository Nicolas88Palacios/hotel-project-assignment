package hotelproject;

import java.sql.*;
import java.util.*;
import hotelproject.CustomerView.CustomerMenu;
import java.text.SimpleDateFormat;
import java.util.stream.Stream;

public class Booking {

    static JDBC jdbc = new JDBC();
    static Statement sqlStatement = null;
    static Main backToMain = new Main();
    static ResultSet rs = null;
    static PreparedStatement statement = null;
    static int addingRows;
    static Scanner scanner = new Scanner(System.in);
    static int quantity = 0;
    static int roomInfoId = 0;
    static String startDate;
    static String endDate;
    static int total;
    static long diffDays;
    static String dateStart;
    static String dateStop;

    public void book() throws SQLException {

        try (Connection connection = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUser(), jdbc.getPassword())) {

            System.out.println("Connected\n");

            sqlStatement = connection.createStatement();
            Scanner scanner = new Scanner(System.in);
            int choice;

            System.out.println("Hotel Rooms\n");
            System.out.println("What room do you want to book? \n");

            System.out.println("\n\n1. Luxury Double Room\n");
            System.out.println("2. Deluxe Double Room\n");
            System.out.println("3. Luxury Single Room\n");
            System.out.println("4. Deluxe Single Room\n");
            System.out.println("0. Back to menu!\n");

            System.out.println("\nMake a choice");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    total = total + 4000;
                    // Booking a Luxury Double Room!

                    System.out.println("You have chosen Luxury Double Room!");

                    String query = "SELECT room_info_id, available FROM room_info WHERE available = 0 AND room_info_id BETWEEN 400 AND 403";

                    sqlStatement.executeQuery(query);
                    listTable(query);

                    System.out.println("Enter room number!");
                    roomInfoId = scanner.nextInt();

                    emailValidation();

                    break;
                case 2:
                    total = total + 3000;
                    // Booking a Deluxe Double Room!

                    System.out.println("You have chosen Deluxe Double Room!");

                    query = "SELECT r.room_info_id, r.available FROM room_info r WHERE available = " + 0
                            + " And room_info_id BETWEEN 300 AND 305";

                    sqlStatement.executeQuery(query);
                    listTable(query);

                    System.out.println("Enter room number!");
                    roomInfoId = scanner.nextInt();

                    emailValidation();

                    break;
                case 3:
                    total = total + 2000;
                    // Booking a Luxury Single Room!
                    System.out.println("You have chosen Luxury Single Room!");

                    query = "SELECT r.room_info_id, r.available FROM room_info r WHERE available = " + 0
                            + " And room_info_id BETWEEN 200 AND 207";

                    sqlStatement.executeQuery(query);
                    listTable(query);

                    System.out.println("Enter room number!");
                    roomInfoId = scanner.nextInt();

                    emailValidation();

                    break;
                case 4:
                    total = total + 1000;
                    // Booking a Deluxe Single Room!
                    System.out.println("You have chosen Deluxe Single Room!");

                    query = "SELECT r.room_info_id, r.available FROM room_info r WHERE available = " + 0
                            + " And room_info_id BETWEEN 100 AND 109";

                    sqlStatement.executeQuery(query);
                    listTable(query);

                    System.out.println("Enter room number!");
                    roomInfoId = scanner.nextInt();

                    emailValidation();

                    break;
                case 0:
                
                    Main.menu();
                    // loop = false;
                    break;

                default:
                    book();
                    break;
            }
            // scanner.close();
            // Removed while from here----- } while (loop);

        } catch (SQLException e) {
            System.err.println(e);
        }

    }

    public void addbill() {

        //// ADD BILL!!!
    }

    public static void booking(int roomInfoId, String email) throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUser(), jdbc.getPassword())) {
            sqlStatement = connection.createStatement();

            CustomerMenu customerMenu = new CustomerMenu();
            int guestId = 0;

            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                System.out.println("YYYY-MM-DD");

                System.out.println("DateStart: ");
                dateStart = scanner.nextLine();

                System.out.println("DateStop: ");
                dateStop = scanner.nextLine();

                long date2 = simpleDateFormat.parse(dateStop).getTime();
                long date1 = simpleDateFormat.parse(dateStart).getTime();
                long diff = date2 - date1;

                diffDays = diff / (24 * 60 * 60 * 1000);

            } catch (Exception e) {
                System.out.println("error" + e);
            }

            System.out.println("\nYou've chosen these dates: " + dateStart + " to: " + dateStop);
            System.out.print(diffDays + " days(Nights)");
            total = (int) (total * diffDays);
            System.out.println("TOTAL BILL:" + total);

            rs = sqlStatement.executeQuery("SELECT guest_id FROM guest WHERE email ='" + email + "'");

            while (rs.next()) {
                guestId = rs.getInt("guest_id");
            }

            System.out.println("guestid: " + guestId);
            System.out.println("start: " + dateStart);
            System.out.println("end: " + dateStop);

            sqlStatement
                    .executeUpdate("INSERT INTO booking " + "(guest_id, room_info_id, start_date, end_date)" + "VALUES "
                            + "('" + guestId + "' ,'" + roomInfoId + "' ,'" + dateStart + "' , '" + dateStop + "');");

            sqlStatement.executeUpdate("UPDATE guest SET bill = bill + " + total + " WHERE guest_id = " + guestId);

            sqlStatement.executeUpdate("UPDATE room_info SET available = 1  WHERE room_info_id = " + roomInfoId);

            System.out.println("");

            Main.menu();

        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    private static String createGuest() throws SQLException {
        String firstname;
        String surname;
        String email;
        String phoneNumber;
        String address;
        System.out.println("For booking enter your details \n");

        System.out.println("Enter your firstname");
        firstname = scanner.nextLine();

        System.out.println("Enter your surname");
        surname = scanner.nextLine();

        System.out.println("Enter email for contact");
        email = scanner.nextLine();

        System.out.println("Enter phonenumber for contact");
        phoneNumber = scanner.nextLine();

        System.out.println("Enter address for contact");
        address = scanner.nextLine();

        addingRows = sqlStatement.executeUpdate("INSERT INTO guest "
                + "( first_name, surname, email, phone_number, address)" + "VALUES " + "('" + firstname + "' ,'"
                + surname + "' ,'" + email + "' , '" + phoneNumber + "' ,'" + address + "');");

        System.out.println("Your details have been registered!\n");

        return email;
    }

    public static void emailValidation() throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUser(), jdbc.getPassword())) {
            sqlStatement = connection.createStatement();

            System.out.println("\nRoom " + roomInfoId + " chosen!");

            String email;
            boolean value;

            System.out.println("\nEnter email for booking");

            email = scanner.nextLine();
            rs = sqlStatement.executeQuery("SELECT guest_id FROM guest WHERE email ='" + email + "'");

            value = rs.next();

            if (value == true) {
                booking(roomInfoId, email);
            } else {
                System.out.println("\nCould not find you!");
                email = createGuest();
                booking(roomInfoId, email);
            }

        } catch (SQLException e) {
            System.err.println(e);
        }

    }

    private static String padRight(String string) {
        int totalStringLength = 30;
        int charsToPadd = totalStringLength - string.length();

        // incase the string is the same length or longer than our maximum lenght
        if (string.length() >= totalStringLength) {
            return string;
        }

        StringBuilder stringBuilder = new StringBuilder(string);
        for (int i = 0; i < charsToPadd; i++) {
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    public static void listTable(String query) throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUser(), jdbc.getPassword())) {
            sqlStatement = connection.createStatement();

            try {
                // Utför query mot databas
                // rs = sqlStatement.executeQuery("SELECT * FROM " + tableName + ";");
                rs = sqlStatement.executeQuery(query);

                // hämta antal kolumner
                int columnCount = rs.getMetaData().getColumnCount();
                // hämta alla kolmnnamn
                String[] columnNames = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    columnNames[i] = rs.getMetaData().getColumnName(i + 1);
                }

                // lägg kolumnnamn i string array
                // for (String columnName : columnNames) {
                // System.out.print(padRight(columnName));
                // }

                // LAMBDA!!!!
                Stream.of(columnNames).forEach(columnName -> {
                    System.out.print(padRight(columnName));
                });

                while (rs.next()) {
                    System.out.println();
                    // hämta data för alla kolumner för den nuvarande raden
                    for (String columnName : columnNames) {
                        String value = rs.getString(columnName);

                        if (value == null) {
                            value = "null";
                        }

                        System.out.print(padRight(value));
                    }
                }

                System.out.println();

            } catch (Exception e) {
                System.out.println("Error");
            }
        }
    }
}
