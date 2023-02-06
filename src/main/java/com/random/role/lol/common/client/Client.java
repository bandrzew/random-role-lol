package com.random.role.lol.common.client;

import java.util.concurrent.TimeUnit;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.springframework.beans.factory.annotation.Value;

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
