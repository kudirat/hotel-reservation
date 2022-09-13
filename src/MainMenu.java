import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;
import service.CustomerService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {

    static HotelResource hotelResource = new HotelResource();
    static AdminResource adminResource = new AdminResource();
    //static String DATE_FORMAT = "MM/dd/yyyy";
    static DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");


    public static void findAndReserve(Scanner scanner) {
        Date dateIn = null;
        Date dateOut = null;
        String email = null;
        String dateInS = null;
        String dateOutS = null;
        Customer customer = null;
        Collection<IRoom> rooms = null;
        Collection<IRoom> recRooms = null;
        Collection<IRoom> currRooms = null;

        System.out.println("Are you a customer? (Y/N)");
        String cutomConf = scanner.next();
        switch (cutomConf) {
            case "Y":
                System.out.println("Great. Please enter your email");
                email = scanner.next();
                if (adminResource.getCustomer(email) == null) {
                    System.out.println("This customer does not exist. Please create an account.");
                    mainMenu();
                    break;
                }
                System.out.println("Great. Please enter your check in date in the following format: mm/dd/yyyy");
                dateInS = scanner.next();
                System.out.println("Now please enter your check out date in the following format: mm/dd/yyyy");
                dateOutS = scanner.next();
                try {
                    dateIn = formatter.parse(dateInS);
                    dateOut = formatter.parse(dateOutS);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }


                rooms = hotelResource.findARoom(dateIn, dateOut);

                if (rooms.isEmpty()) {
                    System.out.println("Was not able to find any available rooms. Checking for recommended rooms");
                    recRooms = getRecRooms(dateIn, dateOut);
                    if (recRooms.isEmpty()) {
                        System.out.println("Was not able to find any possible rooms.");
                        return;
                    }
                    return;
                }
                System.out.println("Here are the available rooms: " + "\n" + rooms);

                for (IRoom room : rooms) {
                    try {
                        hotelResource.bookARoom(email, room, dateIn, dateOut);
                    } catch (Exception e) {
                    } finally {
                        break;
                    }
                }

                System.out.println("A room had been reserved");
                break;
            case "N":
                //create the customer
                System.out.println("Please enter your First and Last Name. As well as your email.");
                String fName = scanner.next();
                String lName = scanner.next();
                email = scanner.next();

                System.out.println(fName + " " + lName + " " + email + " ");
                hotelResource.createACustomer(email, fName, lName);
                customer = hotelResource.getCustomer(email);
                if (hotelResource.getCustomer(email) == null) {

                }
                System.out.println("Great. Please enter your check in date in the following format: mm/dd/yyyy");
                dateInS = scanner.next();
                System.out.println("Now please enter your check out date in the following format: mm/dd/yyyy");
                dateOutS = scanner.next();
                try {
                    dateIn = formatter.parse(dateInS);
                    dateOut = formatter.parse(dateOutS);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

                rooms = hotelResource.findARoom(dateIn, dateOut);

                if (rooms.isEmpty()) {
                    System.out.println("Was not able to find any available rooms. Checking for recommended rooms");
                    recRooms = getRecRooms(dateIn, dateOut);
                    if (recRooms.isEmpty()) {
                        System.out.println("Was not able to find any possible rooms.");
                        return;
                    }
                    return;
                }
                System.out.println("Here are the available rooms: " + "\n" + rooms);

                for (IRoom room : rooms) {
                    try {
                        hotelResource.bookARoom(email, room, dateIn, dateOut);
                    } catch (Exception e) {
                    } finally {
                        break;
                    }

                }
                System.out.println("Rooms have been found and reserved");
                break;
            default:
                System.out.println("Please input Y for yes, or N for no.");
        }
    }

    public static Collection<IRoom> getRecRooms(Date checkIn, Date checkOut) {
        Collection<IRoom> recRooms = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(checkIn);
        cal.add(Calendar.DATE, 7);
        Date modCheckIn = cal.getTime();
        //checkout
        cal.setTime(checkOut);
        cal.add(Calendar.DATE, 7);
        Date modCheckOut = cal.getTime();
        recRooms = hotelResource.findARoom(modCheckIn, modCheckOut);

        System.out.println("All rooms are currently booked. We do however recommend the following rooms for check in on: " + modCheckIn + " and check out on: " + modCheckOut);

        for (IRoom room : recRooms) {
            System.out.println(room);
        }
        return recRooms;
    }

    public static void createAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your email: eg. name@domain.com");
        String email = scanner.nextLine();

        System.out.println("First Name:");
        String fName = scanner.nextLine();

        System.out.println("Last Name:");
        String lName = scanner.nextLine();

        try {
            hotelResource.createACustomer(email, fName, lName);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid email. Returning to Main Menu. \n");
            return;
        }

        System.out.println("Account created");

    }

    public static void printReservations(Collection<Reservation> reservations) {
        if (reservations == null) {
            System.out.println("No reservations have been made");
            return;
        }
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    public static void displayMenu() {
        System.out.print("\nWelcome to the Hotel Reservation Application. Please select one of the following options:\n" +
                "--------------------------------------------\n" +
                "1. Find and reserve a room\n" +
                "2. See my reservations\n" +
                "3. Create an Account\n" +
                "4. Admin\n" +
                "5. Exit\n" +
                "--------------------------------------------\n");
    }

    public static void mainMenu() {
        Scanner userInput = new Scanner(System.in);
        boolean inMenu = true;

        displayMenu();
        int option = userInput.nextInt();

        switch (option) {
            case 1:
                findAndReserve(userInput);
                break;
            case 2:
                System.out.println("Please enter your email: eg. name@domain.com");
                String email = userInput.next();
                Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);
                printReservations(reservations);
                break;
            case 3:
                createAccount();
                break;
            case 4:
                AdminMenu.adminMenu();
                break;
            case 5:
                inMenu = false;
                System.exit(0);
            default:
                System.out.println("Please enter a valid option. \\n\\n");

        }

        mainMenu();
    }


}
