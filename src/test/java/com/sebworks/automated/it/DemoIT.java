package com.sebworks.automated.it;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.raonifn.casperjs.junit.CasperEnvironment;
import com.github.raonifn.casperjs.junit.CasperRunner;
import com.sebworks.automated.it.entity.User;
import com.sebworks.automated.it.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class DemoIT {

	@Test
	public void casperJS() throws Exception {
		CasperIT.countDownLatch();
		JUnitCore.runClasses(CasperIT.class);
	}

	@Configuration
	public static class TestConfig extends DemoApplication {
		private static final Logger logger = LoggerFactory.getLogger(TestConfig.class);
		
		@Bean
		public TestBean testBean(){
			return new TestBean();
		}
		
		public static class TestBean {
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

	}

	@RunWith(CasperRunner.class)
	public static class CasperIT {

		private static final CountDownLatch latch = new CountDownLatch(1);

		/**
		 * The latch must be counted down after server init.
		 */
		public static void countDownLatch() {
			latch.countDown();
		}

		/**
		 * Ensures spring context is initialized before this class.
		 */
		@BeforeClass
		public static void beforeClass() throws Exception {
			latch.await(5, TimeUnit.MINUTES);
		}

		@CasperEnvironment
		public Map<String, String> env() {
			Map<String, String> map = new HashMap<>();
			map.put("START_URL", "http://localhost:45000");
			return map;
		}
	}
}
