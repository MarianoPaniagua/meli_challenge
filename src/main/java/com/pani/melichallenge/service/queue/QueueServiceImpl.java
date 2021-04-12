package com.pani.melichallenge.service.queue;

import javax.jms.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pani.melichallenge.service.access.AccessService;
import com.pani.melichallenge.web.bean.GeoInfoBean;

@Service
public class QueueServiceImpl implements QueueService {

	private static final Logger logger = LoggerFactory.getLogger(QueueServiceImpl.class);

	private JmsTemplate jmsTemplate;
	private Queue queue;
	private AccessService accessService;
	private static ObjectMapper mapper = new ObjectMapper();

	@Autowired
	public QueueServiceImpl(JmsTemplate jmsTemplate, Queue queue, AccessService accessService) {
		super();
		this.jmsTemplate = jmsTemplate;
		this.queue = queue;
		this.accessService = accessService;
	}

	@Override
	public void sendMessage(String message) {
		logger.info("Sending message to the queue");
		jmsTemplate.convertAndSend(queue, message);
		logger.info("Message sent to the queue");
	}

	@JmsListener(destination = "meli_challenge.queue")
	public void consume(String message) {
		GeoInfoBean bean = new GeoInfoBean();
		try {
			bean = mapper.readValue(message, new TypeReference<GeoInfoBean>() {
			});
		} catch (Exception e) {
			logger.error("Error trying to parse message from queue", e);
		}
		accessService.storeNewCall(bean.getIso_code(), bean.getDistance(), bean.getCountry());
		logger.info("Message recieved. Stored data in db");

	}
}
