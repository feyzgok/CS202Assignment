package hotelmanagement;

import java.sql.*;
import java.util.Scanner;

public class Main {
//TODO:newID eklenirken auto increment olacak

    public static void main(String[] args) {

//bütün sql'ları sıra sıra yazıp sonradan functiona çevir
//kimse commit yapmasın bitirene kadar
        //bunun üzerine sadece kendi fonksiyonlarınızı ekleyin en son ikiniz birbirinizi override yapmaman için
//fonksiyonlar bittiğinde user interface'ını (menüyü) ekleyebiliriz

        try {
            System.out.println("Trying to establish Connection by using DBConnector");
            Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/public", "root", "password");
            System.out.println("Connected successfully,catalog name equals " + myConnection.getCatalog());

            //addNewHotel(myConnection);
            //viewAllHotels(myConnection);
            //updateHotel(myConnection);
            //deleteHotel(myConnection);

            System.out.println("Trying close connection");
            myConnection.close();
            System.out.println("Closed connection byebye");
        }
        catch(SQLException e){
            System.out.println("Received SQLException here are message:");
            System.out.println(e.getMessage());
        }
    }

    private static void addNewHotel(Connection myConnection) throws SQLException {
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

    private static void viewAllHotels(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAllHotels");
        //step1: bunun sql'ı nasıl olmalı?
        String sql = "SELECT * FROM public.hotel;";
        //burada dynamic bir şey yok (? gibi )
        //bu yüzden Statement yeterli
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        //Şu anda result set'i alıp içindekileri geri döndürüyoruz
        ResultSet rs = prep_statement.executeQuery();
        while (rs.next()) {//rs.next() -> boolean
            System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getString("address"));
        }
    }

    private static void updateHotel(Connection myConnection) throws SQLException {
        System.out.println("Now executing updateHotel");

        String sql = "UPDATE public.hotel SET address= ? , name= ? WHERE id= ?;";
        PreparedStatement  prep_statement = myConnection.prepareStatement(sql);


        System.out.println("Please enter the hotel address");
        Scanner in = new Scanner(System.in);
        String hotelAddress = in.nextLine();
        prep_statement.setString(1, hotelAddress);

        System.out.println("Please enter the hotel name");
        String hotelName  = in.nextLine();
        prep_statement.setString(2, hotelName);

        System.out.println("To update the given information, please enter the hotel ID that you want");
        int hotelId = in.nextInt();
        in.nextLine();
        prep_statement.setInt(3, hotelId);
        System.out.println("updated hotel successfully");

        prep_statement.executeUpdate();
    }

    private static void deleteHotel(Connection myConnection) throws SQLException {
        System.out.println("Now executing deleteHotel");
        PreparedStatement prep_statement = myConnection.prepareStatement("DELETE FROM public.hotel WHERE id= ?;");
        System.out.println("Please enter the hotel id to delete");
        Scanner in = new Scanner(System.in);
        int hotelId = in.nextInt();
        in.nextLine();
        prep_statement.setInt(1, hotelId);
        prep_statement.executeUpdate();

    }



}


