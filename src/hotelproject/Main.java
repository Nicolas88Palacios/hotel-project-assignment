/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelproject;

import Reception.CheckoutGuest;
import Reception.FoodGuest;
import Reception.Receptionmenu;
import hotelproject.CustomerView.CustomerMenu;
import hotelproject.CustomerView.Food;
import hotelproject.CustomerView.databas;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.*;

public class Main {

    static Statement sqlStatement = null;
    static Connection connection = null;
    static ResultSet rs = null;
    static PreparedStatement statement = null;
    static int addingRows;
    static JDBC jdbc = new JDBC();
    static boolean loop = true;

    static CustomerMenu customerMenu = new CustomerMenu();
    static Receptionmenu reception = new Receptionmenu();
    static databas databas = new databas();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {

        try ( Connection connection = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUser(), jdbc.getPassword())) {
            System.out.println("Connected\n");
            sqlStatement = connection.createStatement();
            do {
                menu();

            } while (loop);

        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public static void switchlist(int choice) {
        try {
            switch (choice) {
                case 1:
                    Receptionmenu.menu();
                    break;
                case 2:
                    customerMenu.customermenu();
                    break;
                case 3:
                    loop = false;
                    break;
                default:
                    System.out.println("\nPlease use the correct number!");
                    break;
            }

        } catch (Exception e) {
            System.out.println("Please use the numberpad!");
        }
    }

    public static int menu() {
        int choice = 0;
        do {
            System.out.println("\n1.Receptionist\n2.Customer\n3.Exit");          
            try {
                choice = scanner.nextInt();
                 switchlist(choice);
            } catch (Exception e) {
                System.out.println("\nWrong input!");   //Try catch fungerar inte det loopas bara om och om.
            }
            scanner.nextLine();
        } while (!isValid(choice));
        return choice;

    }

    public static boolean isValid(int choice) {
        if (choice >= 0 && choice <= 3) {
            return true;
        } else {
            System.out.println("You have to chose betwen 1-3.");
            return false;
        }
    }
}
