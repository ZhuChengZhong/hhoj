package com.hhoj.judger.docker.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContainerBlockingPool implements BlockingPool<String> {
	private static Logger logger = LoggerFactory.getLogger(ContainerBlockingPool.class);

	private static final Integer DEFAULT_POOL_SIZE = 10;

	private ArrayBlockingQueue<String> containerBlockingQueue;

	private ObjectFactory<String> objectFactory;

	private Validator<String> validator;

	private volatile boolean isShutdown;

	public ContainerBlockingPool(ObjectFactory<String> objectFactory, Validator<String> validator) {
		this(DEFAULT_POOL_SIZE, objectFactory, validator);
	}

	public ContainerBlockingPool(int capacity, ObjectFactory<String> objectFactory, Validator<String> validator) {
		containerBlockingQueue = new ArrayBlockingQueue<String>(capacity);
		this.objectFactory = objectFactory;
		this.validator = validator;
		initContainer(capacity);
	}

	private void initContainer(int capacity) {
		try {
			for (int i = 0; i < capacity; i++) {
				String containerId = objectFactory.create();
				containerBlockingQueue.put(containerId);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void release(String containerId) {
		if (isShutdown) {
			validator.invalidate(containerId);
			return;
		}
		String returnContainerId = containerId;
		if (!validator.isValid(containerId)) {
			validator.invalidate(containerId);
			returnContainerId = objectFactory.create();
		}
		try {
			containerBlockingQueue.put(returnContainerId);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void shutdown() {
		isShutdown = true;
		while (!containerBlockingQueue.isEmpty()) {
			String containerId = containerBlockingQueue.poll();
			validator.invalidate(containerId);
		}
	}

	public String get(int time, TimeUnit timeUnit) {
		if (!isShutdown) {
			String res = null;
			try {
				containerBlockingQueue.poll(time, timeUnit);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return res;
		}
		throw new RuntimeException("容器池已经关闭！！");
	}

	public String get() {
		if (!isShutdown) {
			String res = null;
			try {
				res = containerBlockingQueue.take();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return res;
		}
		throw new RuntimeException("容器池已经关闭！！");
	}

}
