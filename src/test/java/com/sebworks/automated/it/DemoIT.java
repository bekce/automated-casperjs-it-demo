package com.sebworks.automated.it;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.raonifn.casperjs.junit.CasperEnvironment;
import com.github.raonifn.casperjs.junit.CasperRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class DemoIT {

	@Test
	public void casperJS() throws Exception {
		CasperIT.countDownLatch();
		JUnitCore.runClasses(CasperIT.class);
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
