package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private static AdminResource instance;
    CustomerService customerService;
    ReservationService reservationService;

    public AdminResource() {
        this.customerService = new CustomerService();
        this.reservationService = new ReservationService();
    }

    public static AdminResource getInstance() {
        if (instance == null) {
            instance = new AdminResource();
        }
        return instance;
    }

    public Customer getCustomer(String email) {
        Customer customer = customerService.getCustomer(email);
        return customer;
    }

    public void addRoom(List<IRoom> rooms) {
        if (rooms == null) {
            return;
        }

        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }

    }

    public Collection<IRoom> getAllRooms() {

        return reservationService.getAllRoom();
    }

    public Collection<Customer> getAllCustomers() {

        return this.customerService.getAllCustomers();
    }

    public void displayAllReservations() {

        reservationService.printAllReservation();
    }
}
