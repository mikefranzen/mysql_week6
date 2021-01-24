package reservationDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import reservationApp.Reservations;

public class ReservationDAO {
	//stores the user ID at sign-in
	private int userId;

	//sign-in as a user before any menu options
	public void signIn() {
		try {
			//establishes the MySQL db connection
			Connection connection = DBconnection.getConnection();
			
			//defines sql queries for prepared statements
			String sql1 = "select user_id from users where first_name = ? and last_name = ?;";
			String sql = "insert into users (first_name, last_name) values (? , ?);";
			
			try {
				//first, check the db if the user exists (by name)
				PreparedStatement statement = connection.prepareStatement(sql1);
				statement.setString(1, Reservations.firstName.toLowerCase());
				statement.setString(2, Reservations.lastName.toLowerCase());
				ResultSet rs = statement.executeQuery();
				
				//if there is no user returned in the result set, then create new user
				if (!rs.next()) {
					statement = connection.prepareStatement(sql);
					statement.setString(1, Reservations.firstName.toLowerCase());
					statement.setString(2, Reservations.lastName.toLowerCase());
					statement.execute();
					
					//after creating new user, re-call signIn() to set userId
					signIn();
				} else {
					//set the user ID to the user_id field of the result set
					userId = rs.getInt("user_id");
				}
			} catch (SQLException e) {}
		} catch (SQLException e) {}
	}
	
	//show all reservations belonging to the signed-in user, return the list of reservation ID's
	public List<Integer> listReservations() {
		List<Integer> reservationIds = new ArrayList<>();
		
		try {
			//establishes the MySQL db connection
			Connection connection = DBconnection.getConnection();
			
			//defines sql query for prepared statement
			String sql1 = "select reservations.reservation_id, rooms.room_name, rooms.occupancy, "
					+ "reservations.reservation_date from rooms, reservations where rooms.room_id = "
					+ "reservations.room_id and reservations.user_id = ?; ";
			PreparedStatement statement = connection.prepareStatement(sql1);
			
			if (userId > 0) { //if the there is a user signed-in
				//find reservations where user_id = signed-in user's ID
				statement.setInt(1, userId);
				ResultSet rs = statement.executeQuery();
				
				//loop through the result set and display each row
				while (rs.next()) {
					String reservation = "Reservation #: " + rs.getInt("reservation_id") + "    Room name: "
							+ rs.getString("room_name") + "    Max occupancy: " + rs.getInt("occupancy") + "    Date: "
							+ rs.getDate("reservation_date");
					System.out.println(reservation);
					
					//add to integer list of reservation ID's
					reservationIds.add(rs.getInt("reservation_id"));
				}

			}
		} catch (SQLException e) {
			System.out.println("No reservations");
		}
		//return the list of reservation ID's
		return reservationIds;
	}

	//show all rooms available for a user to reserve at a given time
	public List<Integer> listAvailableRooms(java.sql.Date date) {
		List<Integer> roomIds = new ArrayList<>();

		try {
			//establishes the MySQL db connection
			Connection connection = DBconnection.getConnection();
			
			//defines sql query for prepared statement
			String sql1 = "select distinct rooms.room_id, rooms.room_name, "
					+ "rooms.occupancy, reservations.reservation_date from rooms "
					+ "left outer join reservations on rooms.room_id = reservations.room_id "
					+ "where reservations.reservation_date " + "is NULL or reservations.reservation_date <> ?;";
			PreparedStatement statement = connection.prepareStatement(sql1);
			statement.setDate(1, date);
			ResultSet rs = statement.executeQuery();

			//loop through the result set and display each row 
			while (rs.next()) {
				//don't display row if its ID is in the list already
				if (!roomIds.contains(rs.getInt("room_id"))) {
					String rooms = "Room #: " + rs.getInt("room_id") + "    Room name: " + rs.getString("room_name")
							+ "    Max occupancy: " + rs.getInt("occupancy");
					System.out.println(rooms);
					roomIds.add(rs.getInt("room_id"));
				}
			}

		} catch (SQLException e) {}
		
		//return the list of available room ID's
		return roomIds;
	}

	//creates reservations for a room with user ID and date
	public void createReservations(int roomId, Date date) {

		try {
			//establishes the MySQL db connection
			Connection connection = DBconnection.getConnection();

			//defines sql query for prepared statement
			String sql = "Insert into reservations (user_id, room_id, reservation_date) values ( ?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userId);
			statement.setInt(2, roomId);
			statement.setDate(3, date);
			
			//creates reservation
			statement.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	//remove reservation by selected reservation ID
	public void deleteReservation(int reservationId) {
		try {
			//establishes the MySQL db connection
			Connection connection = DBconnection.getConnection();
			
			//defines sql query for prepared statement
			String sql1 = "delete from reservations where reservation_id = ?;";
			PreparedStatement statement = connection.prepareStatement(sql1);
			statement.setInt(1, reservationId);
			
			//deletes the selected reservation
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
