/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelproject.CustomerView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Food {

    CustomerMenu customerMenu = new CustomerMenu();
    Scanner scanner = new Scanner(System.in);
    databas databas = new databas();
    int choice;
    String pay;
    int val;
    boolean loop = true;
    int total = 0;

    public void foodmenu() throws SQLException {

        List<String> list = new ArrayList<String>();

        do {
            System.out.println("\n-----The Menu------");
            System.out.println("\n1. Carbonara 80 SEK\n2. Sandwich: 20 SEK\n3. Noodles 10 SEK\n4. Drink 8 SEK\n-------------------\n5. Shopping cart\n6. Cancle and exit");

            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    list.add("Carbonara 80 SEK");
                    System.out.println("\nCarbonara added!");
                    total = total + 80;
                    break;
                case 2:
                    list.add("Sandwich 20 SEK");
                    System.out.println("\nSandwich added!");
                    total = total + 20;
                    break;
                case 3:
                    list.add("Noodles 10 SEK");
                    System.out.println("\nNoodle added!");
                    total = total + 10;
                    break;
                case 4:
                    list.add("Drink 8 SEK");
                    System.out.println("\nDrink added!");
                    total = total + 8;
                    break;
                case 5:
                    //////////   "LAMBDA EXPRESSION"!
                    list.forEach((n) -> {
                        System.out.println("\n" + n);
                    });
                    System.out.println("\nTotal cost: " + total + " SEK");

                    payup();

                    break;
                case 6:
                    System.out.println("\nReturing you back to the menu...");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Food.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    loop = false;
                    break;
                default:
                    System.out.println("\nWrong input!");
                    break;
            }

        } while (loop);

    }

    public void payup() throws SQLException {

        System.out.println("\n1. Pay \n2. back");
        val = scanner.nextInt();
        switch (val) {
            case 1:
                if (total < 8) {
                    System.out.println("\nYou havn't bought anything yet!");
                } else {
                    // please enter your room ID: -> Thanks for ordering our food!
                    databas.updateboughtcheck();
                    System.out.println("\nThanks for ordering our food! ");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Food.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    loop = false;
                }

                break;
            case 2:
                System.out.println("\n");
                break;
            default:
                System.out.println("\nWrong input!");
                payup();
                break;
        }
    }

}
