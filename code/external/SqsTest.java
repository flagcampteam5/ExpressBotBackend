package external;

/**
 * this class is for demonstrating how to enqueue and dequeue task queues. 
 * Each station has its own unique task queue, i.e., "1", or "2", or "3".
 * Placing order RPC only need to call enqueue API.
 */


public class SqsTest {
	public static void main(String[] args) {
		// create a connection to three task queues
		TaskQConnection conn = new TaskQConnection();
		
		// store queue names
		String[] queuesName = {"1", "2", "3"};
		
		// use a for loop to write one task for each station
		for (int i = 0; i < 3; i++) {		
			// prepare one task record to enqueue
			String transporter = "robot";
			String orderId = "0" + i;
			// enqueue operation
			conn.enqueue(queuesName[0], transporter, orderId);			
		}
		
//		List<Message> messages = new ArrayList<>();
		// read one task from each queue
//		for (String queueName : queuesName) {
//			// dequeue operation
//			messages.add(conn.dequeue(queueName, "robot"));
//		}
//		
//		for (Message m : messages) {
//			// parse each message
//			System.out.println(m.body());
//		}		
	}

}
