package reservationapp.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;




public class ReservationDao {

String firstName = "";
String lastName = "";
int userId;

public void signIn() {
	
	try{
		Connection connection = DBconnection.getConnection();
		String sql1 = "select user_id from users where first_name = ? and last_name = ?;";
		String sql = "insert into users (first_name, last_name) values (? , ?);";
		try{
			PreparedStatement statement = connection.prepareStatement(sql1);
			statement.setString(1, firstName.toLowerCase());
			statement.setString(2, lastName.toLowerCase());
			ResultSet rs = statement.executeQuery();
			userId = rs.getInt("user_id");
			
			if (userId < 1) {
				statement = connection.prepareStatement(sql);
				statement.setString(1, firstName.toLowerCase());
				statement.setString(2, lastName.toLowerCase());
				statement.executeUpdate();
				
				statement = connection.prepareStatement(sql1);
				statement.setString(1, firstName.toLowerCase());
				statement.setString(2, lastName.toLowerCase());
				rs = statement.executeQuery();
				userId = rs.getInt("user_id");
			} 
		} catch (Exception e){
			
		}
	} catch (Exception e){
		
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
			} catch(Exception e) {
					throw new RuntimeException(e); 									
			}												
						
	}
	

	public void listAvailableRooms() {
		
		try {
			Connection connection = DBconnection.getConnection();
			String sql2 = "select rooms.room_id, rooms.room_name, rooms.occupancy, reservations.reservation_date "
					+ "from rooms left outer join reservations "
					+ "on rooms.room_id = reservations.room_id "
					+ "where reservations.reservation_date is NULL or reservation_date <> ?;";
			
			PreparedStatement statement = connection.prepareStatement(sql2);
			ResultSet rs = statement.executeQuery();																	
			while(rs.next()) {									
				String rooms = 
					"Available room #: " + rs.getInt("room_id")
					+ " Room name: " + rs.getString("room_name")
					+ " Max occupancy: " + rs.getInt("occupancy");
				System.out.println(rooms);
			}
			
		} catch(SQLException e) {
			throw new RuntimeException(e);
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
		String sql1 = "delete from reservations where reservation_id - ?;";
		PreparedStatement statement = connection.prepareStatement(sql1);
					statement.setInt(1, reservationId);
		statement.executeUpdate();
		} catch(Exception e) {
				throw new RuntimeException(e); 									
		}												
	}				
}