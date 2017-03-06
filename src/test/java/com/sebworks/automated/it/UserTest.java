package com.sebworks.automated.it;

import static org.junit.Assert.*;

import org.junit.Test;

import com.sebworks.automated.it.entity.User;

public class UserTest {

	@Test
	public void dumbTest() {
		assertEquals("John", new User("John", "", "").getName());
	}

}
