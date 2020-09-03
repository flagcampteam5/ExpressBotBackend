package robot;

/**
 * This class is for creating drone threads.
 */

import software.amazon.awssdk.services.sqs.model.Message;

import external.TaskQConnection;
import db.MySQLConnection;

public class Drone extends Thread implements Runnable {
	String station;
	String id;
	TaskQConnection conn;

	public Drone(String station) {
		super();
		this.station = station;
		this.id = "drone" + System.currentTimeMillis();
		this.conn = new TaskQConnection();
	}

	@Override
	public void run() {
		// System.out.println("working");
		while (true) {
			Message message = conn.dequeue(station, "drone");
			String order_id;
			if (message != null) {
				order_id = message.body().substring(5);
				
				// update status when working
				int numStates = 19;
				for (int i = 0; i < numStates; i++) {
					// move forward in 2000 milliseconds
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// update delivery status_id in DB 
					MySQLConnection dbConnection = new MySQLConnection();
					dbConnection.updateOrderStatus(order_id, i + 1);
					dbConnection.close();
					System.out.println(order_id + " : " + (i + 1));
				}				
			}
		}
	}
}
