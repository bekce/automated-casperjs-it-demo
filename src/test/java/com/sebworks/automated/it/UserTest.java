package com.sebworks.automated.it;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sebworks.automated.it.entity.User;

/**
 * This is an unit test class (since its name ends with 'Test').
 * It will be run in 'test' phase on Maven, with no Spring context. 
 * 
 * @author Selim Eren Bekçe
 */
public class UserTest {

	@Test
	public void dumbTest() {
		assertEquals("John", new User("John", "", "").getName());
	}

}
