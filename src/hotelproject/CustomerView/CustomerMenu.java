/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelproject.CustomerView;

import java.util.Scanner;
import java.sql.*;

import hotelproject.Booking;
import hotelproject.HotelRoom;
import hotelproject.RoomAvailability;

public class CustomerMenu {

    static Statement sqlStatement = null;
    static Connection connection = null;
    static ResultSet rs = null;
    static PreparedStatement statement = null;
    private static int choice;

    public void customermenu() throws SQLException {

        HotelRoom hotelRoom = new HotelRoom();
        Booking bookingRoom = new Booking();
        RoomAvailability roomAvailability = new RoomAvailability();
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;

        do {
            System.out.println("\n----------------------");
            System.out.println("Enter your choice");
            System.out.println(
                    "1. Display room details\n2. Display room availability\n3. Book\n4. Order food\n5. Check out\n0. Exit");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    hotelRoom.roomMenu();

                    break;
                case 2:
                    roomAvailability.showRooms();

                    break;
                case 3:
                    bookingRoom.book();

                    break;
                case 4:
                    Food foodlist = new Food();
                    foodlist.foodmenu();
                    break;
                case 5:
                    Checkout checkout = new Checkout();
                    checkout.checkingout();

                    break;
                case 0:
                    loop = false;
                    break;
                default:
                    System.out.println("\nWrong input!");
            }

        } while (loop);

    }
}
