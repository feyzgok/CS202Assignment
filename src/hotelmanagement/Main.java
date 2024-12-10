package hotelmanagement;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hotel";
        try{
            System.out.println("Trying to establish Connection by using DBConnector");
            Connection myConnection= DriverManager.getConnection("jdbc:mysql://localhost:3306/public","root","password");
            System.out.println("Connected successfully,catalog name equals "+myConnection.getCatalog());

            int hotelId = 5;
            String hotelName = "HotelDynamicName";
            String hotelAddress = "Istanbul";



            System.out.println("Dynamiclly trying to do prepared statements ");
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

            System.out.println("Trying close connection");
            myConnection.close();
            System.out.println("Closed connection byebye");
        }
        catch(SQLException e){
            System.out.println("Received SQLException here are message:");
            System.out.println(e.getMessage());
        }
    }
}
