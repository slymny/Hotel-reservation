package hotelApplication;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class AdminMenu {
    private static final AdminResource adminResource = AdminResource.adminResource;
    private static final HotelResource hotelResource = HotelResource.getInstance();

    public static void admin() {
        String userInput = "";
        Scanner scanner = new Scanner(System.in);
        try {
            while (!userInput.equals("5")) {
                System.out.println("-------Admin Menu-------");
                System.out.println("1. See all Customers");
                System.out.println("2. See all Rooms");
                System.out.println("3. See all Reservations");
                System.out.println("4. Add a Room");
                System.out.println("5. Back to Main Menu");
                System.out.println("6. Populate test data");
                System.out.println("Please select from the menu!");
                userInput = scanner.nextLine();
                switch (userInput) {
                    case "1" -> getAllCustomers();
                    case "2" -> getAllRooms();
                    case "3" -> getAllReservations();
                    case "4" -> addRoom(scanner);
                    case "5" -> MainMenu.menu();
                    case "6" -> populateTestData();
                    default -> System.out.println("Unknown input!");
                }
            }
        } catch (IllegalArgumentException ex) {
            ex.getLocalizedMessage();
        }
    }

    private static void populateTestData() {
        try {
            hotelResource.createACustomer("michelScott@email.com", "Michel", "Scott");
            hotelResource.createACustomer("dwightSchrute@email.com", "Dwight", "Schrute");
            IRoom room = new Room("F101", 100.5, RoomType.DOUBLE);
            IRoom room2 = new Room("F102", 80.0, RoomType.SINGLE);
            adminResource.addRoom(room);
            adminResource.addRoom(room2);
            hotelResource.bookARoom("michelScott@email.com", room, new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2023"), new SimpleDateFormat("dd/MM/yyyy").parse("05/01/2023"));
            hotelResource.bookARoom("dwightSchrute@email.com", room2, new SimpleDateFormat("dd/MM/yyyy").parse("01/10/2023"), new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2023"));
            System.out.println("Test data is populated!");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void getAllReservations() {
        System.out.println("Reservations: ");
        adminResource.displayAllReservations();
    }

    private static void getAllRooms() {
        Collection<IRoom> allRooms = adminResource.getAllRooms();
        System.out.println("Rooms: ");
        if (allRooms.isEmpty()) {
            System.out.println("There is no room yet!");
        }
        for (IRoom room : allRooms) {
            System.out.println(room.toString());
        }
    }

    private static void getAllCustomers() {
        Collection<Customer> allCustomers = adminResource.getAllCustomers();
        System.out.println("Customers: ");
        if (allCustomers.isEmpty()) {
            System.out.println("There is no customer yet!");
        }
        for (Customer customer : allCustomers) {
            System.out.println(customer.toString());
        }
    }

    private static Collection<IRoom> addRoom(Scanner scanner) {
        try {
            Collection<IRoom> rooms = new ArrayList<>();
            System.out.println("Add a room.");
            System.out.println("Please enter the room number:");
            String roomNumber = scanner.nextLine();
            System.out.println("Please enter the room price:");
            Double price = Double.valueOf(scanner.nextLine());
            System.out.println("Please enter the room type: \n Single or Double");
            RoomType type = RoomType.valueOf(scanner.nextLine().toUpperCase());
            Room room = new Room(roomNumber, price, type);
            System.out.println("The room created successfully.");
            adminResource.addRoom(room);
            return rooms;
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: Invalid input. Please try again!");
            System.out.println(ex.getLocalizedMessage());
            return addRoom(scanner);
        }
    }
}
