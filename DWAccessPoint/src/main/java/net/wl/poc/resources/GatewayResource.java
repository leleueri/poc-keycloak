package net.wl.poc.resources;

import java.io.IOException;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import de.ahus1.keycloak.dropwizard.User;
import io.dropwizard.auth.Auth;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GatewayResource {
	private final OkHttpClient client = new OkHttpClient();

	@Path("y")
	@GET
	@Timed
	@RolesAllowed("gateway:service-y")
	public String callServiceY(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {

		System.out.println(auth);

		Request request = new Request.Builder().url("http://localhost:8081/helloY").build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	@Path("x")
	@GET
	@Timed
	@RolesAllowed({ "gateway:service-x" })
	public String callServiceX(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {

		System.out.println(auth);

		Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}
}
