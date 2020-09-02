package rpc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONObject;

import db.MySQLConnection;
import entity.TransactionItem;

/**
 * Servlet implementation class TrackOrder
 */
public class TrackOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TrackOrder() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// read order_id from parameter part of an URL
		String orderId = request.getParameter("order_id");
		
		// query DB to fetch the corresponding order_state
		MySQLConnection connection = new MySQLConnection();
		TransactionItem orderInfo = connection.getOrder(orderId);
		connection.close();
		
		// convert data type returned by DB_client to a JSON object
		JSONObject obj = new JSONObject();
		obj.put("pickUpLat", new String[]{Double.toString(orderInfo.getPick_up_lat())});
		obj.put("pickUpLng", new String[]{Double.toString(orderInfo.getPick_up_lon())});		
		obj.put("destinationLat", new String[]{Double.toString(orderInfo.getDeliver_location_lat())});
		obj.put("destinationLng", new String[]{Double.toString(orderInfo.getDeliver_location_lon())});	
		obj.put("station_id", new String[]{Integer.toString(orderInfo.getStation_id())});
		obj.put("status_id", new String[]{Integer.toString(orderInfo.getStatus_id())});
		obj.put("isRobot", new String[]{Boolean.toString(orderInfo.isStart_device_is_robot())});
				
		// return a JSON object for front-end client
		RpcHelper.writeJsonObject(response, obj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
