package net.wl.poc.sx.resources;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import de.ahus1.keycloak.dropwizard.User;
import io.dropwizard.auth.Auth;

@Path("/helloX")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HelloResource {

	@GET
	@Timed
	public String home(@Auth User principal) {
		// TODO modifier pour afficher l'identité
		if (principal != null && principal.userid != null) {
			return "Hello " + principal.userid + " From Service X!";
		} else {
			return "Hello World From Service X!";
		}
	}
}
