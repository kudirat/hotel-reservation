package model;

import java.util.Objects;

public class Room implements IRoom {
    private String roomNumber;
    private RoomType enumeration;
    public Double price;

    public Room(String roomNumber, RoomType enumeration, Double price){
        //super();
        this.roomNumber = roomNumber;
        this.enumeration = enumeration;
        this.price = price;
    }


    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.enumeration;
    }

    @Override
    public boolean isFree() {
        if(this.price.equals(0.0) && !this.equals(null)){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Room Number: " + this.roomNumber + ". " + "RoomType: " + this.enumeration + ". " + "Price: " + this.price;
    }

    @Override
    public int hashcode(){
        return Objects.hash(roomNumber);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this){
            return true;
        }
        if(!(obj instanceof  Room)){
            return false;
        }
        Room room = (Room) obj;
        return Objects.equals(this.roomNumber, room.roomNumber);
    }

}
