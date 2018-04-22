package com.enoxs.verify;

import static org.junit.Assert.*;

import com.enoxs.utillity.Calculator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCalculator {
	private static Logger log = Logger.getLogger(TestCalculator.class);
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	Calculator cal = new Calculator();
	String result,target;
	byte inByte;
	@Test
	public void testCal(){
		inByte = 0x1A;
		target = "1A"; 
		result = cal.BytesToHexString(inByte);
		log.info(result);
		assertEquals(target,result);
	}
}
