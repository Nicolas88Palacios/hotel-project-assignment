import java.util.Scanner;

public class HotelRoom {

    private static int choice;

    public static void roomMenu() {
        // meny
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hotel Rooms\n");
        System.out.println("What kind of room do you want to book? \n");
        System.out.println("\n\n1. Luxury Double Room\n");
        System.out.println("2. Deluxe Double Room\n");
        System.out.println("3. Luxury Single Room\n");
        System.out.println("4. Deluxe Single Room\n");
        System.out.println("0. Exit\n");

        System.out.println("\nMake a choice");

        choice = scanner.nextInt();

        switch (choice) {
            case 1:
            System.out.println("The hotels most luxurius room!");
                break;
            case 2:

                break;
            case 3:
                break;
            case 4:

                break;
            default:
                throw new AssertionError();
        }
    }
}
