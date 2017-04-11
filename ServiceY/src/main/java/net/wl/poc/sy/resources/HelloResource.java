package net.wl.poc.sy.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

@Path("/helloY")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {

	@GET
	@Timed
	public String home() {
		// TODO modifier pour afficher l'identité
		return "Hello World From Service Y!";
	}
}
