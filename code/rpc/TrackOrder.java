package rpc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import db.MySQLConnection;
import entity.Item;

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
		Map<String, String> orderInfo = connection.getOrderItem(orderId);
		connection.close();
		
		// convert data type returned by DB_client to a JSON object
		JSONObject obj = new JSONObject();
		obj.put("station", new String[]{orderInfo.get("stationLat"),orderInfo.get("stationLon")});
		obj.put("start", new String[]{orderInfo.get("startLat"),orderInfo.get("startLon")});
		obj.put("end", new String[]{orderInfo.get("endLat"),orderInfo.get("endLon")});
		obj.put("mid1", new String[]{orderInfo.get("mid1Lat"),orderInfo.get("mid1Lon")});
		obj.put("mid2", new String[]{orderInfo.get("mid2Lat"),orderInfo.get("mid2Lon")});
		obj.put("orderState", orderInfo.get("orderState"));
		
		// return a JSON object for front-end client
		RpcHelper.writeJsonObject(response, obj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
