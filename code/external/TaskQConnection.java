package external;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesResponse;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

public class TaskQConnection {

	private SqsClient sqsClient;

	public TaskQConnection() {
		try {
			sqsClient = SqsClient.builder().region(Region.US_WEST_2).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void enqueue(String queueName, String[] attributes, String m) {
		final Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
		messageAttributes.put("phase",
				MessageAttributeValue.builder().dataType("String").stringValue(attributes[0]).build());

		messageAttributes.put("toStation",
				MessageAttributeValue.builder().dataType("String").stringValue(attributes[1]).build());

		messageAttributes.put("orderId",
				MessageAttributeValue.builder().dataType("String").stringValue(attributes[2]).build());

		messageAttributes.put("bot1",
				MessageAttributeValue.builder().dataType("String").stringValue(attributes[3]).build());

		messageAttributes.put("bot2",
				MessageAttributeValue.builder().dataType("String").stringValue(attributes[4]).build());

		// search queue with filter
		ListQueuesRequest filterListRequest = ListQueuesRequest.builder().queueNamePrefix(queueName).build();

		ListQueuesResponse listQueuesFilteredResponse = sqsClient.listQueues(filterListRequest);
		// write one task (JSON or XML) into queue
		for (String url : listQueuesFilteredResponse.queueUrls()) {
			System.out.println("\nEnqueue for queue" + queueName);
			sqsClient.sendMessage(SendMessageRequest.builder().queueUrl(url).messageAttributes(messageAttributes)
					.messageBody(m).delaySeconds(0).build());
		}
		notifyAll();
	}

	public synchronized Message dequeue(String queueName, String bot) {
		// search queue with filter
		ListQueuesRequest filterListRequest = ListQueuesRequest.builder().queueNamePrefix(queueName).build();

		ListQueuesResponse listQueuesFilteredResponse = sqsClient.listQueues(filterListRequest);
		String url = listQueuesFilteredResponse.queueUrls().get(0);
		// read one task from queue
		System.out.println("\nDequeue for queue" + queueName);
		ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder().queueUrl(url)
				.maxNumberOfMessages(1).build();

		List<Message> messages = new ArrayList<>();
		while (messages.isEmpty()) {
			messages = sqsClient.receiveMessage(receiveMessageRequest).messages();
			if (messages.isEmpty()) {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if (phase == "1" && bot1 == "uav" || phase == "2" && bot2 == "uav") {
			return null;
		}

		// delete the task message from queue
		for (Message message : messages) {
			DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder().queueUrl(url)
					.receiptHandle(message.receiptHandle()).build();
			sqsClient.deleteMessage(deleteMessageRequest);
		}
		return messages.get(0);
	}

}
