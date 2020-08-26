package robot;

/**
 * This class is for creating robot threads.
 */

import external.TaskQConnection;
import software.amazon.awssdk.services.sqs.model.Message;
import util.ParcelTransporter;

public class Robot extends Thread implements Runnable {
	String station;
	String id;
	TaskQConnection conn;
	public Robot(String station) {
		super();
		this.station = station;
		this.id = "robot" + System.currentTimeMillis();
		this.conn = new TaskQConnection();
	}

	@Override
	public void run() {
		// System.out.println("working");
		while (true) {
			Message message = conn.dequeue(station, "robot");
			// parse message
			if (message != null) {
				if (phase.equals("1")) {
					// change delivery state in DB to [to pick up]
					
					// moving uses 2000 milliseconds
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// change delivery state in DB to [picking up]
					
					// preparation uses 1000 milliseconds
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// change delivery state in DB to [returning to Station a]
					
					// returning uses 2000 milliseconds
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// change delivery state in DB to [arrived at Station a]
					
					// call a transporter
					new Thread(new ParcelTransporter(attributes, message.body())).start();
				} else {
					// change delivery state in DB to [to deliver]
					
					// moving uses 2000 milliseconds
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// change delivery state in DB to [arrived]
				}
			} 
		}
	}
	
}
