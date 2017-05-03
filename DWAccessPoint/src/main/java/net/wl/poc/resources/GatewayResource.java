package net.wl.poc.resources;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.representation.EntitlementRequest;
import org.keycloak.authorization.client.representation.PermissionRequest;

import com.codahale.metrics.annotation.Timed;
import com.squareup.okhttp.OkHttpClient;

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
	// @RolesAllowed({ "gateway:service-y" })
	public String callServiceY(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "Y");
		/*
		 * Request requesty = new Request.Builder().url("http://localhost:8081/helloY").header("Authorization", auth).build(); Response response =
		 * client.newCall(requesty).execute(); return response.body().string();
		 */
		return "Access Granted to Y";
	}

	private void checkPermissionOnResource(String auth, String resourceName) {
		AuthzClient authzClient = AuthzClient.create();
		EntitlementRequest request = new EntitlementRequest();
		PermissionRequest permission = new PermissionRequest();
		permission.setResourceSetName(resourceName);
		request.addPermission(permission);
		authzClient.entitlement(auth.replaceFirst("Bearer ", "")).get("gateway", request);
	}

	@Path("x")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceX(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "X");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to X";
	}

	@Path("a")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceA(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "A");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to A";
	}

	@Path("b")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceB(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "B");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to B";
	}

	@Path("c")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceC(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "C");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to C";
	}

	@Path("d")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceD(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "D");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to D";
	}

	@Path("e")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceE(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "E");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to E";
	}

	@Path("f")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceF(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "F");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to F";
	}

	@Path("g")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceg(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "G");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to G";
	}

	@Path("h")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceH(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "H");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to H";
	}

	@Path("i")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceI(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "I");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to I";
	}

	@Path("j")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceJ(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "J");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to J";
	}

	@Path("k")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceK(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "K");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to K";
	}

	@Path("l")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceL(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "L");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to L";
	}

	@Path("m")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceM(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "M");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to M";
	}

	@Path("n")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceN(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "N");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to N";
	}

	@Path("o")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceO(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "O");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to O";
	}

	@Path("p")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceP(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "P");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to P";
	}

	@Path("q")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceQ(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "Q");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to Q";
	}

	@Path("r")
	@GET
	@Timed
	// @RolesAllowed({ "gateway:service-x" })
	public String callServiceR(@HeaderParam("Authorization") String auth, @Auth User principal) throws IOException {
		// System.out.println(auth);
		checkPermissionOnResource(auth, "R");
		/*
		 * Request request = new Request.Builder().url("http://localhost:8082/helloX").header("Authorization", auth).build(); Response response =
		 * client.newCall(request).execute(); return response.body().string();
		 */
		return "Access Granted to R";
	}
}
