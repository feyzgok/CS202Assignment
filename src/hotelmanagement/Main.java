package hotelmanagement;

import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.*;



public class Main {

    public static void main(String[] args) {

//bütün sql'ları sıra sıra yazıp sonradan functiona çevir
//kimse commit yapmasın bitirene kadar
        //bunun üzerine sadece kendi fonksiyonlarınızı ekleyin en son ikiniz birbirinizi override yapmaman için
//fonksiyonlar bittiğinde user interface'ını (menüyü) ekleyebiliriz

        try {
            System.out.println("Trying to establish Connection by using DBConnector");
            Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/public", "root", "password");
            System.out.println("Connected successfully,catalog name equals " + myConnection.getCatalog());

            while (true) {
                displayMainMenu();
                int choice = getUserChoice();

                switch (choice) {
                    case 1:
                        handleGuestMenu(myConnection);
                        break;
                    case 2:
                        handleReceptionistMenu(myConnection);
                        break;
                    case 3:
                        handleAdministratorMenu(myConnection);
                        break;
                    case 4:
                        handleHousekeepingMenu(myConnection);
                        break;
                    case 5:
                        System.out.println("Thank you for using the Hotel Management System. Goodbye!");
                        myConnection.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    private static void displayMainMenu() {
        System.out.println("\n=== Hotel Management System ===");
        System.out.println("1. Guest Portal");
        System.out.println("2. Receptionist Portal");
        System.out.println("3. Administrator Portal");
        System.out.println("4. Housekeeping Portal");
        System.out.println("5. Exit");
        System.out.print("Enter your choice (1-5): ");
    }

    private static int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void handleGuestMenu(Connection conn) throws SQLException {
        while (true) {
            System.out.println("\n=== Guest Portal ===");
            System.out.println("1. View Available Rooms For Today");
            System.out.println("2. Make New Booking");
            System.out.println("3. View My Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Make Payment");
            System.out.println("6. View My Payment");
            System.out.println("7. View All Available Rooms By Hotel ID For A Period");
            System.out.println("8. View All Rooms By Hotel ID");
            System.out.println("9. Return to Main Menu");
            System.out.print("Enter your choice (1-9): ");

            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    viewAvaibleRooms(conn);
                    break;
                case 2:
                    addNewBooking(conn);
                    break;
                case 3:
                    viewBooking(conn);
                    break;
                case 4:
                    cancelBooking(conn);
                    break;
                case 5:
                    makePayment(conn);
                    break;

                case 6:
                    viewMyPayment(conn);
                    break;
                case 7:
                    viewAllAvailableRoomsByHotelIdForPeriod(conn);
                    break;
                case 8:
                    viewAllRoomsByHotelId(conn);
                    break;

                case 9:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void handleReceptionistMenu(Connection conn) throws SQLException {
        while (true) {
            System.out.println("\n=== Receptionist Portal ===");
            System.out.println("1. View All Bookings");
            System.out.println("2. Confirm Booking");
            System.out.println("3. Process Check-in");
            System.out.println("4. Process Check-out");
            System.out.println("5. Process Payment");
            System.out.println("6. Modify Booking");
            System.out.println("7. Assign Housekeeping Task");
            System.out.println("8. View All Housekeepers Records and Their Availability");
            System.out.println("9. Delete Booking");
            System.out.println("10. View All Guests");
            System.out.println("11. Return to Main Menu");

            System.out.print("Enter your choice (1-11): ");

            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    viewAllBookingS(conn);
                    break;
                case 2:
                    confirmBookingReceptionist(conn);
                    break;
                case 3:
                    checkIn(conn);
                    break;
                case 4:
                    checkOut(conn);
                    break;
                case 5:
                    processPayment(conn);
                    break;
                case 6:
                    modifyBooking(conn);
                    break;
                case 7:
                    assignHousekeepingTask(conn);
                    break;
                case 8:
                    viewAllHousekeepersRecordsAndAvailability(conn);
                    break;
                case 9:
                    deleteBooking(conn);
                    break;
                case 10:
                    viewAllGuests(conn);
                    break;
                case 11:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }
    private static void handleAdministratorMenu(Connection conn) throws SQLException {
        while (true) {
            System.out.println("\n=== Administrator Portal ===");
            System.out.println("1. Add New Hotel");
            System.out.println("2. View All Hotels");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Add New User Account");
            System.out.println("5. View All the Employees with Their Role");
            System.out.println("6. View All Housekeeping Records");
            System.out.println("7. Delete User Account");
            System.out.println("8. View All the User by Their Role");
            System.out.println("9. Delete Room");
            System.out.println("10. Delete Hotel");
            System.out.println("11. Add New Room");
            System.out.println("12. Update Hotel");
            System.out.println("13. Return to Main Menu");

            System.out.print("Enter your choice (1-13): ");

            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    addNewHotel(conn);
                    break;
                case 2:
                    viewAllHotels(conn);
                    break;
                case 3:
                    cancelBookingAdmin(conn);
                    break;
                case 4:
                    addNewUser(conn);
                    break;
                case 5:
                    viewAllEmployeeswithTheirRoles(conn);
                    break;
                case 6:
                    viewAllHousekeepingRecords(conn);
                    break;
                case 7:
                    deleteUser(conn);
                    break;
                case 8:
                    viewAllUsersByRole(conn);
                    break;
                case 9:
                    deleteRoom(conn);
                    break;
                case 10:
                    deleteHotel(conn);
                    break;
                case 11:
                    addNewRoom(conn);
                    break;
                case 12:
                    updateHotel(conn);
                    break;
                case 13:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void handleHousekeepingMenu(Connection conn) throws SQLException {
        while (true) {
            System.out.println("\n=== Housekeeping Portal ===");
            System.out.println("1. View Room Status");
            System.out.println("2. Update Room Cleaning Status");
            System.out.println("3. View Cleaning Schedule");
            System.out.println("4. View Keeping Availability For Today");
            System.out.println("5. View Pending Housekeeping Tasks");
            System.out.println("6. View Completed Housekeeping Tasks");
            System.out.println("7. Update Task Status to Completed");
            System.out.println("8. Return to Main Menu");
            System.out.print("Enter your choice (1-8): ");

            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    viewRoomStatus(conn);
                    break;
                case 2:
                    // Add update room cleaning status functionality
                    System.out.println("Room cleaning status update functionality to be implemented.");
                    break;
                case 3:
                    viewScheduleIdByHousekeepingId(conn);
                    break;
                case 4:
                    viewHousekeepingAvailabilityForTodayByHousekeepingId(conn);
                    break;
                case 5:
                    viewPendingHousekeepingTasks(conn);
                    break;
                case 6:
                    viewCompletedHousekeepingTasks(conn);
                    break;
                case 7:
                    updateTaskStatusToCompleted(conn);
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addNewBooking(Connection myConnection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("addNewBooking is starting...");

        // Step 1: Enter Guest ID
        System.out.println("Enter your Guest ID:");
        int guestId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Check if the guest exists
        if (!checkGuestExists(myConnection, guestId)) {
            System.out.println("Guest with ID " + guestId + " does not exist. Please register the guest first.");
            return;
        }

        // Step 2: Enter Booking Period
        System.out.println("Please enter the start date of booking (YYYY-MM-DD):");
        String startDateInput = scanner.next();
        Date startDate = Date.valueOf(LocalDate.parse(startDateInput));

        System.out.println("Please enter the end date of booking (YYYY-MM-DD):");
        String endDateInput = scanner.next();
        Date endDate = Date.valueOf(LocalDate.parse(endDateInput));

        // Step 3: Number of Rooms
        System.out.println("How many rooms do you need?");
        int numberOfRooms = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Step 4: Loop to book each room
        for (int i = 0; i < numberOfRooms; i++) {
            // Display available rooms for the given period
            viewAllAvailableRoomsOfAllHotelsForPeriod(myConnection, startDate, endDate);

            System.out.println("Enter the Room ID you want to book:");
            int roomId = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Insert booking into the database
            String sql = "INSERT INTO public.booking (startdate, enddate, bookingstatus, room_id, guest_id) " +
                    "VALUES (?, ?, 'Pending', ?, ?);";

            PreparedStatement prep_statement = myConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            prep_statement.setDate(1, startDate);
            prep_statement.setDate(2, endDate);
            prep_statement.setInt(3, roomId);
            prep_statement.setInt(4, guestId);

            int affectedRows = prep_statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = prep_statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int bookingId = generatedKeys.getInt(1);
                    System.out.println("Booking successful! Booking ID: " + bookingId);
                    String priceQuery = "SELECT RT.price FROM public.roomtype RT, public.room R WHERE R.type_name = RT.type_Name AND R.id=?";
                    PreparedStatement priceStatement= myConnection.prepareStatement(priceQuery);
                    priceStatement.setInt(1, roomId);
                    ResultSet rs = priceStatement.executeQuery();
                    rs.next();
                    addPayment(myConnection, rs.getInt("price"), bookingId);
                }
            } else {
                System.out.println("Booking failed. Please try again.");
            }
        }
    }
    private static void addPayment(Connection myConnection, int amount, int booking_ID) throws SQLException {
        String sql3 = "INSERT INTO public.payment (booking_ID, amount, payment_Status) VALUES( ?, ?, 'Pending');";
        PreparedStatement prep_statement3 = myConnection.prepareStatement(sql3);
        prep_statement3.setInt(1, booking_ID);
        prep_statement3.setInt(2, amount);
        int rowsInserted = prep_statement3.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Payment record created successfully for booking ID: " + booking_ID);
        } else {
            System.out.println("Failed to create payment record.");
        }

    }

    // Helper Method to Check if Guest Exists
    private static boolean checkGuestExists(Connection myConnection, int guestId) throws SQLException {
        String checkGuestSql = "SELECT 1 FROM public.guest WHERE id = ?";
        PreparedStatement checkGuestStatement = myConnection.prepareStatement(checkGuestSql);
        checkGuestStatement.setInt(1, guestId);

        ResultSet rs = checkGuestStatement.executeQuery();
        return rs.next();
    }

    private static void viewAllAvailableRoomsByHotelIdForPeriod(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAllAvailableRoomsByHotelIdForPeriod");

        Scanner scanner = new Scanner(System.in);

        // Get hotel ID and date range from the user
        System.out.println("Enter hotel ID:");
        int hotelId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Enter start date (YYYY-MM-DD):");
        Date startDate = Date.valueOf(scanner.nextLine());

        System.out.println("Enter end date (YYYY-MM-DD):");
        Date endDate = Date.valueOf(scanner.nextLine());

        // SQL query to check rooms that are available by combining housekeeping and booking checks
        String sql = "SELECT r.id, r.name, r.type_name, r.hotel_id FROM public.room r WHERE r.hotel_id = ? AND NOT EXISTS (SELECT 1 FROM public.housekeeping_schedule hs WHERE hs.room_id = r.id AND hs.cleaning_status = 'Pending' AND hs.scheduledate BETWEEN ? AND ?) AND NOT EXISTS (SELECT 1 FROM public.booking b WHERE b.room_id = r.id AND b.startdate <= ? AND b.enddate >= ? AND b.bookingstatus NOT IN ('cancelled by guest', 'cancelled by admin'));";

        // Prepare the SQL statement
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        prep_statement.setInt(1, hotelId);
        prep_statement.setDate(2, startDate);
        prep_statement.setDate(3, endDate);
        prep_statement.setDate(4, endDate);
        prep_statement.setDate(5, startDate);

        // Execute the query
        ResultSet rs = prep_statement.executeQuery();

        // Print the available rooms
        boolean found = false;
        while (rs.next()) {
            found = true;
            System.out.println("Room ID: " + rs.getInt("id") +
                    ", Name: " + rs.getString("name") +
                    ", Type: " + rs.getString("type_name"));
        }

        if (!found) {
            System.out.println("No available rooms found for the given period and hotel ID.");
        }
    }

    private static void viewAllAvailableRoomsOfAllHotelsForPeriod(Connection myConnection, java.sql.Date startDateIn, java.sql.Date endDateIn) throws SQLException {
        System.out.println("Now executing viewAllAvailableRoomsOfAllHotelsForPeriod");
        Date startDate = startDateIn;
        Date endDate = endDateIn;

        // Corrected SQL query
        String sql = "SELECT r.id, r.name, r.type_name, r.hotel_id " +
                "FROM public.room r " +
                "WHERE NOT EXISTS ( " +
                "    SELECT 1 FROM public.housekeeping_schedule hs " +
                "    WHERE hs.room_id = r.id " +
                "    AND hs.cleaning_status = 'Pending' " +
                "    AND hs.scheduledate BETWEEN ? AND ? " +
                ") " +
                "AND NOT EXISTS ( " +
                "    SELECT 1 FROM public.booking b " +
                "    WHERE b.room_id = r.id " +
                "    AND b.startdate <= ? " +
                "    AND b.enddate >= ? " +
                "    AND b.bookingstatus NOT IN ('cancelled by guest', 'cancelled by admin') " +
                ");";

        // Prepare the SQL statement
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        prep_statement.setDate(1, startDate);
        prep_statement.setDate(2, endDate);
        prep_statement.setDate(3, endDate);
        prep_statement.setDate(4, startDate);

        // Execute the query
        ResultSet rs = prep_statement.executeQuery();

        // Print the available rooms
        boolean found = false;
        while (rs.next()) {
            found = true;
            System.out.println("Room ID: " + rs.getInt("id") +
                    ", Name: " + rs.getString("name") +
                    ", Type: " + rs.getString("type_name") +
                    ", Hotel ID: " + rs.getInt("hotel_id"));
        }

        if (!found) {
            System.out.println("No available rooms found for the given period.");
        }
    }

        /*
        ben kalırken odayı temizlemeye gelemezler
        tam tersi şekilde
        eğer ben kalmaya geleceksem o oda ben gelmeden booklanmışsa da temizlenmeli
        eğer booklanmamışsa sıkıntı yok

        fun1;
        housekeeping scheduledan pending olan tum odalari cek
        bu pending olan odalardan biri kendisi mi
        eger kendisiyse scheduleDateinin verilen start dateden en az bir gun once  lazim
        çünkü önce gidenin ardından sonra kendisinin önünden temizlik yapılacak toplamda 2 temizlik şart desek de gerçek hayatta tek temizlik yeterli

intersect

        func2;
        booking tablosundan tum bookingleri cek
        bu bookinglerden herhangi birinin start dateinin verilen enddateden once olmamasi ve
        bookingin end dateinin verilen startdateden sonra olmamasi sartini sagliyorsa o zaman diliminda bu oda bostur
        Eger bossa true dondur bossa false dondur
         */

    private static void viewNotBookedRoomsForGivenPeriod(Connection myConnection, Date startDate, Date endDate) throws SQLException {
        System.out.println("Executing viewNotBookedRoomsForGivenPeiod");
        String sql = "SELECT id, name, type_name, hotel_id FROM public.room where not exists (SELECT room_id FROM public.booking where startdate <= ? or enddate >= ? AND bookingstatus NOT IN ('cancelled by guest', 'cancelled by admin'))";
        PreparedStatement room_preparedStatement = myConnection.prepareStatement(sql);
        room_preparedStatement.setDate(1, endDate);
        room_preparedStatement.setDate(2, startDate);
        /*
        booking tablosundan tum bookingleri cek
        (eğer bitişinden önce başlıyorsam veya başlangıcımdan önce bitmiyorsa benimle çakışıyordur)
        bu bookinglerden herhangi birinin start dateinin verilen enddateden once olmamasi ve
        bookingin end dateinin verilen startdateden sonra olmamasi sartini sagliyorsa o zaman diliminda bu oda bostur
        */

        ResultSet rs = room_preparedStatement.executeQuery();

        while (rs.next()) {
            System.out.println("ID: " + rs.getInt("id") +
                    ", Name: " + rs.getString("name") +
                    ", Type: " + rs.getString("type_name"));
        }
    }

    private static void viewCleanRoomsForGivenPeriod(Connection myConnection, Date startDate, Date endDate) throws SQLException {
        System.out.println("Now executing viewCleanRoomsForGivenPeriod");

        String clean_sql = "SELECT id, name, type_name, hotel_id FROM public.room where not exists (SELECT room_id FROM public.housekeeping_schedule where public.housekeeping_schedule.cleaning_status =\"Pending\" and public.housekeeping_schedule.scheduledate >= ? and public.housekeeping_schedule.scheduledate <= ?);";
        PreparedStatement clean_preparedStatement = myConnection.prepareStatement(clean_sql);
        clean_preparedStatement.setDate(1, startDate);
        clean_preparedStatement.setDate(2,endDate);
        ResultSet cs = clean_preparedStatement.executeQuery();
        while (cs.next()) {
            System.out.println("ID: " + cs.getInt("id") +
                    ", Name: " + cs.getString("name") +
                    ", Type: " + cs.getString("type_name"));
        }
    }
    private static void viewAllRoomsByHotelId(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAvailableRooms");

        System.out.println("Enter hotel id:");
        Scanner scanner = new Scanner(System.in);

        int hotelId = scanner.nextInt();
        scanner.nextLine();

        PreparedStatement prep_statement = myConnection.prepareStatement("SELECT public.room.id as room_id , public.room.name, public.room.type_name FROM public.room where public.room.hotel_Id = ?;");
        prep_statement.setInt(1, hotelId);
        ResultSet rs = prep_statement.executeQuery();

        while (rs.next()) {
            System.out.println("Room Id:" + rs.getInt(" room_id") + " Name:" + rs.getString("name") + " Room_type: " + rs.getString("type_name"));
        }

    }


    //working method used by receptionist
    private static void viewAllBookingS(Connection myConnection) throws SQLException {
        //this method will be used by reception to view ALL bookings
        System.out.println("Now executing viewAllBookings from receptionist");
        //it will view all pending requests and will get input as booking id then change status to: cancelled, confirmed
        System.out.println("All bookings are listings below");
        String sql= "SELECT * FROM booking b;";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        ResultSet rs = prep_statement.executeQuery();
        while (rs.next()) {//rs.next() -> boolean
            System.out.println( "Booking ID: " + rs.getInt("id") +
                    ", Room ID: " + rs.getInt("room_id") +
                    ", Start Date: " + rs.getDate("startdate") +
                    ", End Date: " + rs.getDate("enddate") +
                    ", Status: " + rs.getString("bookingstatus") +
                    ", Guest ID: " + rs.getInt("guest_id"));
        }
        System.out.println("Showing all bookings");
    }

    //working not showing the payment status but it is fine. used by guest.
    private static void viewBooking(Connection myConnection) throws SQLException {
        //this method will be used by guest to view their booking
        System.out.println("Now executing viewBooking from guest.");
        System.out.println("Please enter the your guest id:");
        Scanner in = new Scanner(System.in);
        int guestID = in.nextInt();
        String sql = "SELECT b.id, b.room_id, b.startdate, b.enddate, b.bookingstatus FROM public.booking b WHERE b.guest_id = ?";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        prep_statement.setInt(1, guestID);
        ResultSet rs = prep_statement.executeQuery();
        boolean hasBookings = false;
        while (rs.next()) {
            hasBookings = true;
            System.out.println("Booking ID: " + rs.getInt("id") +
                    ", Room ID: " + rs.getInt("room_id") +
                    ", Start Date: " + rs.getDate("startdate") +
                    ", End Date: " + rs.getDate("enddate") +
                    ", Booking Status: " + rs.getString("bookingstatus"));
        }
        if (!hasBookings) {
            System.out.println("No bookings found.");
        }
    }

    //working method used by admin
    private static void cancelBookingAdmin(Connection myConnection) throws SQLException {
        System.out.println("Now executing cancelBooking from administrator");
        Scanner scanner = new Scanner(System.in);
        System.out.println("All not paid bookings are listings below");
        String sql= "SELECT b.id, b.startdate , b.enddate, b.room_id, p.payment_status FROM booking b, payment p \n" +
                "WHERE p.booking_id=b.id AND  p.payment_Status='Pending';";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        ResultSet rs = prep_statement.executeQuery();
        ArrayList<Integer> pendingBooking = new ArrayList();
        while (rs.next()) {//rs.next() -> boolean
            System.out.println("Booking ID: " + rs.getInt("id") +
                    ", Room ID: " + rs.getInt("room_id") +
                    ", Start Date: " + rs.getDate("startdate") +
                    ", End Date: " + rs.getDate("enddate") +
                    ", Payment Status: " + rs.getString("payment_status"));
            pendingBooking.add(rs.getInt("id"));
        }
        System.out.println("Enter booking ID to delete: ");
        int bookingId = scanner.nextInt();
        for (int i = 0; i < pendingBooking.size(); i++) {
            if(pendingBooking.get(i).equals(bookingId)) {
                String sql2= "UPDATE public.booking SET bookingstatus = 'cancelled by admin' WHERE id = ?;";
                PreparedStatement prep_statement2 = myConnection.prepareStatement(sql2);
                prep_statement2.setInt(1, bookingId);
                prep_statement2.executeUpdate();
                System.out.println("You have successfully cancelled the booking.");
                break;
            }
        }
        System.out.println("Booking you want to cancel is already paid.");
    }

    //working method used by reception
    private static void modifyBooking(Connection myConnection) throws SQLException {
        //this is used from receptionist
        System.out.println("Now executing modify from guest.");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter booking ID to update: ");
        int bookingId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.println("Enter new start date (yyyy-MM-dd): ");
        String startDate= scanner.nextLine();

        System.out.println("Enter new end date (yyyy-MM-dd): ");
        String endDate= scanner.nextLine();
        System.out.println("Enter new booking status: ");
        String bookingStatus = scanner.nextLine();

        System.out.println("Enter new room ID: ");
        int roomId = scanner.nextInt();

        String sql = "UPDATE public.booking SET startdate=?, enddate=?, bookingstatus=?, room_id=? WHERE id=?";

        PreparedStatement prepStatement = myConnection.prepareStatement(sql);
        prepStatement.setDate(1, Date.valueOf(startDate));
        prepStatement.setDate(2, Date.valueOf(endDate));
        prepStatement.setString(3, bookingStatus);
        prepStatement.setInt(4, roomId);
        prepStatement.setInt(5, bookingId);

        int rowsAffected = prepStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Booking updated successfully");
        } else {
            System.out.println("No booking found with ID: " + bookingId);
        }
    }

    //working method but return and if the guest id exists check
    private static void cancelBooking(Connection myConnection) throws SQLException {
        System.out.println("Now executing cancelBooking from guest");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your guest ID: ");
        int guestID = scanner.nextInt();
        System.out.println("All your bookings future bookings listings below");
        String sql= "SELECT b.id, b.startdate , b.enddate, b.room_id, b.bookingstatus FROM booking b WHERE b.guest_id = ? AND CURRENT_DATE <= b.startdate;";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        prep_statement.setInt(1, guestID);
        ResultSet rs = prep_statement.executeQuery();

        while (rs.next()){
            System.out.printf("Booking ID: %d, Room ID: %d, Start Date: %s, End Date: %s, Booking Status: %s%n",
                    rs.getInt("id"), rs.getInt("room_id"), rs.getDate("startdate"), rs.getDate("enddate"), rs.getString("bookingstatus"));
        }

        System.out.println("Enter booking ID to cancel: ");
        int bookingId = scanner.nextInt();
        String sql2= "UPDATE public.booking SET bookingstatus = 'cancelled by guest' WHERE id = ? AND guest_id = ?;";
        PreparedStatement prep_statement2 = myConnection.prepareStatement(sql2);
        prep_statement2.setInt(1, bookingId);
        prep_statement2.setInt(2, guestID);

        int rowsAffected = prep_statement2.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Booking with ID " + bookingId + " has been successfully cancelled.");
        }
        else {
            System.out.println("No booking found with ID " + bookingId + " to cancel.");
        }
    }

    //working method by reception
    private static void processPayment(Connection myConnection) throws SQLException {
        System.out.println("Now executing processPayment from receptionist");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the payment ID to process the payment: ");
        int paymentID = scanner.nextInt();
        String sql = "UPDATE public.payment SET payment_Status='Paid', payment_date= CURRENT_DATE WHERE id=? AND payment_Status='Pending';";
        PreparedStatement prepStatement = myConnection.prepareStatement(sql);
        prepStatement.setInt(1, paymentID);
        int rowsAffected= prepStatement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Payment with ID " + paymentID + " has been successfully processed.");
        } else {
            System.out.println("This payment is already paid or there is no existing payment with " +
                    "ID: " + paymentID + ". Please check the ID and try again.");
        }
    }

    //working method that is called by guest
    private static void viewMyPayment(Connection myConnection) throws SQLException{
        System.out.println("Executing viewMyPayment from guest");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the payment ID to view: ");
        int paymentID = scanner.nextInt();
        String sql = "SELECT p.id, p.booking_ID, p.amount,p.payment_date, p.payment_Status FROM public.payment p WHERE p.id = ?";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        prep_statement.setInt(1, paymentID);
        ResultSet rs = prep_statement.executeQuery();
        if (rs.next()) {
            System.out.println("Payment Details:");
            System.out.println("Payment ID: " + rs.getInt("id"));
            System.out.println("Booking ID: " + rs.getInt("booking_id"));
            System.out.println("Amount: $" + rs.getDouble("amount"));
            System.out.println("Payment Date: " + rs.getDate("payment_date"));
            System.out.println("Payment Status: " + rs.getString("payment_status"));
        } else {
            System.out.println("No payment found with ID: " + paymentID);
        }
    }

    //
    private static void checkIn(Connection myConnection) throws SQLException {
        System.out.println("Now executing checkIn from receptionist");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter booking ID to check in: ");
        int bookingId = scanner.nextInt();
        String sql = "UPDATE public.booking SET bookingstatus='Checked in'  WHERE id=? AND startdate=CURRENT_DATE " +
                "AND bookingstatus = 'confirmed';";
        //they can only make check in if their booking`s start date is today
        PreparedStatement prepStatement = myConnection.prepareStatement(sql);
        prepStatement.setInt(1, bookingId);
        int rowsAffected = prepStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Booking with ID " + bookingId + " has been successfully checked in.");
        }
        else {
            System.out.println("Check-in failed. Either the booking does not exist, is not confirmed, or the start date is not today.");
        }
    }

    //working method, it allows guest to pay their booking while checking out currentdate=enddate
    private static void makePayment(Connection myConnection) throws SQLException {
        System.out.println("Now executing makePayment for guest.");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the payment ID to make payment: ");
        int paymentID = scanner.nextInt();

        if (!isPaid(myConnection, paymentID)) {
            String sql = "UPDATE public.payment SET payment_status = 'Paid', payment_date = CURRENT_DATE WHERE id = ?" +
                    " AND payment_status = 'Pending' AND EXISTS ( " +
                    "    SELECT 1 FROM public.booking b   WHERE b.id = public.payment.booking_id " +
                    "    AND CURRENT_DATE = b.enddate );";

            try (PreparedStatement prepStatement = myConnection.prepareStatement(sql)) {
                prepStatement.setInt(1, paymentID);
                int rowsAffected = prepStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Payment with ID " + paymentID + " has been successfully processed.");
                } else {
                    System.out.println("Payment failed. Either the payment ID does not exist, or the conditions were not met.");
                }
            }
        }
        else {
            System.out.println("This payment has already been made.");
        }

    }

    //helper method that checks if the payment is paid
    private static boolean isPaid(Connection myConnection, int paymentID) throws SQLException {
        String checkSql = "SELECT payment_status FROM public.payment WHERE id = ?";
        try (PreparedStatement checkStatement = myConnection.prepareStatement(checkSql)) {
            checkStatement.setInt(1, paymentID);
            ResultSet rs = checkStatement.executeQuery();
            if (rs.next()) {
                String paymentStatus = rs.getString("payment_status");
                return "Paid".equalsIgnoreCase(paymentStatus);
            }
        }
        return false;
    }

    //working method that is used by admin
    private static void addNewHotel(Connection myConnection) throws SQLException {
        System.out.println("Now executing addHotel");
        String hotelName;
        String hotelAddress;

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the hotel name");
        hotelName = in.nextLine();
        //burada kendiliğinden string alırken diğer satıra geçiş yaptı
        System.out.println("Please enter the hotel address");
        hotelAddress = in.nextLine();

        System.out.println("Dynamically trying to do prepared statements ");
        PreparedStatement prep_statement = myConnection.prepareStatement("INSERT INTO public.hotel (address, name) VALUES( ?, ?);");
        prep_statement.setString(1, hotelAddress);
        System.out.println("setted hotel address");
        prep_statement.setString(2, hotelName);
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

    private static void assignHousekeepingTask(Connection myConnection) throws SQLException {
        System.out.println("Now executing assignHousekeepingTask");
        Scanner in = new Scanner(System.in);

        System.out.println("Enter the housekeeping schedule ID");
        int scheduleId = in.nextInt();
        in.nextLine();

        // Check if the room and housekeeping staff belong to the same hotel
        boolean isValidID = false;
        int roomId = 0;
        int staffId = 0;
        while (!isValidID) {

            roomId = checkRoomById(myConnection);

            staffId = checkHousekeepingStaffById(myConnection);

            // Get the hotel ID for the housekeeping staff
            PreparedStatement h_prep_statement = myConnection.prepareStatement("SELECT public.employee.hotel_id FROM public.employee JOIN public.housekeeping_staff ON public.housekeeping_staff.employee_id = public.employee.id WHERE public.housekeeping_staff.id = ?;");
            h_prep_statement.setInt(1, staffId);
            ResultSet h_rs = h_prep_statement.executeQuery();
            h_rs.next();
            int housekeeper_hotel_id = h_rs.getInt("hotel_id");

            // Get the hotel ID for the room
            PreparedStatement r_prep_statement = myConnection.prepareStatement("SELECT hotel_id FROM public.room WHERE public.room.id = ?;");
            r_prep_statement.setInt(1, roomId);
            ResultSet r_rs = r_prep_statement.executeQuery();
            r_rs.next();
            int room_hotel_id = r_rs.getInt("hotel_id");

            if (room_hotel_id == housekeeper_hotel_id) {
                isValidID = true;
                System.out.println("Valid IDs. Both the room and the housekeeping staff belong to the same hotel.");
            } else {
                System.out.println("Invalid IDs. The room belongs to hotel ID " + room_hotel_id +
                        " while the housekeeper belongs to hotel ID " + housekeeper_hotel_id + ".");
                System.out.println("Please again enter IDs for the same hotel.");
            }
        }

        System.out.println("Enter the scheduled date (yyyy-mm-dd):");
        String scheduleDate = in.nextLine();

        int receptionistId = checkReceptionist(myConnection);

        // Check if the schedule date is valid against existing bookings
        String bookingCheckSql = "SELECT startdate, enddate FROM public.booking WHERE room_id = ?;";
        PreparedStatement bookingPrep = myConnection.prepareStatement(bookingCheckSql);
        bookingPrep.setInt(1, roomId);
        ResultSet rs = bookingPrep.executeQuery();

        boolean isDateValid = true;
        LocalDate scheduledLocalDate = LocalDate.parse(scheduleDate);
        while (rs.next()) {
            LocalDate bookingStartDate = rs.getDate("startdate").toLocalDate();
            LocalDate bookingEndDate = rs.getDate("enddate").toLocalDate();
            if ((scheduledLocalDate.isAfter(bookingStartDate) && scheduledLocalDate.isBefore(bookingEndDate)) ||
                    scheduledLocalDate.isEqual(bookingStartDate) || scheduledLocalDate.isEqual(bookingEndDate)) {
                isDateValid = false;
                break;
            }
        }

        if (!isDateValid) {
            System.out.println("Invalid schedule date. The room is booked between specified dates. Please enter a different date.");
            return;
        }

        String sql = "INSERT INTO public.housekeeping_schedule (id, room_id, housekeeping_staff_id, scheduledate, cleaning_status, receptionist_id) VALUES (?, ?, ?, ?, 'Pending', ?);";

        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        prep_statement.setInt(1, scheduleId);
        prep_statement.setInt(2, roomId);
        prep_statement.setInt(3, staffId);
        prep_statement.setString(4, scheduleDate);
        prep_statement.setInt(5, receptionistId);

        prep_statement.executeUpdate();
        System.out.println("Task assigned successfully to housekeeping staff.");
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

    private static int checkHousekeepingStaffById(Connection myConnection) throws SQLException {
        System.out.println("Now executing checkHousekeepingScheduleById");
        Scanner in = new Scanner(System.in);
        int scheduleId = 0;
        boolean validId = false;

        String Sql = "SELECT id, employee_id FROM public.housekeeping_staff WHERE id = ?;";

        while (!validId) {
            System.out.println("Enter a valid housekeeping staff ID:");

            try {
                scheduleId = in.nextInt();
                in.nextLine();

                try (PreparedStatement schedule_prep_statement = myConnection.prepareStatement(Sql)) {
                    schedule_prep_statement.setInt(1, scheduleId);

                    try (ResultSet rs = schedule_prep_statement.executeQuery()) {
                        if (rs.next()) {
                            System.out.println("You are lucky. The housekeeping staff with the given ID exists.");
                            validId = true;
                        } else {
                            System.out.println("There is no housekeeping staff with this ID. Please try again.");
                        }
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric housekeeping staff ID.");
                in.nextLine();
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
                return 0;
            }
        }

        return scheduleId;
    }

    private static int checkReceptionist(Connection myConnection) throws SQLException {
        System.out.println("Now executing checkReceptionist");
        Scanner in = new Scanner(System.in);
        int receptionistId = 0;
        boolean validReceptionistId = false;

        String receptionistSql = "SELECT id, employee_id FROM public.receptionist WHERE id = ?;";

        while (!validReceptionistId) {
            System.out.println("Enter a valid receptionist ID:");

            try {
                receptionistId = in.nextInt();
                in.nextLine(); // Consume the newline

                try (PreparedStatement receptionist_prep_statement = myConnection.prepareStatement(receptionistSql)) {
                    receptionist_prep_statement.setInt(1, receptionistId);

                    try (ResultSet rs = receptionist_prep_statement.executeQuery()) {
                        if (rs.next()) {
                            System.out.println("You are lucky. The receptionist with the given ID exists.");
                            validReceptionistId = true;
                        } else {
                            System.out.println("There is no receptionist with this ID. Please try again.");
                        }
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric receptionist ID.");
                in.nextLine(); // Clear the invalid input
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
                return 0; // Exit--receptionistId 0 can't be valid
            }
        }

        return receptionistId;
    }

    private static void viewScheduleIdByHousekeepingId(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewScheduleIdByHousekeepingId");
        Scanner in = new Scanner(System.in);

        System.out.println("Enter your Housekeeping ID:");
        int housekeepingId = in.nextInt();
        in.nextLine();

        PreparedStatement statement = myConnection.prepareStatement("SELECT sh.id FROM public.housekeeping_schedule sh join public.housekeeping_staff hs on hs.id = sh.housekeeping_staff_id where hs.id = ?;");
        statement.setInt(1, housekeepingId);
        ResultSet rs = statement.executeQuery();


        if (rs.next()) {
            System.out.println(rs.getInt("id"));
        }

        else{
            System.out.println("There is no housekeeping schedule with that housekeeper ID.");
        }

    }

    private static void viewAllEmployeeswithTheirRoles(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAllEmployeeswithTheirRoles");
        String sql = "SELECT e.id AS employee_id, u.name, u.surname, u.type AS role FROM public.employee e JOIN public.user u ON e.user_id = u.id;";

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

    private static void viewAllHousekeepersRecordsAndAvailability(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAllHousekeepersRecordsAndAvailability");
        String sql = "SELECT hs.id AS housekeeper_id, e.id AS employee_id, u.name, u.surname, CASE WHEN EXISTS ( SELECT 1 FROM public.housekeeping_schedule hsched WHERE hsched.housekeeping_staff_id = hs.id AND hsched.cleaning_status = 'Pending') THEN 'Not Available' ELSE 'Available' END AS availability FROM public.housekeeping_staff hs JOIN public.employee e ON hs.employee_id = e.id JOIN public.user u ON e.user_id = u.id;";

        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        ResultSet rs = prep_statement.executeQuery();

        while (rs.next()) {
            System.out.println(
                    "Housekeeper ID:" + rs.getInt("housekeeper_id") + " | " +
                            "Employee ID: " +  rs.getInt("employee_id") + " | " +
                            "Name:" + rs.getString("name") + " | " +
                            "Surname:" + rs.getString("surname") + " | " +
                            "Availability:" + rs.getString("availability")
            );
        }
    }

    private static void viewHousekeepingAvailabilityForTodayByHousekeepingId(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAllHousekeepingAvailability");

        Scanner in = new Scanner(System.in);
        System.out.println("Enter housekeeper ID:");
        int housekeeperId = in.nextInt();
        in.nextLine();

        PreparedStatement prep_statement = myConnection.prepareStatement("select sh.id, sh.housekeeping_staff_id, sh.cleaning_status, sh.scheduledate from public.housekeeping_schedule sh join public.housekeeping_staff hs on hs.id = sh.housekeeping_staff_id where hs.id = ?;");
        prep_statement.setInt(1,housekeeperId);
        ResultSet rs = prep_statement.executeQuery();
        String state = "";
        String date = "";

        if (rs.next()) {
            state = rs.getString("cleaning_status");
            date = rs.getString("scheduledate");
        }

        java.sql.Date current = java.sql.Date.valueOf(LocalDate.now());

        String availability = "Available";
        if (state.equals("Pending") && current.equals(date) ) {
            availability = "Not Available for today";
        }

        System.out.println(availability);
    }

    private static void viewAllHousekeepingRecords(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAllHousekeepingRecords");
        String sql = "SELECT  public.housekeeping_staff.id AS housekeeper_id, public.employee.id AS employee_id, public.`user`.name, public.`user`.surname,  public.housekeeping_staff.id AS housekeeping_staff_id FROM public.housekeeping_staff JOIN public.employee ON public.housekeeping_staff.employee_id =  public.employee.id JOIN public.`user` ON  public.employee.user_id = public.`user`.id ;";

        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        ResultSet rs = prep_statement.executeQuery();

        while (rs.next()) {
            System.out.println("Housekeeper ID:" + rs.getInt("housekeeper_id") + " | " + "Employee ID:" + rs.getInt("employee_id") + " | " + "Name:" + rs.getString("name") + " | " + "Surname:" + rs.getString("surname"));
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

//                case "A":
//                    type = "admin";
//                    choice=3;
//                    break;

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

    private static void confirmBookingReceptionist(Connection myConnection) throws SQLException {
        System.out.println("Now executing confirmnewbooking from receptionist");
        //it will view all pending requests and will get input as booking id then change status to: cancelled, confirmed
        System.out.println("All pending bookings are listings below");
        String sql = "SELECT b.id, b.startdate , b.enddate, b.room_id FROM booking b WHERE b.bookingstatus = 'Pending';";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        ResultSet rs = prep_statement.executeQuery();
        while (rs.next()) {//rs.next() -> boolean
            System.out.println(rs.getInt("id") + " " + rs.getInt("room_id") + " " + rs.getDate("startdate") + " " + rs.getDate("enddate"));
        }
        System.out.println("Please enter the id of booking you want to confirm");
        Scanner in = new Scanner(System.in);
        int booking_id = in.nextInt();
        String sql2 = "UPDATE public.booking SET bookingstatus = 'Confirmed' WHERE id = ? AND bookingstatus = 'Pending';";
        PreparedStatement prep_statement2 = myConnection.prepareStatement(sql2);
        prep_statement2.setInt(1, booking_id);
        System.out.println("setted booking id");
        prep_statement2.executeUpdate();
        System.out.println("Executed Update Statement successfully");
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

    //you cannot create an Administrator account from the end-points
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

                //case 3:
                // addNewAdmin(myConnection, id);
                default:
                    System.out.println("Invalid choice. No specific role assigned.");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting employee: " + e.getMessage());
        }
    }

    private static void addNewReceptionist(Connection myConnection, int receptionist_employeeId) throws SQLException {
        System.out.println("Now executing addNewReceptionist.");
        Scanner in = new Scanner(System.in);

        System.out.println("Enter ID");
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

    private static void deleteUser(Connection myConnection) throws SQLException {
        System.out.println("Now executing deleteUser");
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter user ID");
        int userID= in.nextInt();
        in.nextLine();
        PreparedStatement prep_statement2 = myConnection.prepareStatement("SELECT type FROM public.user WHERE id= ?;");
        prep_statement2.setInt(1, userID);
        ResultSet rs= prep_statement2.executeQuery();

        if (rs.next()) {
            if (rs.getString("type").equals("admin")) {
                System.out.println("The user you want to delete is an admin. Admin users cannot be deleted.");
                return;
            }
            System.out.println("WARNING: Deleting this user will also delete all related information (e.g., bookings, payments).");
            System.out.println("Are you sure you want to proceed? (Y/N):");
            String confirmation = in.nextLine().trim().toUpperCase();

            if (confirmation.equals("Y") || confirmation.equals("y")) {
                PreparedStatement prep_statement = myConnection.prepareStatement("DELETE FROM public.user WHERE id = ?;");
                prep_statement.setInt(1, userID);
                int rowsAffected = prep_statement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("User with ID " + userID + " and all related information have been successfully deleted.");
                } else {
                    System.out.println("Failed to delete the user. Please check the ID and try again.");
                }
            }
            else {
                System.out.println("User deletion has been canceled.");
            }
        }
        else {
            System.out.println("User with the specified ID does not exist.");
        }
    }

    private static void viewAllUsersByRole(Connection myConnection) throws SQLException {
        //View All Users with their role'dan ilham alınmıştır
        //role'ünü seç, o role'deki tüm userları listeler
        System.out.println("Now executing viewAllUsersByRole");
        System.out.println("For housekeeper enter:H/h");
        System.out.println("For receptionist enter:R/r");
        System.out.println("For guest enter: G/g");
        System.out.println("For admin enter:A/a");
        Scanner in = new Scanner(System.in);

        String type = "";

        while (type.isEmpty()) {
            System.out.println("Please enter the type in this format");
            type = in.nextLine().toUpperCase();

            switch (type) {
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
                    type = "";
                    break;
            }
        }
    }

    private static void viewAvaibleRooms(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAvaibleRooms");
        Date date = Date.valueOf(LocalDate.now());
        String sql = "SELECT R.id, R.name, R.room_type, R.hotel_id  FROM public.room R " +
                "WHERE R.id NOT IN ( SELECT B.room_id FROM public.booking B WHERE CURRENT_DATE BETWEEN B.startdate AND B.enddate  )";
        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        ResultSet rs = prep_statement.executeQuery();
        boolean hasRooms = false;
        while (rs.next()) {
            hasRooms = true;
            System.out.println(
                    "Room ID: " + rs.getInt("id") +
                            ", Name: " + rs.getString("name") +
                            ", Room Type: " + rs.getString("room_type") +
                            ", Hotel ID: " + rs.getInt("hotel_id")
            );
        }
        if (!hasRooms) {
            System.out.println("No available rooms at the moment.");
        }
    }

    private static void addNewRoom(Connection myConnection) throws SQLException {
        Scanner in = new Scanner(System.in);
        System.out.println("Now executing addNewRoom");

        System.out.println("Please enter the hotel ID");
        int hotelId = in.nextInt();
        in.nextLine();
        String queryHotelSql = "SELECT * FROM hotel WHERE id = ?";
        PreparedStatement prep_statement = myConnection.prepareStatement(queryHotelSql);
        prep_statement.setInt(1, hotelId);
        ResultSet rsHotel = prep_statement.executeQuery();

        if (!rsHotel.next()) {
            System.out.println("Hotel with ID " + hotelId + " does not exist. Cannot add room.");
            return;
        }
        System.out.println("Please enter the room type");
        String type_Name = in.nextLine();

        // Check if room type exists before proceeding
        String queryRoomTypeSql = "SELECT * FROM roomtype WHERE type_Name = ?";
        PreparedStatement prep_statement2 = myConnection.prepareStatement(queryRoomTypeSql);
        prep_statement2.setString(1, type_Name);
        ResultSet rs = prep_statement2.executeQuery();

        if(rs.next()){// Room type exists
            System.out.println("Please enter the room ID");
            int roomId = in.nextInt();
            in.nextLine();

            System.out.println("Please enter the room's name");
            String roomName = in.nextLine();

            // Room add function
            prep_statement2 = myConnection.prepareStatement("INSERT INTO public.room (id, name, type_name, hotel_id) VALUES( ?, ?, ?, ?);");
            prep_statement2.setInt(1, roomId);
            prep_statement2.setString(2, roomName);
            prep_statement2.setString(3, type_Name);
            prep_statement2.setInt(4, hotelId);

            prep_statement2.executeUpdate();
            System.out.println("addNewRoom executed");
        } else {
            System.out.println("Room type does not exist. Cannot add room.");
        }
    }

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

    private static void viewCompletedHousekeepingTasks(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewCompletedHousekeepingTasks");
        String sql = "SELECT * FROM public.housekeeping_schedule WHERE cleaning_status = 'Completed';";
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

    private static void viewRoomStatus(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewRoomStatus.");

        String sql = "SELECT r.id AS room_id, r.name AS room_name, " +
                "CASE " +
                "    WHEN EXISTS ( SELECT 1   FROM public.booking b " +
                "        WHERE b.room_id = r.id AND CURRENT_DATE BETWEEN b.startdate AND b.enddate " +
                "    ) THEN 'Occupied'   WHEN r.id IN ( SELECT b.room_id    FROM public.booking b " +
                "        WHERE b.enddate < CURRENT_DATE AND NOT EXISTS ( " +
                "            SELECT 1 FROM public.housekeeping_schedule h WHERE h.room_id = r.id AND h.scheduledate >= b.enddate " +
                "        THEN 'Waiting to Clean'    ELSE 'Cleaned' " +
                "END AS room_status FROM public.room r " +
                "LEFT JOIN public.housekeeping_schedule h ON r.id = h.room_id " +
                "GROUP BY r.id, r.name;";

        PreparedStatement prepStatement = myConnection.prepareStatement(sql);
        ResultSet rs = prepStatement.executeQuery();

        System.out.printf("%-10s %-20s %-20s%n", "Room ID", "Room Name", "Room Status");
        System.out.println("----------------------------------------------------------");
        while (rs.next()) {
            System.out.printf("%-10d %-20s %-20s%n",
                    rs.getInt("room_id"),
                    rs.getString("room_name"),
                    rs.getString("room_status"));
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
        System.out.println("delete room set");;
        int rowsAffected = prep_statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Room with ID " + roomId + " was deleted successfully.");
        } else {
            System.out.println("No room found with ID " + roomId);
        }
    }

    private static void deleteBooking(Connection myConnection) throws SQLException {
        //It will be using be receptionist
        System.out.println("Now executing deleteBooking from receptionist");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter booking ID to delete: ");
        int bookingId = scanner.nextInt();

        //first deletes related payment
        String deletePayment = "DELETE FROM public.payment WHERE booking_id = ?";
        PreparedStatement paymentStatement = myConnection.prepareStatement(deletePayment);
        paymentStatement.setInt(1, bookingId);
        int paymentsDeleted = paymentStatement.executeUpdate();

        String sql = "DELETE FROM public.booking WHERE id = ?";
        PreparedStatement prepStatement = myConnection.prepareStatement(sql);
        prepStatement.setInt(1, bookingId);

        int rowsAffected = prepStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Booking deleted successfully. " + paymentsDeleted + " associated payment(s) were also deleted.");
        } else {
            System.out.println("No booking found with ID: " + bookingId);
        }


    }

    private static void checkOut(Connection myConnection) throws SQLException {
        System.out.println("Now executing checkOut from receptionist");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter booking ID to check out: ");
        int bookingId = scanner.nextInt();
        String sql1 = "SELECT p.id FROM payment p WHERE p.booking_id= ? ";
        PreparedStatement prep_statement2 = myConnection.prepareStatement(sql1);
        prep_statement2.setInt(1, bookingId);
        ResultSet rs = prep_statement2.executeQuery();
        rs.next();
        if(!isPaid(myConnection,rs.getInt("id"))){
            System.out.println("Check out failed, Guest need to make payment from Guest menu from `make payment`");
            return;
        }

        String sql = "UPDATE public.booking SET bookingstatus='Checked out'  WHERE id=? AND enddate =CURRENT_DATE " +
                "AND bookingstatus = 'Checked in';";
        //they can only make check out if their booking`s end date is today
        PreparedStatement prepStatement = myConnection.prepareStatement(sql);
        prepStatement.setInt(1, bookingId);
        int rowsAffected = prepStatement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Booking with ID " + bookingId + " has been successfully checked out.");
        }
        else {
            System.out.println("Check out failed. Either the booking does not exist, is not checked in, or the end date is not today.");

        }
    }

    private static void viewHousekeepingScheduleByHousekeepingScheduleId(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewMyCleaningSchedule");
        Scanner in = new Scanner(System.in);

        System.out.println("Enter your Housekeeper ID:");
        int housekeepingStaffId = in.nextInt();
        in.nextLine();

        String sql = "SELECT hs.id, hs.room_id, hs.scheduledate, hs.cleaning_status, r.name AS room_name FROM public.housekeeping_schedule hs JOIN public.room r ON hs.room_id = r.id WHERE hs.housekeeping_staff_id = ?;";

        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        prep_statement.setInt(1, housekeepingStaffId);
        ResultSet rs = prep_statement.executeQuery();

        while (rs.next()) {
            System.out.println( "Housekeeping Schedule ID:" + rs.getInt("id") + " | " +
                    "Room ID:" + rs.getInt("room_id") + " | " +
                    " Room Name:" + rs.getString("room_name") + " | " +
                    "Schedule Date:" + rs.getDate("scheduledate") + " | " +
                    "Cleaning Status:" + rs.getString("cleaning_status")
            );
        }
    }

    private static void viewCleaningScheduleByHousekeepingId(Connection myConnection) throws SQLException {
        //burada sadece Pendingse listeliyoruz çünkü sadece bekleyen işlerini görmesinin kolaylık sağlayacağını düşündük
        System.out.println("Now executing viewCleaningScheduleByHousekeepingId");
        Scanner in = new Scanner(System.in);

        System.out.println("Enter your Housekeeping ID:");
        int housekeepingId = in.nextInt();
        in.nextLine();

        String sql = "SELECT hs.id, hs.room_id, hs.scheduledate, hs.cleaning_status, r.name AS room_name FROM public.housekeeping_schedule hs JOIN public.room r ON hs.room_id = r.id WHERE hs.housekeeping_staff_id = ? AND hs.cleaning_status = 'Pending' ORDER BY hs.scheduledate;";

        PreparedStatement prep_statement = myConnection.prepareStatement(sql);
        prep_statement.setInt(1, housekeepingId);
        ResultSet rs = prep_statement.executeQuery();

        while (rs.next()) {
            System.out.println( "Schedule ID:"+ rs.getInt("id") + " | " +
                    "Room ID:"+rs.getInt("room_id") + " | " +
                    "Room Name:"+rs.getString("room_name") + " | " +
                    "Schedule Date:"+rs.getDate("scheduledate") + " | " +
                    "Cleaning Status:"+rs.getString("cleaning_status")
            );
        }
    }

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
    private static void addNewRevenueReport(Connection myConnection) throws SQLException {
        System.out.println("Now executing addNewRevenueReport(generateRevenueReport)");
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


    private static void viewAllGuests(Connection myConnection) throws SQLException {
        System.out.println("Now executing viewAllGuests");
        PreparedStatement prep_statement = myConnection.prepareStatement("SELECT id, name, surname, phone_num, user_id FROM public.guest;");
        ResultSet rs=prep_statement.executeQuery();
        while (rs.next()){
            System.out.println(rs.getInt("id") + " " + rs.getString("name")+ " " + rs.getString("surname")+" "+ rs.getInt("phone_num"));
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