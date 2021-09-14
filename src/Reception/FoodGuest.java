/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;

import static Reception.Receptionmenu.jdbc;
import hotelproject.CustomerView.Food;
import hotelproject.CustomerView.databas;
import hotelproject.JDBC;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mathias
 */
public class FoodGuest {

    static JDBC jdbc = new JDBC();
    Scanner scanner = new Scanner(System.in);
    databas check = new databas();
   
    int choice;
    String pay;
    int roomid;
    int val;
    boolean loop = true;
    int total = 0;

    public void Guestfood() throws SQLException {
        List<String> list = new ArrayList<String>();

        do {
            System.out.println("\n-----The Menu------");
            System.out.println("\n1. Carbonara 80 SEK\n2. Sandwich: 20 SEK\n3. Noodles 10 SEK\n4. Drink 8 SEK\n-------------------\n5. Done\n6. Cancle and exit");

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

                    sendtoroom();

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
                    System.out.println("\nWrong input!!");
                    break;
            }

        } while (loop);
    }

    public void sendtoroom() throws SQLException {

        System.out.println("\n1. Send the food/order to the guest/chef \n2. back");
        val = scanner.nextInt();
        Connection con = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUser(), jdbc.getPassword());
        Statement st = con.createStatement();

        switch (val) {
            case 1:
                if (total < 8) {
                    System.out.println("\nYou havn't bought anything yet!");
                } else {
                    System.out.print("\nPlease enter your room number(id): ");
                    roomid = scanner.nextInt();

                    if (roomid == 0) {
                        loop = false;
                    }
                    ResultSet rs = st.executeQuery("select * from Customer where id=" + roomid);
                    if (rs.next()) {
                        System.out.println("Are you " + rs.getString("name") + "?" + "\n1. Yes\n2. No");
                        choice = scanner.nextInt();
                        switch (choice) {
                            case 1:
                                System.out.println("\nSending the food to: " + rs.getString("name"));
                                st.executeUpdate("update customer set bill=bill+" + total + " where Id=" + roomid);
                                System.out.println("\nThanks for ordering our food! ");
                                break;
                            case 2:
                                System.out.println("EXITING!");

                                break;
                        }

                        loop = false;
                    } else {
                        System.out.println("The room number does not exist!");
                    }
                    con.close();

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

                break;
        }

    }

}
