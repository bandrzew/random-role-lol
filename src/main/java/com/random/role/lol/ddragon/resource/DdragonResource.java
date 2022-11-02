package com.random.role.lol.ddragon.resource;

import com.random.role.lol.ddragon.dto.DdragonResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
public interface DdragonResource {

	@GET
	@Path("api/versions.json")
	List<String> getVersions();

	@GET
	@Path("cdn/{version}/data/en_US/champion.json")
	DdragonResponse getChampions(@PathParam("version") String version);

}
