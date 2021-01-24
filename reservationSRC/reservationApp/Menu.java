package reservationApp;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Menu {

	// prepare to recieve option selection and create menu
	private Scanner scanner = new Scanner(System.in);
	private List<String> options = Arrays.asList("Show Your Reservations", "Make Reservation", "Cancel Reservation",
			"Sign out", "Quit");

	public void start() throws ParseException {
		try {
			// instantiates a string to hold the menu option selected
			String selection = "";

			do {
				// calls the printMenu() method in the current class to display menu options and
				// prompt for input
				printMenu();

				// records user's selected option
				selection = scanner.nextLine();

				// menu logic
				if (selection.equals("1")) { // 1 Show Your Reservations
					Reservations.dataAccess.listReservations();
				} else if (selection.equals("2")) { // 2 Make reservation
					String strDate = "";
					// Prompt for desired date for new reservation
					// NOTE: reservations are all-day
					do {
						System.out.println("Date (YYYY-MM-DD): ");
						Scanner scanner1 = new Scanner(System.in);
						strDate = scanner1.nextLine();
					} while (strDate == "");

					// next lines format the user submitted date from a String to java.util.Date,
					// then parses to java.sql.Date
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
					java.util.Date date = sdf1.parse(strDate);
					java.sql.Date sqlDate = new java.sql.Date(date.getTime());

					// lists all rooms that don't have reservations for the requested day
					System.out.println("Rooms available on " + date + "\n-----------------------");

					// both prints a list of available rooms with the ReservationDAO,
					// and returns the ID's of available rooms
					List<Integer> available = Reservations.dataAccess.listAvailableRooms(sqlDate);

					// if there are no available rooms, don't prompt for selection
					if (available.isEmpty()) {
						System.out.println("0 available rooms for this date");
					} else {
						// prompts user to select room to reserve
						// validates that the user selection is in the list of available rooms
						int roomId;
						do {
							// prompts for selected room
							System.out.print("Select available room # (or '0' to exit): ");
							roomId = scanner.nextInt();
							// allows user to exit selection loop
							if (roomId == 0) {
								break;
							}
						} while (!available.contains(roomId)); // loops until a valid roomId is selected

						if (roomId != 0) {
							// calls the reservationDAO to create new reservation with selected room ID and
							// date
							Reservations.dataAccess.createReservations(roomId, sqlDate);
						}
					}
				} else if (selection.equals("3")) { // 3 Cancel Reservation
					// starts by showing the user's reservations
					List<Integer> reservations = Reservations.dataAccess.listReservations();

					int reservationId;
					// if there is no reservation for the user, don't prompt for selection
					if (reservations.isEmpty()) {
						System.out.println("You have no reservations");
					} else {
						// prompts user to select reservation to cancel
						// validates that the user selection is in the list of reservations
						do {
							// prompts for selected reservation
							System.out.print("Select reservation # (or '0' to exit): ");
							reservationId = scanner.nextInt();
							// allows user to exit selection loop
							if (reservationId == 0) {
								break;
							}
						} while (!reservations.contains(reservationId));

						if (reservationId != 0) {
							// calls the reservationDAO to delete selected reservation by reservation ID
							Reservations.dataAccess.deleteReservation(reservationId);
						}
					}
				} else if (selection.equals("4")) { // 4 Sign out
					System.out.println("Signing out.......");
					Reservations.signIn();
					break;
				} else if (selection.equals("5")) { // 5 Quit
					// stops the entire menu loop without asking for new sign-in
					selection.equals("-1");
					System.out.println("Quitting application.......");
				} else {
					System.out.println("No valid menu option selected");
				}
				scanner.nextLine();
			} while (!selection.equals("-1"));
		} catch (ParseException ex) {
			throw ex;
		}
	}

	// displays all the menu options
	private void printMenu() {
		System.out.println("Select an Option:\n-----------------------");
		for (int i = 0; i < options.size(); i++) {
			System.out.println((i + 1 + " ") + options.get(i));
		}
	}

}
