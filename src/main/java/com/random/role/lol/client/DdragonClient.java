package com.random.role.lol.client;

import com.random.role.lol.resource.DdragonResource;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DdragonClient extends Client {

	@Value("${ddragon.url}")
	private String url;

	@Override
	@PostConstruct
	public void init() {
		super.init();
	}

	@Override
	@PreDestroy
	public void destroy() {
		super.destroy();
	}

	public DdragonResource getDdragonResource() {
		return proxy(DdragonResource.class);
	}

	@Override
	public <T> T proxy(Class<? extends T> clazz) {
		ResteasyWebTarget target = client.target(url);
		return target.proxy(clazz);
	}

}
