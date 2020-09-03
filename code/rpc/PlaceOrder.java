package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import db.MySQLConnection;
import entity.TransactionItem;
import entity.TransactionItem.TransactionItemBuilder;
import external.TaskQConnection;

/**
 * Servlet implementation class PlaceOrder
 */
public class PlaceOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public PlaceOrder() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// data model from front-end, need to convert it to data model for DB
//		final String[] ATTRIBUTES = new String[]{"pickUpLat","pickUpLng","destinationLat",
//		           "destinationLng", "order_id", "station_id", "isRobot", "timestamp"};
		
		TransactionItemBuilder transactionItemBuilder = new TransactionItem.TransactionItemBuilder();
		
		// read order_info from HTTP body
		JSONObject input = new JSONObject(IOUtils.toString(request.getReader()));
		
		transactionItemBuilder.setDeliver_location_lat(input.getDouble("destinationLat"));
		transactionItemBuilder.setDeliver_location_lon(input.getDouble("destinationLng"));		
		transactionItemBuilder.setEnd_device_is_robot(input.getBoolean("isRobot"));
		transactionItemBuilder.setStart_device_is_robot(input.getBoolean("isRobot"));
		transactionItemBuilder.setPick_up_lat(input.getDouble("pickUpLat"));
		transactionItemBuilder.setPick_up_lon(input.getDouble("pickUpLng"));		
		transactionItemBuilder.setStation_id(input.getInt("station_id"));
		transactionItemBuilder.setStatus_id(0);		
		transactionItemBuilder.setTimestamp(input.getInt("timestamp"));
		transactionItemBuilder.setTrans_id(input.getString("order_id"));
		
		// insert a task into task_queue based on station
		TaskQConnection queueConnection = new TaskQConnection();
		String botType = input.getBoolean("isRobot") ? "robot" : "drone";
		queueConnection.enqueue(String.valueOf(input.getInt("station_id")), botType, input.getString("order_id"));
		
		// add an order_item into DB 
		MySQLConnection connection = new MySQLConnection();
		JSONObject obj = new JSONObject();
		
		connection.createOrder(transactionItemBuilder.build());
		obj.put("status", "OK");
		
		connection.close();
		
		// return status for front-end client
		RpcHelper.writeJsonObject(response, obj);
	}

}
