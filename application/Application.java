package application;

import java.util.Scanner;
import reservationapp.dao.*;

public class Application {
	public static String firstName;
	public static String lastName;
	static ReservationDao reservationSQL = new ReservationDao();
	
	public static void main(String[] args) {
		signIn();
	}
	
	public static void signIn() {
		reservationSQL.signIn();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to your reservation self-service portal. Please sign-in");
		System.out.print("First name: ");
		firstName = scanner.nextLine();
		System.out.print("Last name: ");
		lastName = scanner.nextLine();
		
		Menu menu = new Menu();
		menu.start();
	}

}
