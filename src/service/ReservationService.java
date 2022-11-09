package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static final ReservationService reservationService = new ReservationService();
    private static final List<Reservation> reservations = new ArrayList<>();
    private static final Map<String, IRoom> rooms = new HashMap<>();

    public static ReservationService getInstance() {
        return reservationService;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room : rooms.values()) {
            if (reservationService.checkAvailability(room, checkInDate, checkOutDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Collection<IRoom> recommendedRooms(Date checkInDate, Date checkOutDate, int addDays) {
        Date checkInRecommended = addDaysToDate(checkInDate, addDays);
        Date checkOutRecommended = addDaysToDate(checkOutDate, addDays);
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

    public Collection<Reservation> getCustomerReservations(String customerEmail) {
        List<Reservation> reservationsOfCustomer = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().getEmail().equals(customerEmail)) {
                reservationsOfCustomer.add(reservation);
            }
        }
        return reservationsOfCustomer;
    }

    boolean checkAvailability(IRoom room, Date checkInDate, Date checkOutDate) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().getRoomNumber().equals(room.getRoomNumber())) {
                Date checkIn = reservation.getCheckInDate();
                Date checkOut = reservation.getCheckOutDate();
                if (checkInDate.before(checkOut) && checkOutDate.after(checkIn)) {
                    return false;
                } else if (checkOutDate.equals(checkOut) || checkInDate.equals(checkIn)) {
                    return false;
                }
            }
        }
        return true;
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
            throw new IllegalArgumentException("A room with the provided room number already exists!");
        }
        rooms.put(room.getRoomNumber(), room);
    }
}
