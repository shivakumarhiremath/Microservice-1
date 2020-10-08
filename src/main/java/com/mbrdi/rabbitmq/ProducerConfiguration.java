package com.mbrdi.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfiguration {

	@Value("${jsa.rabbitmq.store.csv.queue}")
	String queueStoreCsv;

	@Value("${jsa.rabbitmq.update.csv.queue}")
	String queueUpdateCsv;

	@Value("${jsa.rabbitmq.store.xml.queue}")
	String queueStoreXml;

	@Value("${jsa.rabbitmq.update.xml.queue}")
	String queueUpdateXml;

	@Value("${jsa.rabbitmq.exchange}")
	String exchange;

	@Value("${jsa.rabbitmq.store.csv.routingkey}")
	private String routingStoreCsvkey;

	@Value("${jsa.rabbitmq.update.csv.routingkey}")
	private String routingUpdateCsvkey;

	@Value("${jsa.rabbitmq.store.xml.routingkey}")
	private String routingStoreXmlkey;

	@Value("${jsa.rabbitmq.update.xml.routingkey}")
	private String routingUpdateXmlkey;

	@Bean
	Queue queueStoreCsv() {
		return new Queue(queueStoreCsv, true,false,false);
	}

	@Bean
	Queue queueUpdateCsv() {
		return new Queue(queueUpdateCsv, true,false,false);
	}

	@Bean
	Queue queueStoreXml() {
		return new Queue(queueStoreXml, true,false,false);
	}

	@Bean
	Queue queueUpdateXml() {
		return new Queue(queueUpdateXml, true,false,false);
	}

	@Bean 
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding bindingStoreCsv(Queue queueStoreCsv, DirectExchange exchange) {
		return BindingBuilder.bind(queueStoreCsv).to(exchange).with(routingStoreCsvkey);
	}

	@Bean
	Binding bindingUpdateCsv(Queue queueUpdateCsv, DirectExchange exchange) {
		return BindingBuilder.bind(queueUpdateCsv).to(exchange).with(routingUpdateCsvkey);
	}

	@Bean
	Binding bindingStoreXml(Queue queueStoreXml, DirectExchange exchange) {
		return BindingBuilder.bind(queueStoreXml).to(exchange).with(routingStoreXmlkey);
	}

	@Bean
	Binding bindingUpdateXml(Queue queueUpdateXml, DirectExchange exchange) {
		return BindingBuilder.bind(queueUpdateXml).to(exchange).with(routingUpdateXmlkey);
	}

}
