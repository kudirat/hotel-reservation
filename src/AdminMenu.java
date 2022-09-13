import api.AdminResource;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    public static AdminResource adminResource = new AdminResource();

    public static void displayMenu() {
        System.out.print("\nWelcome to the Hotel Reservation Application. Please select one of the following options:\n" +
                "--------------------------------------------\n" +
                "1. See all Customers\n" +
                "2. See all Rooms\n" +
                "3. See all Reservations\n" +
                "4. Add a Room\n" +
                "5. Back to Main Menu\n" +
                "--------------------------------------------\n");
    }

    public static void seeAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println("" + customer + " ");
        }
    }

    public static void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();

        for (IRoom room : rooms) {
            System.out.println(room);
        }
    }

    public static IRoom addARoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give a room number");
        String roomNum = scanner.next();
        System.out.println("Enter the Room Type: (SINGLE/DOUBLE)");
        RoomType roomType = RoomType.valueOf(scanner.next());

        System.out.println("Enter the room's price in the following format: 0.0");

        Double price = scanner.nextDouble();

        IRoom room = new Room(roomNum, roomType, price);

        Collection<IRoom> rooms = adminResource.getAllRooms();

        for (IRoom currRoom : rooms) {
            if (currRoom == room) {
                System.out.print("room exists already");
                return room;
            }
        }
        List<IRoom> roomsList = new ArrayList<>();
        roomsList.add(room);

        adminResource.addRoom(roomsList);
        return room;
    }

    public static void adminMenu() {
        Scanner userInput = new Scanner(System.in);
        displayMenu();
        int option = userInput.nextInt();
        switch (option) {
            case 1:
                seeAllCustomers();
                break;
            case 2:
                seeAllRooms();
                break;
            case 3:
                adminResource.displayAllReservations();
                break;
            case 4:
                addARoom();
                break;
            case 5:
                MainMenu.mainMenu();
            default:
                System.out.println("Please enter a valid option. \\n\\n");

        }
        adminMenu();
    }
}
