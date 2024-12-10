package hotelmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/hotel";
        try{
            System.out.println("Trying to establish Connection by using DBConnector");
            Connection myConnection= DriverManager.getConnection("jdbc:mysql://localhost:3306/public","root","password");
            System.out.println("Connected successfully,catalog name equals "+myConnection.getCatalog());
            Statement prep_statement = myConnection.createStatement();
            System.out.println("Prepared Statement now Trying to Execute Update");
            prep_statement.executeUpdate( "INSERT INTO public.hotel (id, address, name) VALUES(3, 'Gok2', 'okul');" );
            System.out.println("Executed Update Statement successfully");

        }
        catch(SQLException e){
            System.out.println("Received SQLException here are message:");
            System.out.println(e.getMessage());
        }


    }
}
