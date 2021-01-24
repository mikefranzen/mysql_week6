package application;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.sql.Date;


public class Menu {

	
	private Scanner scanner = new Scanner(System.in);
	private List<String> options = Arrays.asList(
			"Show Your Reservations",
			"Make Reservation",
			"Cancel Reservation",
			"Exit");
	
	public void start() {
		String selection = "";
		
		do {
			printMenu();
			selection = scanner.nextLine();
			
			if (selection.equals("1")) {
				Application.reservationSQL.listReservations();
			} else if (selection.equals("2")) {
				System.out.println("Available rooms\n-----------------------");
				Application.reservationSQL.listAvailableRooms();
				System.out.print("Select room #: ");
				int roomId = scanner.nextInt();
				System.out.print("Date (YYYY-MM-DD): ");
				String strDate = scanner.nextLine();
				try {
					Date date = Date.valueOf(strDate);
					Application.reservationSQL.createReservations(roomId, date);;
				} catch (Exception e){
					System.out.println("Invalid, please try again");
				}
				
			} else if (selection.equals("3")) {
				Application.reservationSQL.listReservations();
				System.out.print("Select reservation #: ");
				int reservationId = scanner.nextInt();
				Application.reservationSQL.deleteReservation(reservationId);
			} else if (selection.equals("4")) {
				Application.signIn();
				break;
			}
		} while (!selection.equals("-1"));
	}

	private void printMenu() {
		System.out.println("Select an Option:\n-----------------------");
		for (int i = 0; i < options.size(); i++) {
			System.out.println((i + 1 + " ") + options.get(i));
		}
	}
}
