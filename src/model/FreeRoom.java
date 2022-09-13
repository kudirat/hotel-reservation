package model;

public class FreeRoom extends Room{
    public FreeRoom(String roomNumber, RoomType enumeration, Double price) {
        super(roomNumber, enumeration, price);
        super.price = 0.0;
    }

    @Override
    public String toString() {
        return "Price: " + price;
    }
}
