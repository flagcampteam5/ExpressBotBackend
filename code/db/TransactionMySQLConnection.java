package db;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import util.RandomString;
import entity.TransactionItem;

public class TransactionMySQLConnection extends MySQLConnection {
	public String insertTransactionRecord(TransactionItem transactionItem) {
		if (conn == null) { ////
			System.err.println("DB connection failed");
			return "";
		}
		
		String trans_id="";
		try {
			String sql = "INSERT INTO users VALUE(?,?,?,?,?,?,?,?,?,?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
//			trans_id = "TransId_" + RandomString.getAlphaNumericString(15);			
			
			stmt.setString(1, transactionItem.getTrans_id());
			stmt.setLong(2, transactionItem.getStart_station_id());
			stmt.setLong(3, transactionItem.getEnd_station_id());
			stmt.setBoolean(4, transactionItem.isStart_device_is_robot());
			stmt.setBoolean(5, transactionItem.isEnd_device_is_robot());
			stmt.setLong(6, transactionItem.getStatus_id());
			stmt.setDouble(7, transactionItem.getPick_up_lat());
			stmt.setDouble(8, transactionItem.getPick_up_lon());
			stmt.setDouble(9, transactionItem.getDeliver_location_lat());
			stmt.setDouble(10, transactionItem.getDeliver_location_lon());
			stmt.setTimestamp(11, transactionItem.getTimestamp());
			
			stmt.execute();			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return trans_id;
		
	}

}
