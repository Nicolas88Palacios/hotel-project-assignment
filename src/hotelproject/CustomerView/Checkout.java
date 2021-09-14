/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelproject.CustomerView;

import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author Mathias
 */
public class Checkout {

    int choice;
    CustomerMenu customerMenu = new CustomerMenu();
    Scanner scanner = new Scanner(System.in);
    databas databas = new databas();

    public void checkingout() throws SQLException {
        System.out.println("\nDo you want to checkout? \n1. Yes\n2. Not right now");
        choice = scanner.nextInt();
        switch (choice) {
            case 1:
                bye();
                break;
            case 2:
                customerMenu.customermenu();
                break;
            default:
                System.out.println("Wrong Input!");
                break;
        }

    }

    public void bye() {
        System.out.print("\nPlease enter your ID to checkout: ");
        databas.bill();
        System.exit(0);
        
    }
}
