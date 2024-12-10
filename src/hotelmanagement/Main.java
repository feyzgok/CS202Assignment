package hotelmanagement;

import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {



        try {
            System.out.println("Trying to establish Connection by using DBConnector");
            Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/public", "root", "password");
            System.out.println("Connected successfully,catalog name equals " + myConnection.getCatalog());

            addHotel(myConnection);
            System.out.println("Trying close connection");
            myConnection.close();
            System.out.println("Closed connection byebye");
        }
        catch(SQLException e){
            System.out.println("Received SQLException here are message:");
            System.out.println(e.getMessage());
        }
    }

    private static void addHotel(Connection myConnection) throws SQLException {
        System.out.println("Now executing addHotel");
        int hotelId;
        String hotelName;
        String hotelAddress;

        System.out.println("Please enter the hotel ID");
        Scanner in = new Scanner(System.in);
        hotelId = in.nextInt();
        in.nextLine();//ikinci satıra geçmesi için nextLine kullandık
        System.out.println("Please enter the hotel name");
        hotelName = in.nextLine();
        //burada kendiliğinden string alırken diğer satıra geçiş yaptı
        System.out.println("Please enter the hotel address");
        hotelAddress = in.nextLine();


        System.out.println("Dynamically trying to do prepared statements ");
        PreparedStatement prep_statement = myConnection.prepareStatement("INSERT INTO public.hotel (id, address, name) VALUES(?, ?, ?);");
        prep_statement.setInt(1, hotelId);
        System.out.println("setted hotel id");
        prep_statement.setString(2, hotelAddress);
        System.out.println("setted hotel address");
        prep_statement.setString(3, hotelName);
        System.out.println("setted hotel name");
        System.out.println("Prepared Statement now Trying to Execute Update");
        prep_statement.executeUpdate();
        System.out.println("Executed Update Statement successfully");

    }
}


