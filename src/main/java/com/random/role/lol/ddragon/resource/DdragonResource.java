package com.random.role.lol.ddragon.resource;

import com.random.role.lol.ddragon.dto.DdragonResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
