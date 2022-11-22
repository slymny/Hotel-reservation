package model;

import java.util.Objects;

public class Room implements IRoom {
    private final String roomNumber;
    private final Double price;
    private final RoomType roomType;

    public Room(String roomNumber, Double price, RoomType roomType) {
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.price = price;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room room)) return false;
        return getRoomNumber().equals(room.getRoomNumber()) && price.equals(room.price) && getRoomType() == room.getRoomType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomNumber(), price, getRoomType());
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", price=" + price + " Euro " +
                ", roomType=" + roomType +
                '}';
    }
}
