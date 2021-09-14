/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelproject.CustomerView;

import Reception.CheckoutGuest;
import Reception.FoodGuest;
import com.mysql.cj.xdevapi.PreparableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Mathias
 */
public class databas {
    //////////Change DB!

    String DB = "jdbc:derby://localhost:1527/project;create=true";
    Scanner scanner = new Scanner(System.in);
    public int total = 0;
    int choice;
    int id;
    int roomid;
    int dagar;
    boolean loop = true;
    int bill = 0;

    public static void main(String[] args) {
    }

//    public void viewall() {
//        try {
//            System.out.print("id room: ");
//            String idroom = scanner.next();
//            System.out.print("Name:");
//            String Name = scanner.next();
//            System.out.print("Number: ");
//            String Number = scanner.next();
//            System.out.println("Bought");
//            String bought = scanner.next();
//            System.out.println("bill");
//            String bill = scanner.next();
//
//            Connection con = DriverManager.getConnection(DB);
//            PreparedStatement pst = con.prepareStatement("insert into customer(name,id,bought,bill,number) values(?,?,?,?,?)");
//            pst.setString(1, Name);
//            pst.setString(2, idroom);
//            pst.setString(3, bought);
//            pst.setString(4, bill);
//            pst.setString(5, Number);
//            pst.executeUpdate();
//            Statement st = con.createStatement();
//
//            ResultSet rs = st.executeQuery("select * from Customer");
//
//            System.out.println("Name:");
//
//            System.out.println("\nName  " + "    Id");
//            while (rs.next()) {
//                System.out.println(rs.getString("name") + "   " + rs.getInt("id") + "  " + rs.getBoolean("bought"));
//            }
//
//            con.close();
//
//        } catch (Exception e) {
//            System.out.println("Database could not connect" + e);
//        }
//
//    }

//    public void removebyid() {
//
//        try {
//
//            Connection con = DriverManager.getConnection(DB);
//
//            Statement st = con.createStatement();
//            id = scanner.nextInt();
//            st.executeUpdate("delete from customer where id=" + id);
//            ResultSet rs = st.executeQuery("select * from Customer");
//
//            con.close();
//            System.out.println("Bye!");
//            System.exit(0);
//
//        } catch (Exception e) {
//            System.out.println("Database could not connect" + e);
//        }
//
//    }

//    public void addcustomer() {
//        try {
//            Connection con = DriverManager.getConnection(DB);
//
//            Statement st = con.createStatement();
//            st.executeUpdate("insert into customer values ('Hampus',5,false)");
//            ResultSet rs = st.executeQuery("select * from Customer");
//
//            con.close();
//
//        } catch (Exception e) {
//            System.out.println("Database could not connect" + e);
//        }
//    }

    public void updateboughtcheck() {
        do {
            try {
                Connection con = DriverManager.getConnection(DB);

                Statement st = con.createStatement();
                System.out.println("Please enter your room number(id): ");
                    roomid =scanner.nextInt();
                if (roomid == 0) {
                    loop = false;
                }

                st.executeUpdate("update customer set Bought=True where Id=" + roomid);
                ResultSet rs = st.executeQuery("select * from Customer where id=" + roomid);
                if (rs.next()) {
                    System.out.println("\nSending the food to: " + rs.getString("name"));
                    loop = false;
                } else {
                    System.out.println("The room number does not exist!");
                }

                con.close();

            } catch (Exception e) {
                System.out.println("Database could not connect" + e);
            }

        } while (loop);

    }

    public void bill() {
        do {
            try {
                Connection con = DriverManager.getConnection(DB);

                Statement st = con.createStatement();
                System.out.print("\nThe guest id:");
                id = scanner.nextInt();
                if (id == 0) {
                    loop = false;
                }
                ResultSet rs = st.executeQuery("select * from Customer where id=" + id);
                if (rs.next()) {

                    System.out.println("\n------------------------");
                    System.out.println("\nName  " + "    Id " + "    Bill");

                    System.out.println(rs.getString("name") + "   " + rs.getInt("id") + "     " + rs.getInt("bill") + " SEK");

                    System.out.println("\n------------------------");

                    System.out.println("Correct information?");
                    System.out.print("1.Yes   2.No\n");
                    choice = scanner.nextInt();
                    switch (choice) {
                        case 1:
                            st.executeUpdate("update customer set hotelroomid=null where Id=" + id);
                            System.out.println("Thanks for staying!");
                            TimeUnit.SECONDS.sleep(2);
                            con.close();
                            loop = false;
                            break;
                        case 2:

                            break;

                        default:
                            System.out.println("Wrong input!");
                    }

                    con.close();

                } else {
                    System.out.println("\nThe id you are searching for does not exist!");
                }

            } catch (Exception e) {
                System.out.println("Database could not connect" + e);
            }
        } while (loop);

    }

}
