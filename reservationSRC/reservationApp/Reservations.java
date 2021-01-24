package reservationApp;

import java.text.ParseException;
import java.util.Scanner;

import reservationDB.ReservationDAO;

public class Reservations {
	// These will hold the name for the person signed in at any time
	public static String firstName = "";
	public static String lastName = "";

	// creates a new data access object
	static ReservationDAO dataAccess = new ReservationDAO();

	// starts the application and requires user info
	public static void main(String[] args) throws ParseException {
		signIn();
	}

	public static void signIn() throws ParseException {
		// asks for user's first and last name, stores them in the associated class
		// variables
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to your reservation self-service portal. Please sign-in");
		System.out.print("First name: ");
		firstName = scanner.nextLine();
		System.out.print("Last name: ");
		lastName = scanner.nextLine();

		// calls our DAO sign-in method to make sure there is a database user with this
		// name
		dataAccess.signIn();
		System.out.println();

		// starts the app logic to allow users to manage their own reservations
		Menu menu = new Menu();
		menu.start();
	}

}
