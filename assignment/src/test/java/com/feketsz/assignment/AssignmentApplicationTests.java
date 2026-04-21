package com.feketsz.assignment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AssignmentApplicationTests {

	@Test
	void applicationClassLoads() {
		assertNotNull(AssignmentApplication.class);
	}

}
