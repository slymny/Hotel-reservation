import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.hotelResource;

    public static void menu() {
        String userInput = "";
        Scanner scanner = new Scanner(System.in);
        try {
            while (!userInput.equals("5")) {
                System.out.println("-------------");
                System.out.println("1. Find and reserve a room");
                System.out.println("2. See my reservations");
                System.out.println("3. Create an account");
                System.out.println("4. Admin");
                System.out.println("5. Exit");
                System.out.println("Please select from menu!");
                userInput = scanner.nextLine();
                switch (userInput) {
                    case "1" -> reserveARoom(scanner);
                    case "2" -> getCustomerReservations(scanner);
                    case "3" -> createCustomer();
                    case "4" -> AdminMenu.admin();
                    case "5" -> System.out.println("Exit");
                    default -> System.out.println("Unknown input!");
                }
            }
        } catch (IllegalArgumentException e) {
            e.getLocalizedMessage();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static void reserveARoom(Scanner scanner) throws ParseException {
        System.out.println("Do you have an account? y/n");
        String customerRespond = scanner.nextLine();
        if (customerRespond.equals("n")) {
            System.out.println("Please create an account!");
            createCustomer();
        }
        System.out.println("Please enter your email address:");
        Customer customer = customerCheck(scanner);
        String customerEmail = customer.getEmail();
        System.out.println("Please Enter A Check in Date in the dd/mm/yyyy format:");
        Date dateIn = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
        System.out.println("Please Enter A Check Out Date in the mm/dd/yyyy format:");
        Date dateOut = new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
        Collection<IRoom> availableRooms = hotelResource.findARoom(dateIn, dateOut);
        if (availableRooms.isEmpty()) {
            Collection<IRoom> recommendedRooms = hotelResource.recommendRoom(dateIn, dateOut);
            if (!recommendedRooms.isEmpty()) {
                Date recommendedCheckIn = hotelResource.addDays(dateIn, 7);
                Date recommendedCheckOut = hotelResource.addDays(dateOut, 7);
                createAReservation(scanner, customerEmail, recommendedCheckIn, recommendedCheckOut, recommendedRooms);
            } else {
                System.out.println("No room found!");
            }
        } else {
            System.out.println("Available rooms:");
            createAReservation(scanner, customerEmail, dateIn, dateOut, availableRooms);
        }
    }

    private static Customer customerCheck(Scanner scanner) {
        try {
            String customerEmail = scanner.nextLine();
            return hotelResource.getCustomer(customerEmail);
        } catch (IllegalArgumentException ex) {
            System.out.println("There is no account with given email! Please try again.");
            customerCheck(scanner);
        }
        return null;
    }

    private static void getCustomerReservations(Scanner scanner) {
        System.out.println("Please Enter Your Email:");
        Customer customer = customerCheck(scanner);
        Collection<Reservation> customerReservations = hotelResource.getCustomersReservations(customer.getEmail());
        if (customerReservations.isEmpty()) {
            System.out.println("You have no reservation yet!");
        }
        for (Reservation customerReservation : customerReservations) {
            System.out.println(customerReservation);
        }
    }

    private static void createCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Create an Account");
        System.out.println("Please enter your first name:");
        String firstName = scanner.nextLine();
        System.out.println("Please enter your last name:");
        String lastName = scanner.nextLine();
        try {
            System.out.println("Please enter your email:");
            String email = scanner.nextLine();
            HotelResource.hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid input!");
        }
    }

    private static void createAReservation(Scanner scanner, String customerEmail, Date dateIn, Date dateOut, Collection<IRoom> rooms) {
        for (IRoom room : rooms) {
            System.out.println(room);
        }
        System.out.println("Please enter the room number which you want to reserve:");
        String roomNumber = scanner.nextLine();
        IRoom room = hotelResource.getRoom(roomNumber);
        Reservation newReservation = hotelResource.bookARoom(customerEmail, room, dateIn, dateOut);
        System.out.println("Reservation successfully made: ");
        System.out.println(newReservation.toString());
    }
}
