package hotelmanagement;

import java.sql.*;
import java.time.*;
import java.util.*;
import java.util.Date;

/*Bunların hepsine yarın sql yazılıp kontrol edilecek: todo first
            – View My Cleaning Schedule
 */



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

public class Main {
//TODO:newID eklenirken auto increment olacak--yarının ikinci işi

    //sadece user type seçilecek
    //bir tane main functionda global static variable tut:current user static type--herkes içindekini okuyabilir
    //login yapıldıktan sonra
    //user type neye eşitse fonksiyonlara girmek istediğinde kontrol et--if currentUser==user gibisinden


    public static void main(String[] args) {

        try {
            System.out.println("Trying to establish Connection by using DBConnector");
            Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/public", "root", "password");
            System.out.println("Connected successfully,catalog name equals " + myConnection.getCatalog());

            //addNewHotel(myConnection);
            //viewAllHotels(myConnection);
            //updateHotel(myConnection);
            //deleteHotel(myConnection);
            viewAllEmployeeswithTheirRoles(myConnection);
            //addNewRoom(myConnection);
            //deleteRoom(myConnection);
            //addNewUser(myConnection);

            //checkAdminById(myConnection);
            //checkRoomById(myConnection);
            //viewAllUsers(myConnection);
            //viewTypeOfUserById(myConnection);
            //viewPendingHousekeepingTasks(myConnection);

           //???viewAllEmployeeswithTheirRole(myConnection);

            //viewAllHousekeepingRecords(myConnection);
            //viewAllHousekeepingSchedules(myConnection);
            //showAllRoomsByHotelId(myConnection);

            //addNewGuest(myConnection);
            //deleteGuest(myConnection);
            //viewAllGuests(myConnection);

            System.out.println("Trying close connection");
            myConnection.close();
            System.out.println("Closed connection byebye");
        }
        catch(SQLException e){
            System.out.println("Received SQLException here are message:");
            System.out.println(e.getMessage());
        }
    }

