package hotelApplication;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getInstance();

    public static void menu() {
        String userInput = "";
        Scanner scanner = new Scanner(System.in);
        try {
            while (!userInput.equals("5")) {
                System.out.println("-------Main Menu-------");
                System.out.println("1. Find and reserve a room");
                System.out.println("2. See my reservations");
                System.out.println("3. Create an account");
                System.out.println("4. Admin");
                System.out.println("5. Exit");
                System.out.println("-----------------------");
                System.out.println("Please select a number for the menu option");
                userInput = scanner.nextLine();
                switch (userInput) {
                    case "1" -> {
                        System.out.println("Do you have an account? y/n");
                        String customerRespond = scanner.nextLine();
                        String emailInput;
                        if (customerRespond.equals("n")) {
                            System.out.println("Please first create an account!");
                            emailInput = createCustomerMenu(scanner);
                            if (emailInput == null) break;
                        } else if(!customerRespond.equals("y")) {
                            System.out.println("Error: Invalid input! Please try again.");
                            break;
                        } else {
                            System.out.println("Please enter your email address:");
                            emailInput = scanner.nextLine();
                            String customerEmail = customerCheck(emailInput);
                            if (customerEmail == null) break;
                        }
                        System.out.println("Please Enter A Check In Date in the dd/mm/yyyy format:");
                        Date dateIn = getParse(scanner);
                        if (dateIn == null) break;
                        if (dateIn.before(new Date())) {
                            System.out.println("Error: Date cannot be before today! Please try again.");
                            break;
                        }
                        System.out.println("Please Enter A Check Out Date in the dd/mm/yyyy format:");
                        Date dateOut = getParse(scanner);
                        if (dateOut == null) break;
                        if (dateIn.after(dateOut) || dateIn.equals(dateOut)) {
                            System.out.println("Error: Check out date cannot be before or equals to check in date! Please try again.");
                            break;
                        }
                        reserveARoom(scanner, emailInput, dateIn, dateOut);
                    }
                    case "2" -> getCustomerReservations(scanner);
                    case "3" -> createCustomerMenu(scanner);
                    case "4" -> AdminMenu.admin();
                    case "5" -> System.out.println("Exit");
                    default -> System.out.println("Error: Unknown input!");
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    private static void reserveARoom(Scanner scanner, String customerEmail, Date dateIn, Date dateOut) {
        try {
            Collection<IRoom> availableRooms = hotelResource.findARoom(dateIn, dateOut);
            if (availableRooms.isEmpty()) {
                System.out.println("No room found in the given date!");
                System.out.println("Do you want to check next days? (y/n)");
                String customerRespond = scanner.nextLine();
                if ( customerRespond.equals("y")) {
                    System.out.println("How many days later you want to check?");
                    String addDaysInput = scanner.nextLine();
                    int addDays = parseInt(addDaysInput);
                    Collection<IRoom> recommendedRooms = hotelResource.recommendRoom(dateIn, dateOut, addDays);
                    if (!recommendedRooms.isEmpty()) {
                        System.out.println("Recommended room for the next " + addDays +  " days: ");
                        Date recommendedCheckIn = hotelResource.addDays(dateIn, addDays);
                        Date recommendedCheckOut = hotelResource.addDays(dateOut, addDays);
                        createAReservation(scanner, customerEmail, recommendedCheckIn, recommendedCheckOut, recommendedRooms);
                    } else {
                        System.out.println("No room found!");
                    }
                } else if (!customerRespond.equals("n"))
                    throw new IllegalArgumentException("Error: Invalid input! Please try again.");
            } else {
                System.out.println("Available rooms:");
                createAReservation(scanner, customerEmail, dateIn, dateOut, availableRooms);
            }
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
    }

    private static void createAReservation(Scanner scanner, String customerEmail, Date dateIn, Date dateOut, Collection<IRoom> rooms) {
        for (IRoom room : rooms) {
            System.out.println(room);
        }
        System.out.println("Please enter the room number which you want to reserve:");
        String roomNumber = scanner.nextLine();
        IRoom room = hotelResource.getRoom(roomNumber);
        if(!rooms.contains(room) || room == null) throw new IllegalArgumentException("Error: Invalid room number! Please try again.");
        Reservation newReservation = hotelResource.bookARoom(customerEmail, room, dateIn, dateOut);
        System.out.println("Reservation successfully made: ");
        System.out.println(newReservation.toString());
    }

    private static Date getParse(Scanner scanner) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(scanner.nextLine());
        } catch (ParseException ex) {
            System.out.println("Error: Invalid date. Please try again.");
        }
        return null;
    }

    private static String customerCheck(String customerInput) {
        try {
            Customer customer = hotelResource.getCustomer(customerInput);
            return customer.getEmail();
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }

    private static void getCustomerReservations(Scanner scanner) {
        System.out.println("Please Enter Your Email:");
        String customerInput = scanner.nextLine();
        String customerEmail = customerCheck(customerInput);
        if (customerEmail != null) {
            Collection<Reservation> customerReservations = hotelResource.getCustomersReservations(customerEmail);
            if (customerReservations.isEmpty()) {
                System.out.println("You have no reservation yet!");
            }
            for (Reservation customerReservation : customerReservations) {
                System.out.println(customerReservation);
            }
        }
    }

    private static String createCustomerMenu(Scanner scanner) {
        System.out.println("Create an Account");
        System.out.println("Please enter your first name:");
        String firstName = scanner.nextLine();
        System.out.println("Please enter your last name:");
        String lastName = scanner.nextLine();
        return createCustomer(scanner, firstName, lastName);
    }

    private static String createCustomer(Scanner scanner, String firstName, String lastName) {
        try {
            System.out.println("Please enter your email:");
            String email = scanner.nextLine();
            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully.");
            return email;
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return null;
    }
}
