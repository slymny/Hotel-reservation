package model;

public class FreeRoom extends Room{

    public FreeRoom(String roomNumber, RoomType type) {
        super(roomNumber, 0.0, type);
    }

    @Override
    public String toString( ) {
        return String.format("Room number: %s \n", roomNumber) +
                String.format("Room price: %f \n", price) +
                String.format("Room type: %s", roomType);
    }
}
