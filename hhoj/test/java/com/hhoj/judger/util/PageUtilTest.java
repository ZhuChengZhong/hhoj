package com.hhoj.judger.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.hhoj.judger.entity.PageBean;

public class PageUtilTest {

	@Test
	public void test() {
		String pagination=PageUtil.getPagination("user/list", new PageBean(10, 4, 300));
		System.out.println(pagination);
	}

}
