package hotelproject;

import java.sql.*;
import java.util.Scanner;
import java.util.stream.Stream;

import hotelproject.CustomerView.CustomerMenu;

public class RoomAvailability {
    static JDBC jdbc = new JDBC(); 
    static Statement sqlStatement = null;
    static Connection connection = null;
    static ResultSet rs = null;
    static PreparedStatement statement = null;
    

    public void showRooms() throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUser(), jdbc.getPassword())) {
            sqlStatement = connection.createStatement();
            Scanner scanner = new Scanner(System.in);
            int exit = 0;
            CustomerMenu customerMenu = new CustomerMenu();

                ListTable("room_type");

                System.out.println("Press 0 to return to previus menu");

                exit = scanner.nextInt();
                switch (exit) {
                    case 0:
                       customerMenu.customermenu();
                        break;
                    default:
                    customerMenu.customermenu();
                        break;
                }
                //Remove?
                exit = scanner.nextInt();

        } catch (SQLException e) {
            System.out.println("Error, Can't show room right now");
        }
    }

    private static String PadRight(String string) {
        int totalStringLength = 30;
        int charsToPadd = totalStringLength - string.length();

        // incase the string is the same length or longer than our maximum lenght
        if (string.length() >= totalStringLength)
            return string;

        StringBuilder stringBuilder = new StringBuilder(string);
        for (int i = 0; i < charsToPadd; i++) {
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    public static void ListTable(String tableName) throws SQLException {
        try (Connection connection = DriverManager.getConnection(jdbc.getUrl(), jdbc.getUser(), jdbc.getPassword())) {
            sqlStatement = connection.createStatement();

            try {
                // Utför query mot databas
               //rs = sqlStatement.executeQuery("SELECT room_type.room_name, COUNT(*) AS available_rooms FROM "+ tableName + " room_type JOIN room_info ON room_info.room_type_id  = room_type.room_type_id group by room_name;");
               rs = sqlStatement.executeQuery("SELECT room_name, COUNT(*) AS available_rooms FROM room_type JOIN room_info ON room_info.room_type_id  = room_type.room_type_id where available = 0 group by room_name;");

                // hämta antal kolumner
                int columnCount = rs.getMetaData().getColumnCount();

                // hämta alla kolmnnamn
                String[] columnNames = new String[columnCount];

        
                for (int i = 0; i < columnCount; i++) {
                    columnNames[i] = rs.getMetaData().getColumnName(i + 1);
                }

                // lägg kolumnnamn i string array
                // for (String columnName : columnNames) {
                //     System.out.print(PadRight(columnName));
                // }
                
                    // LAMBDA!!!!
                Stream.of(columnNames).forEach(columnName -> {
                    System.out.print(PadRight(columnName));
                });

                while (rs.next()) {
                    System.out.println();
                    
                    for (String columnName : columnNames) {
                        String value = rs.getString(columnName);

                        if (value == null)
                            value = "null";

                        System.out.print(PadRight(value));
                    }
                }

            } catch (SQLException e) {
                System.out.println("Error");
            }
        }

    }
}
