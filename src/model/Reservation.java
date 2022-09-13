package model;

import java.util.Date;

public class Reservation {
    public Customer customer;
    public IRoom room;
    public Date checkInDate;
    public Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        // super(); this just calls the superclass' constructer. we currently don't have one
        super();
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer(){
        return this.customer;
    }

    public IRoom getRoom(){
        return this.room;
    }

    public Date getCheckIn(){
        return this.checkInDate;
    }

    public Date getCheckOutDate(){
        return this.checkOutDate;
    }

    @Override
    public String toString() {
        return "Customer: " + customer + "." + "Room: " + room + ". " + "Check In Date: " + checkInDate + ". " + "Check Out Date: " + checkOutDate + ".";
    }

}
