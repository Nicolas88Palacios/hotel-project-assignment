package com.company;

import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.sql.*;
import java.util.Scanner;


public class Library {

    private static final String url = "jdbc:mysql://localhost:3306/Library?serverTimezone=UTC";
    private static final String user = "alex";
    private static final String password = "Kung_1100";
    private static Scanner input = new Scanner(System.in);
    private static Statement sqlStatement = null;
    private static Connection connection = null;
    private static boolean shouldExit = true;
    private static ResultSet rs;


    public static void main(String[] args) throws Exception {

        try {
            connection = DriverManager.getConnection(url, user, password);

            System.out.println("Connection succeeded!");

            sqlStatement = connection.createStatement();

            while (shouldExit) {

                System.out.println("\nWelcome to Library!");
                System.out.println("1. Create");
                System.out.println("2. Borrow a book");
                System.out.println("3. Return a book");
                System.out.println("4. Delete");
                System.out.println("5. Exit");

                int selection = Integer.parseInt(input.nextLine());

                switch (selection) {
                    case 1:
                        create();
                        break;
                    case 2:
                        borrowAbook();
                        break;
                    case 3:
                        returnAbook();
                        break;
                    case 4:
                        deleteMenu();
                        break;
                    default:
                        shouldExit = false;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
    }


    private static void create() throws SQLException { //Skapa en meny med alternativ för att skapa användare, bok och författare
//        try {
            boolean exit = false;
            while (!exit) {
                System.out.println();
                System.out.println("===== Create menu =====");
                System.out.println("1. Create a member");
                System.out.println("2. Create a author");
                System.out.println("3. Create a book");
                System.out.println("4. Back");

                int selection = Integer.parseInt(input.nextLine());


                switch (selection) {
                    case 1:
                        createMemberorAuthor("member");
                        break;
                    case 2:
                        createMemberorAuthor("author");
                        break;
                    case 3:
                        createBook();
                        break;
                    default:
                        exit = true;
                }
            }
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//            System.out.println("Syntax ERROR, please try again!");
//        }
    }
    private static void createMemberorAuthor (String table) throws SQLException {
        System.out.println("Please fill in your information below.");

        System.out.print("First name: ");
        String firstName = input.nextLine();
        System.out.print("Last name: ");
        String lastName = input.nextLine();
        try {

            PreparedStatement statement = sqlStatement.getConnection().prepareStatement("INSERT INTO "+ table +" (firstName,lastName) VALUES (?,?);");
            statement.setString(1,firstName );
            statement.setString(2, lastName);
            statement.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }

        if (table.equals("member") ) {
            System.out.println("Member created. Welcome " + firstName + " " + lastName + "!");
        } else {
            System.out.println("Author created: " + firstName + " " + lastName );
        }
    }

    private static void createBook () throws SQLException {
        System.out.println("Please fill in your information below.");

        System.out.print("Title of the book: ");
        String bookTitle = input.nextLine();
        System.out.print("The book's ISBN (13 digits): ");
        String ISBN = input.nextLine();
        System.out.print("Publishing date: ");
        int publishingDate = Integer.parseInt(input.nextLine()); //Detta är dock en int, måste jag parsea den?
        System.out.println("Rating: ");
        float rating = Float.parseFloat(input.nextLine()); // detta är en float hur funkar det när den är string?

        sqlStatement.executeUpdate("INSERT INTO book (title, ISBN, publishingDate, rating) VALUES ('"+ bookTitle +"', '" + ISBN + "', '" + publishingDate + "', '" + rating + "');");
        ResultSet result = sqlStatement.executeQuery("SELECT * FROM book WHERE ISBN = " + ISBN +";");

        int bookID = 0;
        while (result.next()){
        bookID = result.getInt("bookID");
        }

        sqlStatement.executeUpdate("INSERT INTO inventory (bookID) VALUES ('" + bookID +"');");


        System.out.println("Created book:  ('" + bookTitle + "', '" + ISBN + "', '" + publishingDate + "', '" + rating + "');");
    }

    private static void deleteMenu() throws SQLException { //Skapa en meny med alternativ för att radera användare, bok och författare
        try {
            boolean exit = false;
            while (!exit) {
                System.out.println();
                System.out.println("===== Delete menu =====");
                System.out.println("1. Delete a member");
                System.out.println("2. Delete a author");
                System.out.println("3. Delete a book");
                System.out.println("4. Back");

                int selection = input.nextInt();
                input.nextLine();

                switch (selection) {
                    case 1:
                        deleteMember();
                        break;
                    case 2:
                        deleteAuthor();
                        break;
                    case 3:
                        deleteBook();
                        break;
                    default:
                        exit = true;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Syntax ERROR, please try again!");
        }
    }
    private static void deleteMember() throws SQLException {
        listTable("member");


        System.out.println("Put in the members ID to delete:");
        int memberID = Integer.parseInt(input.nextLine());


        int result = sqlStatement.executeUpdate("DELETE FROM member WHERE memberID =" + memberID + ";");

        if (result == 1) {
            System.out.println("The member with ID: " + memberID + " is now deleted!");
        } else {
            System.out.println("Delete failed, please put in the correct member ID");
        }
    }
    private static void deleteAuthor() throws SQLException {
        listTable("author");


        System.out.println("Put in the authors ID to delete:");
        int authorID = Integer.parseInt(input.nextLine());

        int result = sqlStatement.executeUpdate("DELETE FROM author WHERE authorID =" + authorID + ";");

        if (result == 1) {
            System.out.println("Author with ID: " + authorID + " is now deleted!");
        } else {
            System.out.println("Delete failed, please put in the correct author ID");
        }
    }

    private static void deleteBook() throws SQLException {
        listTable("book");

        System.out.println("Put in the books ID:");
        int bookID = Integer.parseInt(input.nextLine());

        int result = sqlStatement.executeUpdate("DELETE FROM book WHERE bookID =" + bookID + ";");

        if (result == 1) {
            System.out.println("The book with ID: " + bookID + " is now deleted!");
        } else {
            System.out.println("Delete failed, please put in the correct book ID");
        }
    }


    private static void borrowAbook() throws SQLException {
        String qry = "SELECT inventoryID, title, rating FROM inventory JOIN book ON inventory.bookID = book.bookID WHERE available = 1";
        print(qry);
        System.out.println("These are the available books");
        System.out.println("Pick the inventory ID of the book you want to borrow: ");
        int inventoryID = Integer.parseInt(input.nextLine());

        System.out.println("Put in your memberID: ");
        int memberID = Integer.parseInt(input.nextLine());

        sqlStatement.executeUpdate ("UPDATE inventory SET available = 0 WHERE inventoryID = " + inventoryID + ";");

        sqlStatement.executeUpdate("INSERT INTO borrow (startDate, inventoryID, memberID) VALUES (CURRENT_TIMESTAMP, "+inventoryID +","+memberID+");");

        System.out.println("\nThe book is now borrowed!");
        System.out.println("You now have a maximum of 21 days until return");

    }


    private static void returnAbook() throws SQLException {
        listTable("borrow");

        System.out.println("Put your borrow ID: ");
        int borrowID = Integer.parseInt(input.nextLine());

        ResultSet result = sqlStatement.executeQuery("SELECT * FROM borrow WHERE borrowID = "+ borrowID +";");

        int inventoryID = 0;
        while(result.next()){
            inventoryID =   result.getInt("inventoryID");

        }



        sqlStatement.executeUpdate("UPDATE borrow SET actualReturnDate = CURRENT_TIMESTAMP WHERE borrowID = "+ borrowID + ";");

        sqlStatement.executeUpdate ("UPDATE inventory SET available = 1 WHERE inventoryID = "+ inventoryID+ ";");

        System.out.println("\nThe book is now returned!");
        System.out.println("Thank you!");



    }

    private static void listTable(String tableName) throws SQLException {

        String qry = "SELECT * FROM "+ tableName + ";";
        print(qry);
    }

    private static void print(String qry) throws SQLException {


        //String qry = "SELECT * FROM "+ tableName + ";";

        try {
            sqlStatement=connection.createStatement();
            rs = sqlStatement.executeQuery(qry);
        } catch (SQLException e){
            e.printStackTrace();
        }

        // hämta antal kolumner
        int columnCount = rs.getMetaData().getColumnCount();
        // hämta alla kolmnnamn
        String[] columnNames = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            columnNames[i] = rs.getMetaData().getColumnName(i + 1);
        }

        // lägg kolumnnamn i string array
        for (String columnName : columnNames) {
            System.out.print(PadRight(columnName));
        }

        while (rs.next()) {
            System.out.println(rs);
            // hämta data för alla kolumner för den nuvarande raden
            for (String columnName : columnNames) {
                String value = rs.getString(columnName);

                if (value == null)
                    value = "null";

                System.out.print(PadRight(value));
            }
        }

        System.out.println();
    }

    private static String PadRight(String string) throws SQLException {
        int totalStringLength = 30;
        int charsToPadd = totalStringLength - string.length();

        // incase the string is the same length or longer than our maximum length
        if (string.length() >= totalStringLength)
            return string;

        StringBuilder stringBuilder = new StringBuilder(string);
        for (int i = 0; i < charsToPadd; i++) {
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    // public static int tryParse(String value) {
    //     return tryParse(value, 0);
    // }

    // public static int tryParse(String value, int defaultVal) {
    //     try {
    //         return Integer.parseInt(value);
    //     } catch (NumberFormatException e) {
    //         return defaultVal;
    //     }
    // }



}
