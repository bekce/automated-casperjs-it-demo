package com.sebworks.automated.it;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.sebworks.automated.it.entity.User;
import com.sebworks.automated.it.repository.UserRepository;

/**
 * @author Selim Eren Bekçe
 */
@Component
public class TestLifecycleBean {

	private static final Logger logger = LoggerFactory.getLogger(TestLifecycleBean.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private UserRepository userRepository;

	@EventListener
	public void handleContextRefresh(ContextRefreshedEvent event) {
		logger.info("Importing data...");
		System.out.println("********************************");
		System.out.println("************IMPORT**************");
		System.out.println("********************************");

		userRepository.save(new User("John", "Doe", "+901112223344"));
		userRepository.save(new User("Jane", "Doe", "+909116667788"));
	}

	@EventListener
	public void handleContextClose(ContextClosedEvent event) {
		logger.info("Dropping database...");
		
		System.out.println("********************************");
		System.out.println("*************DROP***************");
		System.out.println("********************************");
		if ("demo-test".equals(mongoTemplate.getDb().getName())) {
			mongoTemplate.getDb().dropDatabase();
			logger.info("Dropped database: demo-test");
		}
	}

}
