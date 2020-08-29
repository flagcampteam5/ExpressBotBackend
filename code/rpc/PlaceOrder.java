package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import db.MySQLConnection;
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
		final String[] ATTRIBUTES = new String[]{"order_id", "bot", "station"};
		String[] orderParams;
		
		// read order_info from HTTP body
		JSONObject input = new JSONObject(IOUtils.toString(request.getReader()));
		for (int i = 0; i < ATTRIBUTES.length; i++) {
			orderParams[i] = input.getString(ATTRIBUTES[i]);
		}
		
		// insert a task into task_queue based on station
		TaskQConnection queueConnection = new TaskQConnection();
		queueConnection.enqueue(orderParams[2], orderParams[1], orderParams[0]);
		
		// add an order_item into DB 
		MySQLConnection connection = new MySQLConnection();
		JSONObject obj = new JSONObject();
		if (connection.addOrder(orderParams)) {
			obj.put("status", "OK");
		} else {
			obj.put("status", "Failed");
		}
		connection.close();
		
		// return status for front-end client
		RpcHelper.writeJsonObject(response, obj);
	}

}
