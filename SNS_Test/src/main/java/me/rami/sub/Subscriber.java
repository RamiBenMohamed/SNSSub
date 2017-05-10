package me.rami.sub;


import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Queue;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import com.amazonaws.services.sns.model.*;

import me.rami.config.SNSConfig;
import me.rami.config.SQSConfig;
@SuppressWarnings("restriction")
@Configuration
@Component
public class Subscriber {
	@Autowired
	SQSConfig sqs;
	@Autowired
	msgListener TestListener;
	@Autowired
	SNSConfig sns;
	String subscriptionArn;
	
	Session session;
	
	
	
	
	public void inscription() throws JMSException{
		SubscribeResult sResult = sns.getSnsClient().subscribe(sns.getArn(),"sqs", "arn:aws:sqs:eu-central-1:347970623729:QueueR");
		subscriptionArn = sResult.getSubscriptionArn();
		
	}
	
	
	 @PostConstruct
	public void recieveMessage(){
		 
		// start your monitoring in here
			
				
				try {
					
					inscription();
					session = sqs.getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
					Queue queue = session.createQueue("QueueR");
			        MessageConsumer consumer = session.createConsumer(queue);
			        consumer.setMessageListener(TestListener);
			        sqs.getConnection().start();
					
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("JMS Message a l'ecoute " );
	}

}
