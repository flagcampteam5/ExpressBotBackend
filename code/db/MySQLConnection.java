package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

//import entity.Item;
import entity.TransactionItem;
import entity.TransactionItem.TransactionItemBuilder;

public class MySQLConnection {
	protected Connection conn;

	public MySQLConnection() {
		try {
			// connect to MySQL, MySQLTableCreation used it too
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(MySQLDBUtil.URL);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
//	public void saveItem(Item item) {
//		if (conn == null) {
//			System.err.println("DB connection failed");
//			return;
//		}
//		String sql = "INSERT IGNORE INTO items VALUES (?, ?, ?, ?, ?)";
//		try {
//			PreparedStatement statement = conn.prepareStatement(sql);
//			statement.setString(1, item.getItemId());
//			statement.setString(2, item.getName());
//			statement.setString(3, item.getAddress());
//			statement.setString(4, item.getImageUrl());
//			statement.setString(5, item.getUrl());
//			statement.executeUpdate();
//
//			sql = "INSERT IGNORE INTO keywords VALUES (?, ?)";
//			statement = conn.prepareStatement(sql);
//			statement.setString(1, item.getItemId());
//			for (String keyword : item.getKeywords()) {
//				statement.setString(2, keyword);
//				statement.executeUpdate();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	//implement the API 1:  getOrder()
	public TransactionItem getOrder(String transId) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return null;
		}
		String sql = "SELECT * FROM transactions WHERE trans_id = ?";
		TransactionItem item = null;
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			TransactionItemBuilder builder = new TransactionItemBuilder();
if (rs.next()) {
				builder.setTrans_id(rs.getString("trans_id"));
				builder.setStart_station_id(rs.getInt("start_station_id"));
				builder.setEnd_station_id(rs.getInt("end_station_id"));
				builder.setStart_device_is_robot(rs.getBoolean("start_device_is_robot"));
				builder.setEnd_device_is_robot(rs.getBoolean("end_device_is_robot"));
				builder.setStatus_id(rs.getInt("status_id"));
				builder.setPick_up_lat(rs.getDouble("pick_up_lat"));
				builder.setPick_up_lon(rs.getDouble("pick_up_lon"));
				builder.setDeliver_location_lat(rs.getDouble("deliver_location_lat"));
				builder.setDeliver_location_lon(rs.getDouble("deliver_location_lon"));
				builder.setTimestamp(rs.getTimestamp("timestamp"));
				item = builder.build();

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

//	public Set<String> getOrder(String transId) {
//		if (conn == null) {
//			System.err.println("DB connection failed");
//			return new HashSet<>();
//		}		
//		Set<String> OrderItems = new HashSet<>();	
//
//		try {
//			String sql = "SELECT * FROM transactions WHERE trans_id = ?";
//			
//			PreparedStatement statement = conn.prepareStatement(sql);
//			statement.setString(1, transId);
//			ResultSet rs = statement.executeQuery();
//
//				while (rs.next()) {
//					String pickUpLat = rs.getString("pick_up_lat");
//					OrderItems.add(pickUpLat);
//					
//					String pickUpLon = rs.getString("pick_up_lon");
//					OrderItems.add(pickUpLon);
//					
//					String deviceStationLat = rs.getString("device_station_lat");
//					OrderItems.add(deviceStationLat);
//					
//					String deviceStationLon = rs.getString("device_station_lon");
//					OrderItems.add(deviceStationLon);
//					
//					String deviceLocationLat = rs.getString("device_location_lat");
//					OrderItems.add(deviceLocationLat);
//					
//					String deviceLocationLon = rs.getString("device_location_lon");
//					OrderItems.add(deviceLocationLon);
//					
//					String statusId = rs.getString("status_id");
//					OrderItems.add(statusId);
//				
//				}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return OrderItems;
//	}
	//implement the API 2 : updateOrderStatus()
	public void updateOrderStatus(String transId, int newStatus_id) {
		if(conn == null) {
			System.err.println("DB connection failed");
			return;
		}
		String sql = "UPDATE transactions SET status_is = newStatus_id WHERE trans_id = ?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1,newStatus_id);
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// implement the API 3: createOrder()
	public void createOrder(TransactionItem item) {
		if(conn == null) {
			System.err.println("DB connection failed");
			return;
		}
		String sql = "INSERT IGNORE INTO items VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, item.getTrans_id());
			statement.setLong(2, item.getStart_station_id());
			statement.setLong(3, item.getEnd_station_id());
			statement.setBoolean(4, item.isStart_device_is_robot());
			statement.setBoolean(5, item.isEnd_device_is_robot());
			statement.setLong(6, item.getStatus_id());
			statement.setDouble(7, item.getPick_up_lat());
			statement.setDouble(8, item.getPick_up_lon());
			statement.setDouble(9, item.getDeliver_location_lat());
			statement.setDouble(10, item.getDeliver_location_lon());
			statement.setTimestamp(11, item.getTimestamp());
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//test ///////////////////////////////////
	public static void main(String[] args) {
		MySQLConnection s = new MySQLConnection();
		TransactionItemBuilder builder = new TransactionItemBuilder();
		
		builder.setTrans_id("abc123456789");	
		builder.setStart_station_id(1);
		builder.setEnd_station_id(3);
		builder.setStart_device_is_robot(true);		
		builder.setEnd_device_is_robot(false);
		builder.setStatus_id(2);
		builder.setPick_up_lat(-120.0);
		builder.setPick_up_lon(30.0);
		builder.setDeliver_location_lat(-100.0);
		builder.setDeliver_location_lon(10.0);
		builder.setTimestamp(Timestamp.valueOf("2020-08-30 00:00:00"));
	
		s.createOrder(builder.build());
	}

}
