package external;

/**
 * this class is for demonstrating how to enqueue and dequeue task queues. 
 * Each station has its own unique task queue, i.e., "a", or "b", or "c".
 * Placing order RPC only need to call enqueue API.
 * enqueue(String, JSONObject).
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entity.Item;
import entity.Item.ItemBuilder;
import software.amazon.awssdk.services.sqs.model.Message;

public class SqsTest {
	public static void main(String[] args) {
		// create a connection to three task queues
		TaskQConnection conn = new TaskQConnection();
		
		// store queue names
		String[] queuesName = {"a", "b", "c"};
		
		// use a for loop to write one task for each station
		for (int i = 0; i < 3; i++) {
			// set lat and lng for start address
			Set<String> addressFrom = new HashSet<>();
			addressFrom.add("Fromlongitude" + i);
			addressFrom.add("Fromlatitude" + i);
			
			// set lat and lng for destination address
			Set<String> addressTo = new HashSet<>();
			addressTo.add("Tolongitude" + i);
			addressTo.add("Tolatitude" + i);
			
			// prepare one task record to enqueue
			Item item = new ItemBuilder()
					.setItemId("" + i)
					.setAddressFrom(addressFrom)
					.setAddressTo(addressTo)
					.setParcelSender("Sender" + i)
					.setParcelReceiver("Receiver" + i)
					.setStation1(queuesName[i])
					.setStation2(queuesName[(i + 1) % 3])
					.setBot1("robot")
					.setBot2("uav")
					.setPhase("" + (i % 2 + 1))
					.build();
			
			// enqueue operation
			String[] attributes = new String[] {item.getPhase(), item.getStation2(),
					item.getItemId(), item.getBot1(), item.getBot2()};
			conn.enqueue(queuesName[i], attributes, item.toJSONObject().toString());
			
		}
		
		List<Message> messages = new ArrayList<>();
		// read one task from each queue
		for (String queueName : queuesName) {
			// dequeue operation
			messages.add(conn.dequeue(queueName, "robot"));
		}
		
		for (Message m : messages) {
			// parse each message
			System.out.println(m.body());
		}		
	}

}
