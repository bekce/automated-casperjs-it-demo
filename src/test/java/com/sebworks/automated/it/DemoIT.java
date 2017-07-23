package com.sebworks.automated.it;

import com.github.raonifn.casperjs.junit.CasperEnvironment;
import com.github.raonifn.casperjs.junit.CasperRunner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * This is an integration test class (since its name ends with 'IT').
 * It starts a Spring application context.
 * 
 * @author Selim Eren Bekçe
 */
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DemoIT {
	
	@LocalServerPort
	private int serverPort;

	@Test
	public void casperJS() throws Exception {
		CasperIT.serverPort = this.serverPort;
		CasperIT.countDownLatch();
		// Below part makes sure the errors in child runner is propagated correctly to this runner
		JUnitCore jUnitCore = new JUnitCore();
		jUnitCore.addListener(new RunListener(){
			@Override
			public void testFailure(Failure failure) throws Exception {
				Assert.fail(failure.getMessage());
			}
		});
		jUnitCore.run(CasperIT.class);
	}

	@RunWith(CasperRunner.class)
	public static class CasperIT {

		private static final CountDownLatch latch = new CountDownLatch(1);
		private static int serverPort;

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
			map.put("START_URL", "http://localhost:"+serverPort);
			return map;
		}
	}
}
