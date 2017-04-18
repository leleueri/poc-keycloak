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
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.representation.EntitlementRequest;
import org.keycloak.authorization.client.representation.EntitlementResponse;
import org.keycloak.authorization.client.representation.PermissionRequest;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GatewayResource {
	private final OkHttpClient client = new OkHttpClient();

	@Path("y")
	@GET
	@Timed
	//@RolesAllowed("gateway:service-y")
	public String callServiceY(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {

		System.out.println(auth);
		// create a new instance based on the configuration defined in keycloak-authz.json
		AuthzClient authzClient = AuthzClient.create();

		// obtain an Entitlement API Token to get access to the Entitlement API.
		// this token is an access token issued to a client on behalf of an user
		// with a scope = kc_entitlement
		//String eat = getEntitlementAPIToken(authzClient);

		// send the entitlement request to the server to
		// obtain an RPT with all permissions granted to the user
		EntitlementResponse eresponse = authzClient.entitlement(auth.replaceFirst("Bearer ","")).getAll("gateway");
		String rpt = eresponse.getRpt();
		System.out.println("You got a RPT: " + rpt);

		EntitlementRequest request = new EntitlementRequest();
		PermissionRequest permission = new PermissionRequest();
		permission.setResourceSetName("Y");
		request.addPermission(permission);
		authzClient.entitlement(auth.replaceFirst("Bearer ","")).get("gateway", request);
		rpt = eresponse.getRpt();
		System.out.println("You got a RPT 2 : " + rpt);

		Request requesty = new Request.Builder().url("http://localhost:8081/helloY").header("Authorization", auth).build();
		Response response = client.newCall(requesty).execute();
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
