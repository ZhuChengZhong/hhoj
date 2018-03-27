package com.hhoj.judger.pool;

import java.sql.Connection;
import java.util.concurrent.TimeUnit;

public class Main {
	public static void main(String[] args) {
		ConnectionBlockingPool pool = new ConnectionBlockingPool(1, new ConnectionValidator(),
				new ConnectionFactory("jdbc:mysql://localhost:3306/hhoj", "root", "com.mysql.jdbc.Driver","121314" ));
		Connection con1=pool.get();
		Connection con2=pool.get(1,TimeUnit.SECONDS);
		System.out.println("----------");
		pool.release(con1);
		pool.release(con2);
		pool.shutdown();
	}
}