    // Method to view all employees with their roles
    private static void viewAllEmployeeswithTheirRoles(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAllEmployeeswithTheirRole");
        String sql = "SELECT e.id AS employee_id, u.name, u.surname, " +
                "CASE " +
                "    WHEN EXISTS (SELECT 1 FROM public.housekeeping_staff hs WHERE hs.employee_id = e.id) THEN 'Housekeeper' " +
                "    WHEN EXISTS (SELECT 1 FROM public.receptionist r WHERE r.employee_id = e.id) THEN 'Receptionist' " +
                "    WHEN EXISTS (SELECT 1 FROM public.admin a WHERE a.employee_id = e.id) THEN 'Admin' " +
                "    ELSE 'Unknown' " +
                "END AS role " +
                "FROM public.employee e " +
                "JOIN public.user u ON e.user_id = u.id;";

        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        ResultSet rs = prep_statement.executeQuery();

        System.out.println("Employee ID | Name | Surname | Role");
        while (rs.next()) {
            System.out.println(
                    rs.getInt("employee_id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getString("surname") + " | " +
                            rs.getString("role")
            );
        }
    }

    // Method to view all housekeepers and their availability
    //TODO:availability için ekstra column mı eklemek lazım nasıl olacak bu iş düşün
    private static void viewAllHousekeepersRecordsAndAvailability(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAllHousekeepersRecordsAndAvailability");
        String sql = "SELECT hs.id AS housekeeper_id, e.id AS employee_id, u.name, u.surname, CASE WHEN EXISTS ( SELECT 1 FROM public.housekeeping_schedule hsched WHERE hsched.housekeeping_staff_id = hs.id AND hsched.cleaning_status = 'Pending') THEN 'Not Available' ELSE 'Available' END AS availability FROM public.housekeeping_staff hs JOIN public.employee e ON hs.employee_id = e.id JOIN public.user u ON e.user_id = u.id;";

        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        ResultSet rs = prep_statement.executeQuery();

        while (rs.next()) {
            System.out.println(
                    "Housekeeper ID" + rs.getInt("housekeeper_id") + " | " +
                    "Employee ID " +  rs.getInt("employee_id") + " | " +
                    "Name" + rs.getString("name") + " | " +
                    "Surname" + rs.getString("surname") + " | " +
                    "Availability" + rs.getString("availability")
            );
        }
    }

    // Method to view all housekeepers
    private static void viewAllHousekeepingRecords(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAllHousekeepingRecords");
        String sql = "SELECT  public.housekeeping_staff.id AS housekeeper_id, public.employee.id AS employee_id, public.`user`.name, public.`user`.surname,  public.housekeeping_staff.id AS housekeeping_staff_id FROM public.housekeeping_staff JOIN public.employee ON public.housekeeping_staff.employee_id =  public.employee.id JOIN public.`user` ON  public.employee.user_id = public.`user`.id ;";

        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        ResultSet rs = prep_statement.executeQuery();

        while (rs.next()) {
            System.out.println("Housekeeper ID:" + rs.getInt("housekeeper_id") + " | " + "Employee ID:" + rs.getInt("employee_id") + " | " + "Name:" + rs.getString("name") + " | " + "Surname:" + rs.getString("surname"));
        }
    }

    // Method to create a new housekeeping schedule
    private static void addNewHousekeepingSchedule(Connection myConnection) throws SQLException {
        System.out.println("Now executing addNewHousekeepingSchedule");
        Scanner in = new Scanner(System.in);

        System.out.println("Enter schedule ID:");
        int scheduleId = in.nextInt();
        in.nextLine();

        System.out.println("Enter room ID:");
        int roomId = checkRoomById(myConnection);

        System.out.println("Enter housekeeping staff ID:");
        int staffId = in.nextInt();
        in.nextLine();

        System.out.println("Enter schedule date (yyyy-mm-dd):");
        String scheduleDate = in.nextLine();

        System.out.println("Enter cleaning status (Pending/Completed):");
        String cleaningStatus;

        while (true){
            cleaningStatus = in.nextLine();
            if (cleaningStatus.equals("Pending") || cleaningStatus.equals("Completed")){
                break;
            }
            System.out.println("Invalid input. Please enter either 'Pending' or 'Completed'.");
        }

        System.out.println("Enter receptionist ID:");
        int receptionistId = in.nextInt();
        in.nextLine();

        String sql = "INSERT INTO public.housekeeping_schedule (id, room_id, housekeeping_staff_id, scheduledate, cleaning_status, receptionist_id) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        prep_statement.setInt(1, scheduleId);
        prep_statement.setInt(2, roomId);
        prep_statement.setInt(3, staffId);
        prep_statement.setString(4, scheduleDate);
        prep_statement.setString(5, cleaningStatus);
        prep_statement.setInt(6, receptionistId);

        prep_statement.executeUpdate();
        System.out.println("Housekeeping schedule created successfully.");
    }

    // Method to update an existing housekeeping schedule to completed directly
    private static void updateHousekeepingScheduleToComplete(Connection myConnection) throws SQLException {
        System.out.println("Now executing updateHousekeepingSchedule");
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the schedule ID to update:");
        int scheduleId = in.nextInt();
        in.nextLine();

        String cleaningStatus = "Completed" ;

        String sql = "UPDATE public.housekeeping_schedule SET cleaning_status = ? WHERE id = ?;";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        prep_statement.setString(1, cleaningStatus);
        prep_statement.setInt(2, scheduleId);

        prep_statement.executeUpdate();
        System.out.println("Housekeeping schedule updated successfully.");
    }

    // Method to update an existing housekeeping schedule
    private static void updateHousekeepingSchedule(Connection myConnection) throws SQLException {
        System.out.println("Now executing updateHousekeepingSchedule");
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the schedule ID to update:");
        int scheduleId = in.nextInt();
        in.nextLine();

        System.out.println("Enter new cleaning status (Pending/Completed):");
        String cleaningStatus ;

        while (true){
            cleaningStatus = in.nextLine();
            if (cleaningStatus.equals("Pending") || cleaningStatus.equals("Completed")){
                break;
            }
            System.out.println("Invalid input. Please enter either 'Pending' or 'Completed'.");
        }

        String sql = "UPDATE public.housekeeping_schedule SET cleaning_status = ? WHERE id = ?;";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        prep_statement.setString(1, cleaningStatus);
        prep_statement.setInt(2, scheduleId);

        prep_statement.executeUpdate();
        System.out.println("Housekeeping schedule updated successfully.");
    }

    // Method to delete a housekeeping schedule
    private static void deleteHousekeepingSchedule(Connection myConnection) throws SQLException {
        System.out.println("Now executing deleteHousekeepingSchedule");
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the schedule ID to delete:");
        int scheduleId = in.nextInt();
        in.nextLine();

        String sql = "DELETE FROM public.housekeeping_schedule WHERE id = ?;";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        prep_statement.setInt(1, scheduleId);

        prep_statement.executeUpdate();
        System.out.println("Housekeeping schedule deleted successfully.");
    }

    // Method to view all housekeeping schedule records
    private static void viewAllHousekeepingSchedules(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAllHousekeepingSchedule");
        String sql = "SELECT * FROM public.housekeeping_schedule;";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        ResultSet rs = prep_statement.executeQuery();

        while (rs.next()) {
            System.out.println("ID:"+ rs.getInt("id") + " | "
                    + "Room ID:" + rs.getInt("room_id") + " | "
                    + "Staff ID:" + rs.getInt("housekeeping_staff_id") + " | "
                    + "Schedule Date:" + rs.getDate("scheduledate") + " | "
                    + "Cleaning status" + rs.getString("cleaning_status") + " | "
                    + "Receptionist ID:" + rs.getInt("receptionist_id")
            );
        }
    }

    // Method to view pending housekeeping tasks
    private static void viewPendingHousekeepingTasks(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewPendingHousekeepingTasks");
        String sql = "SELECT * FROM public.housekeeping_schedule WHERE cleaning_status = 'Pending';";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        ResultSet rs = prep_statement.executeQuery();

        while (rs.next()) {
            System.out.println( "ID:" + rs.getInt("id") + " | " +
                            "Room ID:" + rs.getInt("room_id") + " | " +
                            "Staff ID:" +rs.getInt("housekeeping_staff_id") + " | " +
                            "Schedule Date:" + rs.getDate("scheduledate") + " | " +
                            "Receptionist ID:" + rs.getInt("receptionist_id")
            );
        }
    }

    // Method to mark task status as completed
    private static void updateTaskStatusToCompleted(Connection myConnection) throws SQLException {
        System.out.println("Now executing updateTaskStatusToCompleted");
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the schedule ID to mark as completed:");
        int scheduleId = in.nextInt();
        in.nextLine();

        String sql = "UPDATE public.housekeeping_schedule SET cleaning_status = 'Completed' WHERE id = ?;";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        prep_statement.setInt(1, scheduleId);

        prep_statement.executeUpdate();
        System.out.println("Task status updated to Completed successfully.");
    }

    // Method to view a specific housekeeper's cleaning schedule
    private static void viewMyCleaningSchedule(Connection myConnection, int housekeeperId) throws SQLException {
        System.out.println("Now executing viewMyCleaningSchedule");
        String sql = "SELECT * FROM public.housekeeping_schedule WHERE housekeeping_staff_id = ?;";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        prep_statement.setInt(1, housekeeperId);
        ResultSet rs = prep_statement.executeQuery();

        System.out.println("ID | Room ID | Schedule Date | Cleaning Status | Receptionist ID");
        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + " | " +
                            rs.getInt("room_id") + " | " +
                            rs.getDate("scheduledate") + " | " +
                            rs.getString("cleaning_status") + " | " +
                            rs.getInt("receptionist_id")
            );
        }
    }

    private static void addNewAdmin(Connection myConnection, int admin_employeeId) throws SQLException {
        System.out.println("Now executing addNewAdmin");
        Scanner in = new Scanner(System.in);

        System.out.println("enter id");
        int adminId = in.nextInt();
        in.nextLine();

        PreparedStatement prep_statement = myConnection.prepareStatement("INSERT INTO public.admin (id, employee_id) VALUES(?, ?);");
        prep_statement.setInt(1,adminId);
        prep_statement.setInt(2,admin_employeeId);
        prep_statement.executeUpdate();
    }

    // Method to check if a room exists by its ID
    private static int checkRoomById(Connection myConnection) throws SQLException {
        System.out.println("Now executing checkRoomById");
        Scanner in = new Scanner(System.in);
        int roomId = 0;
        boolean validRoomId = false;

        String roomSql = "SELECT id, name, type_name, hotel_id FROM public.room WHERE public.room.id = ?;";

        // Valid room ID check (ensures the ID exists in the table)
        while (!validRoomId) {
            System.out.println("Enter a valid room ID:");

            try {
                roomId = in.nextInt();
                in.nextLine(); // Consume the newline

                try (PreparedStatement room_prep_statement = myConnection.prepareStatement(roomSql)) {
                    room_prep_statement.setInt(1, roomId);

                    try (ResultSet rs = room_prep_statement.executeQuery()) {
                        if (rs.next()) {
                            System.out.println("You are lucky. The room with the given ID exists.");
                            validRoomId = true;
                        } else {
                            System.out.println("There is no room with this ID. Please try again.");
                        }
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric room ID.");
                in.nextLine(); // Clear the invalid input
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
                return 0; // Exit--roomId 0 can't be valid
            }
        }

        return roomId;
    }

    private static int checkAdminById(Connection myConnection) throws SQLException {
        System.out.println("Now executing checkAdminById");
        Scanner in = new Scanner(System.in);
        int adminId = 0;
        boolean validAdminId = false;

        String adminSql = "SELECT id, employee_id FROM public.admin WHERE public.admin.id = ?;";

        // valid admin id alır (tabloda halihazırda varsa validtir)
        while (!validAdminId) {
            System.out.println("Enter a valid admin ID:");

            try {
                adminId = in.nextInt();
                in.nextLine(); // Consume the newline

                try (PreparedStatement admin_prep_statement = myConnection.prepareStatement(adminSql)) {
                    admin_prep_statement.setInt(1, adminId);

                    try (ResultSet rs = admin_prep_statement.executeQuery()) {
                        if (rs.next()) {
                            System.out.println("You are lucky. The admin with the given ID exists.");
                            validAdminId = true;
                        } else {
                            System.out.println("There is no admin with this ID. Please try again.");
                        }
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric admin ID.");
                in.nextLine(); // Clear the invalid input
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
                return 0; // Exit--adminId 0 can't be valid
            }
        }

        //System.out.println("adminId:" +adminId);
        return adminId;
    }


    //checks Hotels to return valid existed hotel's id
    private static int checkHotelById(Connection myConnection) throws SQLException {
        System.out.println("Now executing checkHotelById");
        Scanner in = new Scanner(System.in);
        int hotelId = 0;
        boolean validHotelId = false;

        String hotelSql = "SELECT id, address, name FROM public.hotel WHERE public.hotel.id = ?;";

        // valid hotel id alır ( tabloda halihazırda varsa validtir)
        while (!validHotelId) {
            System.out.println("Enter a valid hotel ID:");

            try {
                hotelId = in.nextInt();
                in.nextLine(); // Consume the newline

                try (PreparedStatement hotel_prep_statement = myConnection.prepareStatement(hotelSql)) {
                    hotel_prep_statement.setInt(1, hotelId);

                    try (ResultSet rs = hotel_prep_statement.executeQuery()) {
                        if (rs.next()) {
                            System.out.println("You are lucky. The hotel with the given ID exists.");
                            validHotelId = true;
                        } else {
                            System.out.println("There is no hotel with this ID. Please try again.");
                        }
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric hotel ID.");
                in.nextLine();
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
                return 0; // Exit--hotelId 0 olamaz
            }
        }

        return hotelId;
    }

    //creates revenue report and adds to revenueReport table
    private static void addNewRevenueReport(Connection myConnection) throws SQLException {
        System.out.println("Now executing addNewRevenueReport");
        Scanner in = new Scanner(System.in);
        System.out.println("enter the revenue ID");
        int id = in.nextInt();
        in.nextLine();

        int hotelId = checkHotelById(myConnection);
        int adminId = checkAdminById(myConnection);

        System.out.println("Enter Start Date (yyyy-mm-dd): ");
        String startDateInput = in.next();
        Date startDate = java.sql.Date.valueOf(LocalDate.parse(startDateInput));
        System.out.println("Enter End Date (yyyy-mm-dd): ");
        String endDateInput = in.next();
        Date endDate = java.sql.Date.valueOf(LocalDate.parse(endDateInput));


    }

    //TODO:BURCU'YA RECEPTIONIST VE HOUSEKEEPERLARA DA AD SOYAD VS. EKLESEK MI DIYE SOR !!!
    //YAPMASI RAHAT AMA ÖNCE DB DEĞİŞMELİ
    private static void addNewReceptionist(Connection myConnection, int receptionist_employeeId) throws SQLException {
        System.out.println("Now executing addNewReceptionist");
        Scanner in = new Scanner(System.in);

        System.out.println("enter id");
        int receptionId = in.nextInt();
        in.nextLine();

        PreparedStatement prep_statement = myConnection.prepareStatement("INSERT INTO public.receptionist (id, employee_id) VALUES(?, ?);");
        prep_statement.setInt(1,receptionId);
        prep_statement.setInt(2,receptionist_employeeId);
        prep_statement.executeUpdate();
    }

    private static void addNewHousekeeper(Connection myConnection, int housekeeper_employeeId) throws SQLException {
        System.out.println("Now executing addNewHousekeeper");
        Scanner in = new Scanner(System.in);

        System.out.println("Please enter the housekeeper ID");
        int housekeeperId = in.nextInt();
        in.nextLine();

        PreparedStatement prep_statement = myConnection.prepareStatement("INSERT INTO public.housekeeping_staff (id, employee_id) VALUES(?, ?);");
        prep_statement.setInt(1,housekeeperId);
        prep_statement.setInt(2,housekeeper_employeeId);
        prep_statement.executeUpdate();
    }

    private static void addNewEmployee(Connection myConnection, int employee_userId, int choice) throws SQLException {
        System.out.println("Now executing addNewEmployee");
        Scanner in = new Scanner(System.in);

        int hotelId = checkHotelById(myConnection);

        //System.out.println("Function continues...");

        // Insert the new employee
        String sql = "INSERT INTO public.employee (id, user_id, hotel_id) VALUES (?, ?, ?);";

        System.out.println("Please enter the ID for the employee:");
        int id = in.nextInt();
        in.nextLine();

        try (PreparedStatement prep_statement = myConnection.prepareStatement(sql)) {
            prep_statement.setInt(1, id);
            prep_statement.setInt(2, employee_userId);
            prep_statement.setInt(3, hotelId);

            prep_statement.executeUpdate();
            System.out.println("Employee added successfully.");

            // Determine the type of employee based on the choice
            switch (choice) {
                case 1:
                    addNewHousekeeper(myConnection, id);
                    break;
                case 2:
                    addNewReceptionist(myConnection, id);
                    break;

                case 3:
                    addNewAdmin(myConnection, id);
                default:
                    System.out.println("Invalid choice. No specific role assigned.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting employee: " + e.getMessage());
        }
    }


    private static void addNewEmployee0(Connection myConnection, int employee_userId, int choice) throws SQLException {
        System.out.println("Now executing addNewEmployee");
        Scanner in = new Scanner(System.in);

        int hotelId = 0;

        String hotelSql;
        PreparedStatement hotel_prep_statement ;
        ResultSet rs = null;
        
        while (rs==null){
            System.out.println("enter valid hotel id");
            try{
                hotelId = in.nextInt();
                in.nextLine();
            }
            catch(Exception e){
                System.out.println(e);
            }


            //System.out.println("hotelSql connect edemedilerimizdenmisiniz");
            hotelSql = "SELECT id, address, name FROM public.hotel where public.hotel.id = ?;";
            //System.out.println("hotelSql connect edilecek");
            hotel_prep_statement = myConnection.prepareStatement(hotelSql);
            hotel_prep_statement.setInt(1,hotelId);
            //System.out.println("hotelSql connect edildi");
            rs = hotel_prep_statement.executeQuery();
            //System.out.println("hotelSql connect edilmiştir");
            if(rs.next()){
                System.out.println(rs.getInt("id"));
                System.out.println("You are lucky. The hotel with given id is already existed");
                break;
            }
            else{
                System.out.println("There is no hotel with this id");
                rs=null;
            }
        }

        //System.out.println("fonksiyon kaldığı yerden devam ediyor...");
        String sql ="INSERT INTO public.employee (id, user_id, hotel_id) VALUES(?, ?, ?);";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        System.out.println("Please enter the id for employee");
        int id = in.nextInt();
        in.nextLine();
        prep_statement.setInt(1,id);
        prep_statement.setInt(2,employee_userId);
        prep_statement.setInt(3,hotelId);

        prep_statement.executeUpdate();

        switch (choice){
            case 1:
                addNewHousekeeper(myConnection, id);
                break;
            case 2:
                addNewReceptionist(myConnection, id);
        }
    }

    private static void addNewGuest(Connection myConnection, String guest_name,  String guest_surname, int guest_userId) throws SQLException {
        System.out.println("Now executing addNewGuest");
        Scanner in = new Scanner(System.in);

        System.out.println("Please enter the guest ID");
        int guestId = in.nextInt();
        in.nextLine();
        String guestName = guest_name;
        String guestSurname = guest_surname;
        System.out.println("Please enter the guest phone number");
        String guestPhone = in.nextLine();
        int userId = guest_userId;

        PreparedStatement prep_statement = myConnection.prepareStatement("INSERT INTO public.guest (id, name, surname, phone_num, user_id) VALUES(?, ?, ?, ?, ?);");
        prep_statement.setInt(1,guestId);
        prep_statement.setString(2, guestName);
        prep_statement.setString(3, guestSurname);
        prep_statement.setInt(4, Integer.valueOf(guestPhone));//TODO: LONG/INT için gerçek numaralar denedik -- tekrar denenecek
        prep_statement.setInt(5, userId);

        prep_statement.executeUpdate();
    }

    private static void addNewGuest(Connection myConnection) throws SQLException {
        System.out.println("Now executing addNewGuest");
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the guest ID");
        int guestId = in.nextInt();
        in.nextLine();
        System.out.println("Please enter the guest name");
        String guestName = in.nextLine();
        System.out.println("Please enter the guest surname");
        String guestSurname = in.nextLine();
        System.out.println("Please enter the guest phone number");
        String guestPhone = in.nextLine();
        System.out.println("Please enter the guest user id");
        int userId = in.nextInt();
        in.nextLine();

        PreparedStatement prep_statement = myConnection.prepareStatement("INSERT INTO public.guest (id, name, surname, phone_num, user_id) VALUES(?, ?, ?, ?, ?);");
        prep_statement.setInt(1,guestId);
        prep_statement.setString(2, guestName);
        prep_statement.setString(3, guestSurname);
        prep_statement.setInt(4, Integer.valueOf(guestPhone));//TODO: LONG/INT için gerçek numaralar denedik -- tekrar denenecek
        prep_statement.setInt(5,userId);

        prep_statement.executeUpdate();
    }

    private static void deleteGuest(Connection myConnection) throws SQLException {
        System.out.println("Now executing deleteGuest");
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the guest ID");
        int guestId = in.nextInt();
        in.nextLine();

        PreparedStatement prep_statement = myConnection.prepareStatement("DELETE FROM public.guest WHERE id= ?;");
        prep_statement.setInt(1, guestId);
        prep_statement.executeUpdate();
    }

    //idsi girilen USERIN BOOKLADIĞI ODALARIN ROOM IDLERINI LISTELER
    private static void viewAllBookingRoomIdByGuestlId(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAllBookingRoomIdByGuestlId");
        String sql = "SELECT public.booking.* FROM public.booking join public.guest on public.booking.guest_id = public.guest.id where public.guest.id = 1 ;";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        ResultSet rs = prep_statement.executeQuery();
        while (rs.next()){
            System.out.println("id" + " " + "booking room id" + " " + "start date" + " " + "end date");
            System.out.println(rs.getInt("id") + " " + rs.getString("room_id") + " " + rs.getString("startdate") + " " + rs.getString("enddate"));
        }
    }

    private static void viewAllGuests(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAllGuests");
        PreparedStatement prep_statement = myConnection.prepareStatement("SELECT id, name, surname, phone_num, user_id FROM public.guest;");
        ResultSet rs=prep_statement.executeQuery();
        while (rs.next()){
            System.out.println(rs.getInt("id") + " " + rs.getString("name")+ " " + rs.getString("surname")+" "+ rs.getInt("phone_num"));
        }
    }

    private static void viewAllUsers(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAllUsers");
        PreparedStatement prep_statement = myConnection.prepareStatement("SELECT id, username, password, `type`, name, surname FROM public.`user`;");
        ResultSet rs = prep_statement.executeQuery();
        while (rs.next()){
            System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getString("surname"));
        }
    }

    //bunu doğru anladığımı düşünmüyorum.TODO:bunu öğren amin!e sor
    private static void viewAllEmployeeswithTheirRole(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAllEmployeeswithTheirRole");
        System.out.println("For housekeeper enter:H/h");
        System.out.println("For receptionist enter:R/r");
        System.out.println("For admin enter:A/a");

        Scanner in = new Scanner(System.in);
    }

    private static void viewAllUserswithTheirRole(Connection myConnection) throws SQLException{
        System.out.println("Now executing viewAllUserswithTheirRole");
        System.out.println("For housekeeper enter:H/h");
        System.out.println("For receptionist enter:R/r");
        System.out.println("For guest enter: G/g");
        System.out.println("For admin enter:A/a");
        Scanner in = new Scanner(System.in);

        String type="";

        while (type.isEmpty()){
            System.out.println("Please enter the type in this format");
            type = in.nextLine().toUpperCase();

            switch (type){
                case "G":
                    type = "guest";
                    break;

                case "H":
                    type = "housekeeper";
                    break;

                case "R":
                    type = "receptionist";
                    break;

                case "A":
                    type = "admin";
                    break;

                default:
                    type="";
                    break;
            }
        }

        System.out.println("You selected: " + type);


        PreparedStatement prep_statement = myConnection.prepareStatement("SELECT id, username, password, `type`, name, surname FROM public.`user` where public.user.`type`= ? ;");
        prep_statement.setString(1, type);
        ResultSet rs = prep_statement.executeQuery();
        while (rs.next()){
            System.out.println(rs.getInt("id") + " " + rs.getString("name") + " " + rs.getString("surname"));//username ve password özel veri diye göstermek istemedim
        }

    }

    private static void viewTypeOfUserById(Connection myConnection) throws SQLException {
        //userı getir -- employeeıd'den getir user idyi
        //user idsini employeeden çekip listelettiğin userların type'ını çekmelisin
        System.out.println("Now executing viewTypeOfUserById. It enables to find the type/role of user");
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the user ID");
        int userId = in.nextInt();
        in.nextLine();

        PreparedStatement prep_statement = myConnection.prepareStatement("SELECT `type` FROM public.`user` where public.user.id = ?;");
        prep_statement.setInt(1, userId);
        ResultSet rs = prep_statement.executeQuery();
        while (rs.next()){
            System.out.println("User type: "+rs.getString("type"));
        }
    }

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
        System.out.println("For housekeeper enter:H/h");
        System.out.println("For receptionist enter:R/r");
        System.out.println("For guest enter: G/g");
        System.out.println("For admin enter:A/a");
        String type="";

        int choice=-1;
        while (type.isEmpty()){
            System.out.println("Please enter the type in this format");
            type = in.nextLine().toUpperCase();

            switch (type){
                case "G":
                    type = "guest";
                    choice = 0;
                    break;

                case "H":
                    type = "housekeeper";
                    choice=1;//employeeId
                    break;

                case "R":
                    type = "receptionist";
                    choice=2;//employeeId
                    break;

                case "A":
                    type = "admin";
                    choice=3;
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

        if(choice==0){
            String guest_name=name;
            String guest_surname=surname;
            addNewGuest(myConnection, guest_name, guest_surname, userId);
        }
        else{
            addNewEmployee(myConnection, userId, choice);
        }
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