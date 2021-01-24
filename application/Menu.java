package application;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
//import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;





public class Menu {

	
	private Scanner scanner = new Scanner(System.in);
	private List<String> options = Arrays.asList(
			"Show Your Reservations",
			"Make Reservation",
			"Cancel Reservation",
			"Exit");
	
	public void start() throws ParseException {
		try {
		String selection = "";
		
		do {
			printMenu();
			selection = scanner.nextLine();
			
			if (selection.equals("1")) {
				Application.reservationSQL.listReservations();
			} else if (selection.equals("2")) {
				
				System.out.println("Date (YYYY-MM-DD): ");
				Scanner scanner1 = new Scanner(System.in);
				String strDate = scanner1.nextLine();
				
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			
				java.util.Date date;
				
				date = sdf1.parse(strDate);
				java.sql.Date sqlDate = new java.sql.Date(date.getTime());
				
				System.out.println("Available rooms at requested time\n-----------------------");
				Application.reservationSQL.listAvailableRooms(sqlDate);
				System.out.print("Select room #: ");
				int roomId = scanner.nextInt();
				
				
				Application.reservationSQL.createReservations(roomId, sqlDate);
				
				
				
			} else if (selection.equals("3")) {
				Application.reservationSQL.listReservations();
				System.out.print("Select reservation #: ");
				int reservationId = scanner.nextInt();
				Application.reservationSQL.deleteReservation(reservationId);
			} else if (selection.equals("4")) {
				Application.signIn();
				break;
			}
			scanner.nextLine();
		} while (!selection.equals("-1"));
		} catch (ParseException ex) {
			throw ex;
		}
	} 

	private void printMenu() {
		System.out.println("Select an Option:\n-----------------------");
		for (int i = 0; i < options.size(); i++) {
			System.out.println((i + 1 + " ") + options.get(i));
		}
	}
	
}

