package hotelproject;

import java.util.Scanner;
import hotelproject.CustomerView.CustomerMenu;
import java.sql.*;

public class HotelRoom {
    static JDBC jdbc = new JDBC();
    static HotelRoom hotelRoom = new HotelRoom();
    static Statement sqlStatement = null;
    static Connection connection = null;
    static ResultSet rs = null;
    static PreparedStatement statement = null;
    static int addingRows;
    static Scanner scanner = new Scanner(System.in);

    private static int choice;

    public void roomMenu() throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUser(), jdbc.getPassword())) {
            sqlStatement = connection.createStatement();
            CustomerMenu customerMenu = new CustomerMenu();
            boolean loop = true;

            System.out.println("Hotel Rooms\n");
            System.out.println("What room do you want to se? \n");
            System.out.println("\n\n1. Luxury Double Room\n");
            System.out.println("2. Deluxe Double Room\n");
            System.out.println("3. Luxury Single Room\n");
            System.out.println("4. Deluxe Single Room\n");
            System.out.println("0. Exit\n");

            System.out.println("\nMake a choice");

            do {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println(
                                "The hotel's Luxury Double Room are wonderfully spacious and airy with seating area and generous work space."
                                        + "You sleep in a 200 - 240 cm wide bed and the rooms have a 46/50 inch TV."
                                        + "All rooms have large floor-to-ceiling windows and a bathroom with either a shower or a bathtub. Our Deluxe Rooms vary in size and design."
                                        + " Charge per day 4000 SEK\n");

                        returnToMenu();

                        break;
                    case 2:
                        System.out.println(
                                "The hotel's Deluxe Double Room are wonderfully spacious and airy with seating area and generous work space."
                                        + "You sleep in a 200 - 220 cm wide bed and the rooms have a 40/46 inch TV."
                                        + "All rooms have large floor-to-ceiling windows and a bathroom with either a shower or a bathtub. Our Deluxe Rooms vary in size and design."
                                        + " Charge per day 3000 SEK\n");

                        returnToMenu();
                        break;
                    case 3:
                        System.out.println(
                                "The hotel's Luxury Single Room are wonderfully spacious and airy with seating area and generous work space."
                                        + "You sleep in a 180 - 200 cm wide bed and the rooms have a 36/40 inch TV."
                                        + "All rooms have large floor-to-ceiling windows and a bathroom with either a shower or a bathtub. Our Deluxe Rooms vary in size and design."
                                        + " Charge per day 2000 SEK\n");

                        returnToMenu();
                        break;
                    case 4:
                        System.out.println(
                                "The hotel's Deluxe Single Room are wonderfully spacious and airy with seating area and generous work space."
                                        + "You sleep in a 160 - 180 cm wide bed and the rooms have a 36/38 inch TV."
                                        + "All rooms have large floor-to-ceiling windows and a bathroom with either a shower or a bathtub. Our Deluxe Rooms vary in size and design."
                                        + " Charge per day 1000 SEK\n");

                        returnToMenu();
                        break;
                    case 0:
                        customerMenu.customermenu();
                        loop = false;
                        break;
                    default:
                        System.out.println("Invalid choise, what do you want to do?");
                        roomMenu();
                        System.out.println("du kom hit");

                }
            } while (!isValid(choice));
                scanner.close();
                // Replaced While from here
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public static void returnToMenu() throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUser(), jdbc.getPassword())) {

            int returnToMenu = 0;

            System.out.println("Press 0 to return to menu");

            returnToMenu = scanner.nextInt();

            switch (returnToMenu) {
                case 0:
                    hotelRoom.roomMenu();
                    break;

                default:
                hotelRoom.roomMenu();
                    break;
            }
        }

    }
    public static boolean isValid(int choice) {
        if (choice >= 0 && choice <= 4) {
            return true;
        } else {
            System.out.println("You have to chose betwen 1-4.");
            return false;
        }
    }
}