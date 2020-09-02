package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import entity.TransactionItem;
import entity.TransactionItem.TransactionItemBuilder;

public class MySQLConnection {
	protected Connection conn;

	public MySQLConnection() {
		try {
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
				builder.setStation_id(rs.getInt("Station_id"));
				builder.setStart_device_is_robot(rs.getBoolean("start_device_is_robot"));
				builder.setEnd_device_is_robot(rs.getBoolean("end_device_is_robot"));
				builder.setStatus_id(rs.getInt("status_id"));
				builder.setPick_up_lat(rs.getDouble("pick_up_lat"));
				builder.setPick_up_lon(rs.getDouble("pick_up_lon"));
				builder.setDeliver_location_lat(rs.getDouble("deliver_location_lat"));
				builder.setDeliver_location_lon(rs.getDouble("deliver_location_lon"));
				builder.setTimestamp(rs.getInt("timestamp"));
				item = builder.build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}

	public void updateOrderStatus(String transId, int newStatus_id) {
		if(conn == null) {
			System.err.println("DB connection failed");
			return;
		}
		String sql = "UPDATE transactions SET status_id = newStatus_id WHERE trans_id = ?";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1,newStatus_id);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createOrder(TransactionItem item) {
		if(conn == null) {
			System.err.println("DB connection failed");
			return;
		}
		String sql = "INSERT IGNORE INTO items VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, item.getTrans_id());
			statement.setInt(2, item.getStation_id());
			statement.setBoolean(3, item.isStart_device_is_robot());
			statement.setBoolean(4, item.isEnd_device_is_robot());
			statement.setInt(5, item.getStatus_id());
			statement.setDouble(6, item.getPick_up_lat());
			statement.setDouble(7, item.getPick_up_lon());
			statement.setDouble(8, item.getDeliver_location_lat());
			statement.setDouble(9, item.getDeliver_location_lon());
			statement.setInt(10, item.getTimestamp());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	//test ///////////////////////////////////
	public static void main(String[] args) {
		MySQLConnection s = new MySQLConnection();
		TransactionItemBuilder builder = new TransactionItemBuilder();
		
		builder.setTrans_id("abc123456789");	
		builder.setStation_id(1);
		builder.setStart_device_is_robot(true);		
		builder.setEnd_device_is_robot(false);
		builder.setStatus_id(2);
		builder.setPick_up_lat(-120.0);
		builder.setPick_up_lon(30.0);
		builder.setDeliver_location_lat(-100.0);
		builder.setDeliver_location_lon(10.0);
		builder.setTimestamp(123456789);
	
		s.createOrder(builder.build());
	}

}
