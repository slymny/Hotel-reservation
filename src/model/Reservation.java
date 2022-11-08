package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String extractedCheckIn = formatter.format(checkInDate);
        String extractedCheckOut = formatter.format(checkOutDate);

        return
                "Reservation: " + "\n" +
                "   Customer: " + customer + "\n" +
                "   Room: " + room + "\n" +
                "   Check in date: " + extractedCheckIn +
                " Check out date: " + extractedCheckOut;
    }
}
