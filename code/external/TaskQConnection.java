package external;

import java.util.List;

import org.json.JSONObject;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesResponse;
import software.amazon.awssdk.services.sqs.model.Message;
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

	public void enqueue(String queueName, JSONObject obj) {
		// search queue with filter
		ListQueuesRequest filterListRequest = ListQueuesRequest.builder().queueNamePrefix(queueName).build();

		ListQueuesResponse listQueuesFilteredResponse = sqsClient.listQueues(filterListRequest);
		// write one task (JSON or XML) into queue
		for (String url : listQueuesFilteredResponse.queueUrls()) {
			System.out.println("\nEnqueue for queue" + queueName);
			sqsClient.sendMessage(
					SendMessageRequest.builder().queueUrl(url).messageBody(obj.toString()).delaySeconds(0).build());
		}
	}

	public synchronized Message dequeue(String queueName) {
		// search queue with filter
		ListQueuesRequest filterListRequest = ListQueuesRequest.builder().queueNamePrefix(queueName).build();

		ListQueuesResponse listQueuesFilteredResponse = sqsClient.listQueues(filterListRequest);
		String url = listQueuesFilteredResponse.queueUrls().get(0);
		// read one task from queue
		System.out.println("\nDequeue for queue" + queueName);
		ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder().queueUrl(url)
				.maxNumberOfMessages(1).build();
		List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).messages();

		// delete the task message from queue
		for (Message message : messages) {
			DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder().queueUrl(url)
					.receiptHandle(message.receiptHandle()).build();
			sqsClient.deleteMessage(deleteMessageRequest);
		}
		return messages.get(0);
	}

}
