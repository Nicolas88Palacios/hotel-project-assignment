/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reception;

import hotelproject.CustomerView.databas;
import java.util.Scanner;

/**
 *
 * @author Mathias
 */
public class CheckoutGuest {

    int choice;
    boolean loop=true;
    Scanner scanner = new Scanner(System.in);
    databas databas = new databas();

    public void cashier() {
        do {
                 System.out.println("\n---------------------------");
        System.out.println("\nChecking out a customer");
        System.out.println("\n1. Checkout\n2. Cancel");
        choice = scanner.nextInt();
        switch (choice) {
            case 1:          
                processing();
                break;
            case 2:
                loop = false;
                break;
            case 3:
                break;
            default:
                System.out.println("\nWrong Input!");
                break;

        }
        } while (loop);
        
   
    }
    
    
    public void processing(){

        databas.bill();
        
        
    }

}
