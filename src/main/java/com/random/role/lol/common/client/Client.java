package com.random.role.lol.common.client;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

public abstract class Client {

	protected ResteasyClient client;

	@Value("${connect-timeout}")
	private long connectTimeout;

	@Value("${read-timeout}")
	private long readTimeout;

	@PostConstruct
	public void init() {
		client = ((ResteasyClientBuilder) ResteasyClientBuilder.newBuilder()).connectionPoolSize(1000)
				.maxPooledPerRoute(500)
				.readTimeout(readTimeout, TimeUnit.MILLISECONDS)
				.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
				.build();
	}

	@PreDestroy
	public void destroy() {
		if (!client.isClosed())
			client.close();
	}

	public abstract <T> T proxy(Class<? extends T> clazz);

}
