package com.hhoj.judge.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertiesUtilTest {

	@Test
	public void test() {
		String os=PropertiesUtil.getParam("os");
		System.out.println(os);
	}

}
