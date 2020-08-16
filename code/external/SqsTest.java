package external;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import entity.Item;
import entity.Item.ItemBuilder;
import software.amazon.awssdk.services.sqs.model.Message;


public class SqsTest {
	public static void main(String[] args) {
		TaskQConnection conn = new TaskQConnection();
		String[] queuesName = {"a", "b", "c"};
		
		for (int i = 0; i < 3; i++) {
			Set<String> addressFrom = new HashSet<>();
			addressFrom.add("Fromlongitude" + i);
			addressFrom.add("Fromlatitude" + i);
			Set<String> addressTo = new HashSet<>();
			addressTo.add("Tolongitude" + i);
			addressTo.add("Tolatitude" + i);
			
			Item item = new ItemBuilder()
					.setItemId("" + i)
					.setAddressFrom(addressFrom)
					.setAddressTo(addressTo)
					.setParcelSender("Sender" + i)
					.setParcelReceiver("Receiver" + i)
					.setBot1("robot")
					.setBot2("uav")
					.setPhase("" + (i % 2 + 1))
					.build();
			
			conn.enqueue(queuesName[i], item.toJSONObject());
			
		}
		
		List<Message> messages = new ArrayList<>();
		for (String queueName : queuesName) {
			messages.add(conn.dequeue(queueName));
		}
		
		for (Message m : messages) {
			System.out.println(m.body());
		}		
	}

}
