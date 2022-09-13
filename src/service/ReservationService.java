package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.*;

public class ReservationService {
    private static ReservationService instance;

    private static Map<Customer, Collection<Reservation>> reservations;

    private static Map<String, IRoom> rooms;
    private static Map<IRoom, Boolean> currReservedRooms;

    public ReservationService() {
        this.reservations = new HashMap<>();
        this.rooms = new HashMap<>();
        this.currReservedRooms = new HashMap<>();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public Collection<IRoom> getAllRoom() {
        return rooms.values();
    }

    public void addRoom(IRoom room) {
        if (room == null) {
            System.out.println("Room is null");
            return;
        }
        rooms.put(room.getRoomNumber(), room);
    }

    public IRoom getARoom(String roomID) {
        if (roomID.isEmpty()) {
            System.out.println("RoommId is null");
            return null;
        }
        return rooms.get(roomID);

    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        //create reservation
        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        //add to collection of reservations
        //get customer's reservation if they exist in the map. if they don't make a collection, add this reservation, and add them to the map
        Collection<Reservation> cusReserv = getCustomersReservation(customer);
        if (cusReserv == null || cusReserv.isEmpty()) {
            cusReserv = new ArrayList<>();
        }
        cusReserv.add(newReservation);
        for (Reservation reservation : cusReserv) {
            System.out.println(reservation);
            currReservedRooms.put(reservation.room, true);
        }
        reservations.put(customer, cusReserv);
        for (Customer customer1 : reservations.keySet()) {
            System.out.println(reservations.get(customer1));
        }

        return newReservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new ArrayList<>();
        //traverse through reservations
        for (Collection<Reservation> reservationCollection : reservations.values()) {
            //get each reservation room
            for (Reservation reservation : reservationCollection) {
                IRoom room = reservation.room;
                //if this reservations' room exists, check if the dates overlap
                if ((rooms.containsKey(reservation.getRoom().getRoomNumber()))) {
                    //check in and check out conditions
                    if (checkInDate.compareTo(reservation.checkInDate) == 0 && checkOutDate.compareTo(reservation.checkOutDate) == 0) {
                        if (!currReservedRooms.containsKey(reservation.room)) {
                            currReservedRooms.put(reservation.room, true);
                        }
                    }
                    if (((checkInDate.before(reservation.checkInDate)) && (checkOutDate.before(reservation.checkInDate)))) {
                        availableRooms.add(room);
                    } else if (((checkInDate.after(reservation.checkInDate)) && (checkOutDate.after(reservation.checkOutDate)))) {
                        availableRooms.add(room);
                    }

                }
            }
        }

        //add other rooms that are not reserved
        for (IRoom room : rooms.values()) {
            if (!(availableRooms.contains(room))) {
                if (!(currReservedRooms.containsKey(room)) || (currReservedRooms.get(room).booleanValue() == false)) {
                    availableRooms.add(room);
                }
            }
        }
        return availableRooms;
    }


    Map<IRoom, Boolean> getReservedRooms() {
        return this.currReservedRooms;
    }


    public Collection<Reservation> getCustomersReservation(Customer customer) {
        if (customer == null) {
            return null;
        }
        Collection<Reservation> currReservations = reservations.get(customer);

        if (currReservations == null) {
            currReservations = new ArrayList<>();
        }


        return currReservations;
    }

    public void printAllReservation() {
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations made at this time.");
        }
        for (Customer customer : reservations.keySet()) {
            Collection<Reservation> reservationCollection = getCustomersReservation(customer);
            for (Reservation reservation : reservationCollection) {
                System.out.println(reservation);
            }
        }
    }

}
