package com.mbrdi.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
 
@Component
public class Producer {
	
	 @Autowired
	  private AmqpTemplate amqpTemplate;
	  
	  @Value("${jsa.rabbitmq.exchange}")
	  private String exchange;
	  
	  @Value("${jsa.rabbitmq.store.csv.routingkey}")
	  private String routingStoreCsvKey;
	  
	  @Value("${jsa.rabbitmq.update.csv.routingkey}")
	  private String routingUpdateCsvKey;
	  
	  @Value("${jsa.rabbitmq.store.xml.routingkey}")
	  private String routingStoreXmlKey;
	  
	  @Value("${jsa.rabbitmq.update.xml.routingkey}")
	  private String routingUpdateXmlKey;
	  
	  public void produceStoreCsvMsg(String msg){
	    amqpTemplate.convertAndSend(exchange, routingStoreCsvKey, msg);
	    System.out.println("Send msg = " + msg);
	  }
	  
	  public void produceUpdateCsvMsg(String msg){
		    amqpTemplate.convertAndSend(exchange, routingUpdateCsvKey, msg);
		    System.out.println("Send msg = " + msg);
	  }
	  public void produceStoreXmlMsg(String msg){
		    amqpTemplate.convertAndSend(exchange, routingStoreXmlKey, msg);
		    System.out.println("Send msg = " + msg);
	  }
		  
	  public void produceUpdateXmlMsg(String msg){
		    amqpTemplate.convertAndSend(exchange, routingUpdateXmlKey, msg);
		    System.out.println("Send msg = " + msg);
	  }
}
