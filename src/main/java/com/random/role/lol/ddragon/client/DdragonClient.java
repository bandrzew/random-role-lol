package com.random.role.lol.ddragon.client;

import com.random.role.lol.common.client.Client;
import com.random.role.lol.ddragon.resource.DdragonResource;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DdragonClient extends Client {

	@Value("${ddragon.url}")
	private String url;

	public DdragonResource getDdragonResource() {
		return proxy(DdragonResource.class);
	}

	@Override
	public <T> T proxy(Class<? extends T> clazz) {
		ResteasyWebTarget target = client.target(url);
		return target.proxy(clazz);
	}

}
