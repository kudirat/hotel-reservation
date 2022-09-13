package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.*;

public class HotelResource {
    private static HotelResource instance;
    CustomerService customerService;


    ReservationService reservationService;

    public HotelResource() {
        this.customerService = new CustomerService();
        this.reservationService = new ReservationService();
        Map<String, Customer> customers = customerService.mapOfCustomers;
    }

    public static HotelResource getInstance() {
        if (instance == null) {
            instance = new HotelResource();
        }
        return instance;
    }


    public Customer getCustomer(String email) {
        Customer customer = this.customerService.getCustomer(email);
        return customer;
    }

    public void createACustomer(String email, String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName, email);
        if (!(getCustomer(customer.email) == null)) {
            System.out.println("Customer exists already.");
            return;
        }
        this.customerService.addCustomer(customer.email, customer.firstName, customer.lastName);
        //customer printed
        this.customerService.getAllCustomers();
    }

    public IRoom getRoom(String roomNumber) {
        IRoom room = this.reservationService.getARoom(roomNumber);
        return room;
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = this.customerService.getCustomer(customerEmail);
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        //if the customer does not have a reservation
        if (this.reservationService.getCustomersReservation(customer) == null) {
            System.out.println("Customer has no reservations at this time");
            return reservation;
        }
        Collection<Reservation> customerRes = this.reservationService.getCustomersReservation((customer));
        reservation = this.reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
        if (!customerRes.contains(reservation)) {
            customerRes.add(reservation);
        }


        return reservation;
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Collection<Reservation> currReservations = new ArrayList<>();
        Customer customer = customerService.getCustomer(customerEmail);
        if (this.reservationService.getCustomersReservation(customer) == null) {
            System.out.println("No reservations found at this time");
            return currReservations;
        }

        Collection<Reservation> customerRes = this.reservationService.getCustomersReservation(customer);
        Iterator<Reservation> reservationIterator = currReservations.iterator();

        //iterate through collection from reservation service and add it to the collections to be returned by this method
        for (Reservation reservation : customerRes) {
            currReservations.add(reservation);
        }

        return currReservations;
    }


    public Collection<IRoom> findARoom(Date checkIn, Date checkOut) {
        Collection<IRoom> rooms = new ArrayList<>();

        rooms = this.reservationService.findRooms(checkIn, checkOut);


        return rooms;
    }

}
