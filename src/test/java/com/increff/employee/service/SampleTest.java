package com.increff.employee.service;

import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.junit.Test;

public class SampleTest {

	@Test
	public void testFiles() {
		InputStream is = null;
		is = SampleTest.class.getResourceAsStream("/com/increff/employee/Brand_Sample.tsv");
		assertNotNull(is);
		is = SampleTest.class.getResourceAsStream("/com/increff/employee/Product_Sample.tsv");
		assertNotNull(is);
		is = SampleTest.class.getResourceAsStream("/com/increff/employee/Inventory_Sample.tsv");
		assertNotNull(is);
	}

}
