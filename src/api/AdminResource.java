package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;

public class AdminResource {
    public static final AdminResource adminResource = new AdminResource();
    private static final ReservationService reservationService = ReservationService.reservationService;
    private static final CustomerService customerService = CustomerService.customerService;

    public void addRoom(IRoom room) {
            reservationService.addRoom(room);
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
}
