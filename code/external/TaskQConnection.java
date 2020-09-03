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

	public synchronized void enqueue(String queueName, String bot, String orderId) {
		final Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();

		messageAttributes.put("transporter",
				MessageAttributeValue.builder().dataType("String").stringValue(bot).build());

		// search queue with filter
		ListQueuesRequest filterListRequest = ListQueuesRequest.builder().queueNamePrefix(queueName).build();

		ListQueuesResponse listQueuesFilteredResponse = sqsClient.listQueues(filterListRequest);
		// write one task (JSON or XML) into queue
		for (String url : listQueuesFilteredResponse.queueUrls()) {
			System.out.println("\nEnqueue for queue" + queueName);
			sqsClient.sendMessage(SendMessageRequest.builder().queueUrl(url).messageAttributes(messageAttributes)
					.messageBody(bot.concat(orderId)).delaySeconds(0).build());
		}
		// notifyAll();
	}

	public synchronized Message dequeue(String queueName, String bot) {
		// search queue with filter
		ListQueuesRequest filterListRequest = ListQueuesRequest.builder().queueNamePrefix(queueName).build();

		ListQueuesResponse listQueuesFilteredResponse = sqsClient.listQueues(filterListRequest);
		String url = listQueuesFilteredResponse.queueUrls().get(0);
		// read one task from queue
		// System.out.println("\nDequeue for queue" + queueName);
		ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder().queueUrl(url)
				.maxNumberOfMessages(1).build();

		List<Message> messages = new ArrayList<>();
		while (messages.isEmpty()) {
			messages = sqsClient.receiveMessage(receiveMessageRequest).messages();
			if (messages.isEmpty()) {
				try {
					// wait();
					return null;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		Message task = messages.get(0);
		// System.out.println(task.body());
		if (!task.body().substring(0,5).equals(bot)) {
			return null;
		}

		// delete the task message from queue
		for (Message message : messages) {
			DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder().queueUrl(url)
					.receiptHandle(message.receiptHandle()).build();
			sqsClient.deleteMessage(deleteMessageRequest);
		}
		return task;
	}

}
