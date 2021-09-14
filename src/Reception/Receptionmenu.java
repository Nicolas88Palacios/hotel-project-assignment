/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;

import hotelproject.Booking;
import hotelproject.JDBC;
import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Mathias
 */
public class Receptionmenu {

    static JDBC jdbc = new JDBC();
    static Scanner input;
    private static Statement sqlStatement;
    private static Connection connection;
    private static boolean shouldExit;
    private static ResultSet rs;

    public static void menu() {
        try {
            connection = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUser(), jdbc.getPassword());
            sqlStatement = connection.createStatement();

            while (shouldExit) {
                System.out.println("\nReceptionist menu");
                System.out.println("1. Create new guest");
                System.out.println("2. Show all guests and information");
                System.out.println("3. Delete guests");
                System.out.println("4. Booking or upgrading room");
                System.out.println("5. Roomservice");
                System.out.println("6. Checkout");
                System.out.println("0. Exit");
                int selection = input.nextInt();
                input.nextLine();
                switch (selection) {
                    case 1:
                        createGuest("guest");
                        break;
                    case 2:
                        displayGuests();
                        break;
                    case 3:
                        deleteGuest();
                        break;
                    case 4:
                        // booking or upgrading();
                        Booking book = new Booking();
                        System.out.println(" Press: 1 for booking\nPress 2: Upgrading");
                        int choise = input.nextInt();
                        if (choise == 1) {
                            book.book();
                        } else {
                            upgradeRoom();
                        }
                        break;
                    case 5:
                        FoodGuest guest = new FoodGuest();
                        guest.Guestfood();
                        break;
                    case 6:
                        CheckoutGuest checkout = new CheckoutGuest();
                        checkout.cashier();
                        break;
                    case 0:
                        shouldExit = false;
                        break;
                    default:
                        shouldExit = false;
                }
            }
        } catch (Exception e) {
            System.err.println("few");
        }
    }

    private static void createGuest(String table) throws SQLException {
        System.out.println("Please fill in your information below.");
        System.out.println("Name: ");
        String guestName = input.nextLine();
        System.out.println("Phone number: ");
        String phoneNumber = input.nextLine();
        System.out.println("Email: ");
        String email = input.nextLine();

        try {
            PreparedStatement statement = sqlStatement.getConnection()
                    .prepareStatement("INSERT INTO " + table + " (guestName,phoneNumber,email) VALUES (?,?,?);");
            statement.setString(1, guestName);
            statement.setString(2, phoneNumber);
            statement.setString(3, email);
            statement.executeUpdate();
        } catch (SQLException var4) {
            var4.printStackTrace();
        }
        System.out.println("Guest created. Welcome " + guestName + "!");

    }

    private static void displayGuests() throws SQLException {
        listTable("guest");

    }

    //
    private static void deleteGuest() throws SQLException {

        listTable("guest");
        System.out.println("Put in the guest ID you wish to delete:");
        int guestID = Integer.parseInt(input.nextLine());
        int result = sqlStatement.executeUpdate("DELETE FROM guest WHERE guestID =" + guestID + ";");
        if (result == 1) {
            System.out.println("The guest with ID: " + guestID + " is now deleted!");
        } else {
            System.out.println("Delete failed, please put in the correct member ID");
        }

    }

    private static void listTable(String tableName) throws SQLException {
        String qry = "SELECT * FROM " + tableName + ";";
        print(qry);
    }

    private static void print(String qry) throws SQLException {
        try {
            sqlStatement = connection.createStatement();
            rs = sqlStatement.executeQuery(qry);
        } catch (SQLException var8) {
            var8.printStackTrace();
        }

        int columnCount = rs.getMetaData().getColumnCount();
        String[] columnNames = new String[columnCount];

        for (int i = 0; i < columnCount; ++i) {
            columnNames[i] = rs.getMetaData().getColumnName(i + 1);
        }

        String[] var9 = columnNames;
        int var4 = columnNames.length;

        int var5;
        String columnName;
        for (var5 = 0; var5 < var4; ++var5) {
            columnName = var9[var5];
            System.out.print(PadRight(columnName));
        }

        while (rs.next()) {
            System.out.println(rs);
            var9 = columnNames;
            var4 = columnNames.length;

            for (var5 = 0; var5 < var4; ++var5) {
                columnName = var9[var5];
                String value = rs.getString(columnName);
                if (value == null) {
                    value = "null";
                }

                System.out.print(PadRight(value));
            }
        }

        System.out.println();
    }

    private static String PadRight(String string) throws SQLException {
        int totalStringLength = 30;
        int charsToPadd = totalStringLength - string.length();
        if (string.length() >= totalStringLength) {
            return string;
        } else {
            StringBuilder stringBuilder = new StringBuilder(string);

            for (int i = 0; i < charsToPadd; ++i) {
                stringBuilder.append(" ");
            }

            return stringBuilder.toString();
        }
    }

    // public static int tryParse(String value) {
    // return tryParse(value, 0);
    // }

    // public static int tryParse(String value, int defaultVal) {
    // try {
    // return Integer.parseInt(value);
    // } catch (NumberFormatException var3) {
    // return defaultVal;
    // }
    // }

    static {
        input = new Scanner(System.in);
        sqlStatement = null;
        connection = null;
        shouldExit = true;
    }

    public static void upgradeRoom() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        // ResultSet rs = null;

        try (Connection connection = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUser(), jdbc.getPassword())) {

            sqlStatement = connection.createStatement();

            System.out.println("\nType id for the guest you want to have room uppgraded");

            int guestId = scanner.nextInt();
            scanner.nextLine();

            rs = sqlStatement.executeQuery("SELECT * FROM booking WHERE guest_id = '" + guestId + "'");

            System.out.println("\nType in current room number");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();

            System.out.println("\nType in new room number");
            int newRoomNumber = scanner.nextInt();

            sqlStatement.executeUpdate(
                    "  update booking set room_info_id =" + newRoomNumber + " where room_info_id = " + roomNumber);

            System.out.println("Room has been uppgraded!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
