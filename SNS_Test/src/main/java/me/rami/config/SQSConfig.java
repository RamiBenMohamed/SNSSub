package me.rami.config;
import javax.annotation.PostConstruct;
import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import com.amazon.sqs.javamessaging.AmazonSQSMessagingClientWrapper;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;

@Configuration
public class SQSConfig {
	
	SQSConnectionFactory connectionFactory;
	SQSConnection connection;
	AmazonSQSMessagingClientWrapper client;
	
	@Bean
	public AWSCredentials getCredentials() {
		
		
		AWSCredentials credentials = null;
	    try {
	        credentials = new ProfileCredentialsProvider().getCredentials();
	    } catch (Exception e) {
	        throw new AmazonClientException(
	                "Cannot load the credentials from the credential profiles file. " +
	                "Please make sure that your credentials file is at the correct " +
	                "location (~/.aws/credentials), and is in valid format.",
	                e);
	    }
	    
	    @SuppressWarnings("deprecation")
		AWSSecurityTokenServiceClient stsClient = new  AWSSecurityTokenServiceClient(credentials);
	    AssumeRoleRequest assumeRequest = new AssumeRoleRequest()
	            .withRoleArn("arn:aws:iam::347970623729:role/dae_from_support")
	            .withDurationSeconds(3600)
	            .withRoleSessionName("blabla");

	    AssumeRoleResult assumeResult = stsClient.assumeRole(assumeRequest);
	    BasicSessionCredentials temporaryCredentials =
	            new BasicSessionCredentials(
	                        assumeResult.getCredentials().getAccessKeyId(),
	                        assumeResult.getCredentials().getSecretAccessKey(),
	                        assumeResult.getCredentials().getSessionToken());
	    return temporaryCredentials;
	    
	    
	
	
};




@SuppressWarnings("deprecation")
@PostConstruct
public void init() throws JMSException {
	this.connectionFactory =
			 SQSConnectionFactory.builder()
			 .withRegion(Region.getRegion(Regions.EU_CENTRAL_1))
			 .withAWSCredentialsProvider(new StaticCredentialsProvider(
					 getCredentials()
            ))
			 .build();
			// Create the connection.
			this.connection = connectionFactory.createConnection();
			 client = connection.getWrappedAmazonSQSClient();
			if (!client.queueExists("QueueR")) {
				System.out.println("client QueueR inexistant");
			 client.createQueue("QueueR");
			}
			if (!client.queueExists("QueueR")) {
				System.out.println("cleint QueueR inexistant");
				 client.createQueue("QueueR");
				}
}
	

	
	public AmazonSQSMessagingClientWrapper getClient(){
		return client;
	}
	
	public SQSConnection getConnection(){
		return this.connection;
	}
	
	

	
	
	

}
