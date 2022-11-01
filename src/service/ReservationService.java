package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    public static final ReservationService reservationService = new ReservationService();
    private static final List<Reservation> reservations = new ArrayList<>();
    private static final Map<String, IRoom> rooms = new HashMap<>();

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : rooms.values()) {
            if (reservationService.checkAvailability(room, checkInDate, checkOutDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Collection<IRoom> recommendedRooms(Date checkInDate, Date checkOutDate) {
        System.out.println("No room found in the given date!");
        Date checkInRecommended = addDaysToDate(checkInDate, 7);
        Date checkOutRecommended = addDaysToDate(checkOutDate, 7);
        System.out.println("Recommended room for 7 days later: ");
        return findRooms(checkInRecommended, checkOutRecommended);
    }

    public Date addDaysToDate(Date date, int add) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, add);
        return cal.getTime();
    }

    public IRoom getARoom(String roomNumber) {
        return rooms.get(roomNumber);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if (reservationService.checkAvailability(room, checkInDate, checkOutDate)) {
            Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
            reservations.add(newReservation);
            return newReservation;
        }
        return null;
    }

    public Collection<Reservation> getCustomerReservations(Customer customer) {
        List<Reservation> reservationsOfCustomer = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().getEmail().equals(customer.getEmail())) {
                reservationsOfCustomer.add(reservation);
            }
        }
        return reservationsOfCustomer;
    }

    private boolean checkAvailability(IRoom room, Date checkInDate, Date checkOutDate) {
        List<Reservation> reservationsOfTheRoom = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(room)) {
                reservationsOfTheRoom.add(reservation);
            }
        }
        if (reservationsOfTheRoom.isEmpty()) {
            return true;
        }
        for (Reservation reservation : reservationsOfTheRoom) {
            Date checkIn = reservation.getCheckInDate();
            Date checkOut = reservation.getCheckOutDate();
            if (checkInDate.after(checkOut) || checkOutDate.before(checkIn)) {
                return true;
            }
        }
        return false;
    }

    public void printAllReservation() {
        if (reservations.isEmpty()) {
            System.out.println("There is no reservation yet!");
        }
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    public void addRoom(IRoom room) {
        if (rooms.get(room.getRoomNumber()) != null) {
            throw new IllegalArgumentException("Provided room already exists!");
        }
        rooms.put(room.getRoomNumber(), room);
    }
}
