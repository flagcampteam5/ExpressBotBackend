package external;

import java.util.List;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlResponse;
import software.amazon.awssdk.services.sqs.model.ListQueuesRequest;
import software.amazon.awssdk.services.sqs.model.ListQueuesResponse;
import software.amazon.awssdk.services.sqs.model.PurgeQueueRequest;

public class TaskQCreation {
	// Run this as Java application to reset one queue for each station.
	public static void main(String[] args) {
	     try {
	    	 // Init sqs client
	    	 String[] queuesName = {"1", "2", "3"};
			 SqsClient sqsClient = SqsClient.builder()
			         .region(Region.US_WEST_2)
			         .build();

			 // Create one queue for each station if not exist
			 for (String queueName : queuesName) {
			     ListQueuesRequest filterListRequest = ListQueuesRequest.builder()
			             .queueNamePrefix(queueName).build();

			     ListQueuesResponse listQueuesFilteredResponse = sqsClient.listQueues(filterListRequest);
			     List<String> urls = listQueuesFilteredResponse.queueUrls();
			     if (urls.isEmpty()) {
			    	 System.out.println("\nCreate queue" + queueName);
			    	 createQueue(sqsClient, queueName);
			     } else {
			    	 PurgeQueueRequest purgeQueueRequest = PurgeQueueRequest.builder()
				             .queueUrl(urls.get(0)).build(); 
			    	 sqsClient.purgeQueue(purgeQueueRequest);
			     }
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	
	private static String createQueue(SqsClient sqsClient,String queueName) {
		
	     CreateQueueRequest createQueueRequest = CreateQueueRequest.builder()
	             .queueName(queueName)
	             .build();
	     sqsClient.createQueue(createQueueRequest);	     
	     GetQueueUrlResponse getQueueUrlResponse =
	             sqsClient.getQueueUrl(GetQueueUrlRequest.builder().queueName(queueName).build());
	     String queueUrl = getQueueUrlResponse.queueUrl();
	     return queueUrl;
	 }

}
