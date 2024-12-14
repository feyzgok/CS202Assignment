package hotelmanagement;

import java.awt.*;
import java.sql.*;
import java.util.Scanner;

public class Main {
//TODO:newID eklenirken auto increment olacak

    //sadece user type seçilecek
    //bir tane main functionda global static variable tut:current user static type--herkes içindekini okuyabilir
    //login yapıldıktan sonra
    //user type neye eşitse fonksiyonlara girmek istediğinde kontrol et--if currentUser==user gibisinden


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

            //addNewRoom(myConnection);
            //deleteRoom(myConnection);

            addNewUser(myConnection);
            //showAllRoomsByHotelId(myConnection);

            System.out.println("Trying close connection");
            myConnection.close();
            System.out.println("Closed connection byebye");
        }
        catch(SQLException e){
            System.out.println("Received SQLException here are message:");
            System.out.println(e.getMessage());
        }
    }

    //addRoom için 1. SQL ihtiyacım bu:
   /* INSERT INTO public.roomtype (price, type_Name, numPeople) VALUES(0, '', 0);
   *
   * room için sql:
   * INSERT INTO public.room (id, name, type_name, hotel_id) VALUES(0, '', '', 0);
   * */

    /* booking ve cleaning status yapılacak önce
    private static void manageRoomStatus(Connection myConnection) throws SQLException {
        System.out.println("Now executing manageRoomStatus");
        Scanner in = new Scanner(System.in);

        System.out.println("Please enter the hotel ID");
        System.out.println("Please enter the");

        --user type'a göre değişik menüler gösterecek
        1.check in
        2.check out--receptionist de yapabilir
        3.mark room for cleaning
        4.make payment

    }*/


    private static void addNewUser(Connection myConnection) throws SQLException {
        System.out.println("Now executing showAllRoomsByHotelId");
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the user ID");
        int userId = in.nextInt();
        in.nextLine();
        System.out.println("Please enter the username");
        String username = in.nextLine();
        System.out.println("Please enter the password");
        String password = in.nextLine();
        System.out.println("For employee enter:E/e");
        System.out.println("For housekeeper enter:H/h");
        System.out.println("For recep enter:R/r");
        String type="";

        while (type.isEmpty()){
            System.out.println("Please enter the type in this format");
            type = in.nextLine().toUpperCase();
            switch (type){
                case "E":
                    type="employee";
                    break;

                case "H":
                    type = "housekeeper";
                    break;

                case "R":
                    type = "receptionist";
                    break;
                default:
                    type="";
                    break;
            }
        }

        System.out.println("You selected: " + type);

        System.out.println("Please enter the name");
        String name = in.nextLine();
        System.out.println("Please enter the surname");
        String surname = in.nextLine();

        String sql = "INSERT INTO public.`user` (id, username, password, `type`, name, surname) VALUES(?, ?, ?, ?, ?, ?);";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);

        prep_statement.setInt(1, userId);
        prep_statement.setString(2, username);
        prep_statement.setString(3, password);
        prep_statement.setString(4, type);
        prep_statement.setString(5,name);
        prep_statement.setString(6,surname);

        prep_statement.executeUpdate();
    }

    private static void showAllRoomsByHotelId(Connection myConnection) throws SQLException {
        Scanner in = new Scanner(System.in);
        System.out.println("Now executing showAllRoomsByHotelId");
        System.out.println("Please enter the hotel ID");
        int hotelId = in.nextInt();
        in.nextLine();

        String sql =  " select public.room.*, public.roomtype.* from public.room join public.roomtype on public.room.type_name = public.roomtype.type_Name where public.room.hotel_id = ?";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        prep_statement.setInt(1,hotelId);

        ResultSet rs=prep_statement.executeQuery();
        System.out.println("id"+ " " + "name" + " " + "type_name");
        while (rs.next()){
            System.out.println(rs.getInt("id") + " " + rs.getString("name")+ " " + rs.getString("type_name"));
        }
    }


    private static void deleteRoom(Connection myConnection) throws SQLException {
        System.out.println("Now executing deleteRoom");
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the room ID");
        int roomId = in.nextInt();
        in.nextLine();

        PreparedStatement prep_statement = myConnection.prepareStatement("DELETE FROM public.room WHERE id= ?;");
        prep_statement.setInt(1, roomId);
        System.out.println("delete room set");

        System.out.println("Do you want to delete roomType too?");
        System.out.println("Y/N?");
        char choice = in.next().charAt(0);
        if (choice=='Y' || choice=='y'){
            PreparedStatement prep_statement_type =myConnection.prepareStatement("DELETE FROM public.roomtype WHERE type_Name= ?;");
            String roomTypeId ;

            PreparedStatement preparedStatement = myConnection.prepareStatement("select public.roomtype.type_Name from public.room join public.roomtype on public.room.type_name = public.roomtype.type_Name where public.room.id = 1");
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println(rs.next());
            roomTypeId=rs.getString("type_Name");
            System.out.println(roomTypeId);
            prep_statement_type.setString(1,roomTypeId);
            prep_statement.executeUpdate();
        }

        prep_statement.executeUpdate();
    }


    private static void addNewRoom(Connection myConnection) throws SQLException {
        Scanner in = new Scanner(System.in);
        System.out.println("Now executing addNewRoom");

        System.out.println("Please enter the hotel ID");
        int hotelId = in.nextInt();
        in.nextLine();

        //TODO:add room type için bütün room typeları select yapabilirsin.
        //Sonra system.out'da enter room type'da text istemek yerine bunları user'a Select room type or create new type diyebilirsin
        //böylece user eldekilerden birini ya seçer ya da yenisini oluşturmayı seçer

        System.out.println("Please enter the roomType");//user akışına uygun şekilde refactor edilecek
        String type_Name = in.nextLine();

        //room type add function
        //örnek: String sql = "SELECT * FROM user WHERE mail=?";

        String queryRoomTypeSql =  " select * from roomtype where type_Name = ?";

        PreparedStatement prep_statement = myConnection.prepareStatement(queryRoomTypeSql);
        prep_statement.setString(1, type_Name);
        ResultSet rs = prep_statement.executeQuery();

        if(!rs.next()){//roomType içinde bunun varlığını rs üzerinden kontrol eder sadece name'den alarak bakar(primarykey)
            System.out.println("Please enter the price");
            int roomPrice = in.nextInt();
            in.nextLine();

            System.out.println("Please enter the number of people");
            int numPeople = in.nextInt();
            in.nextLine();

            //System.out.println("size equals 0 entering condition");
            prep_statement = myConnection.prepareStatement("INSERT INTO public.roomtype (price, type_Name, numPeople) VALUES(?, ?, ?);");

            prep_statement.setInt(1, roomPrice);
            //System.out.println("statement price set");

            prep_statement.setString(2, type_Name);
            //System.out.println("statement name set successfully");

            prep_statement.setInt(3, numPeople);
            //System.out.println("statement name numPeople successfully");

            prep_statement.executeUpdate();
            System.out.println("addNewRoomType executed");
        }

        System.out.println("Please enter the room ID");
        int roomId = in.nextInt();
        in.nextLine();

        System.out.println("Please enter the room's name");
        String roomName = in.nextLine();

        //room add function
        prep_statement = myConnection.prepareStatement("INSERT INTO public.room (id, name, type_name, hotel_id) VALUES(?, ?, ?, ?);");
        prep_statement.setInt(1, roomId);

        //System.out.println("statement roomID set");
        prep_statement.setString(2, roomName);
        //System.out.println("statement roomName set");
        prep_statement.setString(3,type_Name);
        //System.out.println("statement roomtType set");
        prep_statement.setInt(4,hotelId);
        //System.out.println("statement hotelID set");

        prep_statement.executeUpdate();
        System.out.println("addNewRoom executed");
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