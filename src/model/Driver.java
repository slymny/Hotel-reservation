package model;

import service.ReservationService;

import java.util.Date;

public class Driver {
    public static void main(String[] args) {
        Customer customer = new Customer("first", "second", "j@domain.com");
        IRoom room = new Room("101", 101.0, RoomType.SINGLE);
        IRoom room2 = new Room("101", 101.0, RoomType.DOUBLE);
        Reservation reservation = new Reservation(customer, room, new Date(), ReservationService.reservationService.addDaysToDate(new Date(), 7));
        System.out.println(reservation);
    }
}
