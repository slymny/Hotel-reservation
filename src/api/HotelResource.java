package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    private static final HotelResource hotelResource = new HotelResource();
    private static final ReservationService reservationService = ReservationService.getInstance();
    private static final CustomerService customerService = CustomerService.getInstance();

    public static HotelResource getInstance() {
        return hotelResource;
    }

    public Customer getCustomer(String email) {
        Customer customer = customerService.getCustomer(email);
        if(customer == null) throw new IllegalArgumentException("Invalid input!");
        return customer;
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        return reservationService.getCustomerReservations(customerEmail);
    }

    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }

    public Collection<IRoom> recommendRoom(Date checkIn, Date checkOut, int addDays) {
        return reservationService.recommendedRooms(checkIn, checkOut, addDays);
    }

    public Date addDays(Date date, int day) {return reservationService.addDaysToDate(date, day);}
}
