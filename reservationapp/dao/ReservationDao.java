package reservationapp.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.Application;

public class ReservationDao {

	int userId ;

public void signIn() {
	
	try{
		Connection connection = DBconnection.getConnection();
		String sql1 = "select user_id from users where first_name = ? and last_name = ?;";
		String sql = "insert into users (first_name, last_name) values (? , ?);";
		try{
			PreparedStatement statement = connection.prepareStatement(sql1);
			statement.setString(1, Application.firstName.toLowerCase());
			statement.setString(2, Application.lastName.toLowerCase());
			ResultSet rs = statement.executeQuery();
			if (!rs.next()) {
				statement = connection.prepareStatement(sql);
				statement.setString(1, Application.firstName.toLowerCase());
				statement.setString(2, Application.lastName.toLowerCase());
				statement.execute();
				
				signIn();
			} else {
				userId = rs.getInt("user_id");

			} 
		} catch (SQLException e){
			
		}
	} catch (SQLException e){
		
	}
}
	public void listReservations(){
		try {
			Connection connection = DBconnection.getConnection();
			String sql1 = "select reservations.reservation_id, rooms.room_name, rooms.occupancy, "
					+ "reservations.reservation_date from rooms, reservations where rooms.room_id = "
					+ "reservations.room_id and reservations.user_id = ?; ";
			PreparedStatement statement = connection.prepareStatement(sql1);
			if (userId > 0) {
					
						statement.setInt(1, userId);
						ResultSet rs = statement.executeQuery();																	
						while(rs.next()) {									
							String reservation = 
								"Reservation #: " + rs.getInt("reservation_id")
								+ " Room name: " + rs.getString("room_name")
								+ " Max occupancy: " + rs.getInt("occupancy")
								+ " Date: " + rs.getDate("reservation_date");
							System.out.println(reservation);
						}
					
				}
			} catch(SQLException e) {
					System.out.println("No reservations"); 									
			}												
						
	}

	public void listAvailableRooms(java.sql.Date date) {
		
		try {
		Connection connection = DBconnection.getConnection();
			String sql2 = "select distinct rooms.room_id, rooms.room_name, rooms.occupancy, reservations.reservation_date from rooms left outer join reservations on rooms.room_id = reservations.room_id where reservations.reservation_date is NULL or reservations.reservation_date <> ?;";
			
			
			
			PreparedStatement statement = connection.prepareStatement(sql2);
			statement.setDate(1, date);
			ResultSet rs = statement.executeQuery();	
			
			List<Integer> roomIds = new ArrayList<>();
			//if (!rs.next()) {
			//	throw 
			//}
			while(rs.next()) {
				if (roomIds.contains(rs.getInt("room_id"))) {
					
				} else {
				String rooms = 
					"Room #: " + rs.getInt("room_id")
					+ " Room name: " + rs.getString("room_name")
					+ " Max occupancy: " + rs.getInt("occupancy");
				System.out.println(rooms);
					roomIds.add(rs.getInt("room_id"));
				}
			}
			
			
			
		} catch(SQLException e) {
			throw new RuntimeException("This is the problem");
		}	
		catch(Exception e) {
			
		}	
		
	}
	
public void createReservations(int roomId, Date date) {
		
		try {
			Connection connection = DBconnection.getConnection();
			
			String sql = "Insert into reservations (user_id, room_id, reservation_date) values ( ?, ?, ?);";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userId);
			statement.setInt(2, roomId);
			statement.setDate(3, date);
			
			statement.executeUpdate();
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}	
		
	}
	
public void deleteReservation(int reservationId){
	try {
		Connection connection = DBconnection.getConnection();
		String sql1 = "delete from reservations where reservation_id = ?;";
		PreparedStatement statement = connection.prepareStatement(sql1);
					statement.setInt(1, reservationId);
		statement.executeUpdate();
		} catch(SQLException e) {
				throw new RuntimeException(e); 									
		}												
	}				
}