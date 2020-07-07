package com.random.role.lol.client;

import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ws.rs.client.ClientBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient43Engine;
import org.springframework.beans.factory.annotation.Value;

public abstract class Client {

	protected ResteasyClient client;

	@Value("${connect-timeout}")
	private long connectTimeout;

	@Value("${read-timeout}")
	private long readTimeout;

	@PostConstruct
	public void init() {
		CloseableHttpClient httpClient = HttpClientBuilder.create().setMaxConnPerRoute(20).setMaxConnTotal(100).build();
		ApacheHttpClient43Engine engine = new ApacheHttpClient43Engine(httpClient);
		client = ((ResteasyClientBuilder) ClientBuilder.newBuilder()).httpEngine(engine)
				.connectTimeout(this.connectTimeout, TimeUnit.MILLISECONDS)
				.readTimeout(this.readTimeout, TimeUnit.MILLISECONDS)
				.build();
	}

	@PreDestroy
	public void destroy() {
		if (!client.isClosed())
			client.close();
	}

	public abstract <T> T proxy(Class<? extends T> clazz);

}
