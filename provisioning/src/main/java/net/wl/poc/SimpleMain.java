package net.wl.poc;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.PoliciesResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.ResourcesResource;
import org.keycloak.admin.client.resource.RoleScopeResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.authorization.DecisionStrategy;
import org.keycloak.representations.idm.authorization.Logic;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.keycloak.representations.idm.authorization.ResourceOwnerRepresentation;
import org.keycloak.representations.idm.authorization.ResourceRepresentation;

/**
 * Created by eric on 13/04/17.
 */
public class SimpleMain {
	public static void main(String[] args) {
		final String REALM = "bench3-realm";
		final String FRONT_APP_CLIENTID = "front-app";
		final String GATEWAY_CLIENTID = "gateway";

		Keycloak keycloackClient = Keycloak.getInstance("http://10.24.131.231:8180/auth", "master", "admin", "admin", "admin-cli");
		RealmResource realm = keycloackClient.realm(REALM);

		final String LETTERS = "XYABCDEFGHIJKLMNOPQR";

		// // ------------------
		// // FRONT-APP
		// // ------------------
		final String frontID = realm.clients().findByClientId(FRONT_APP_CLIENTID).get(0).getId();
		ClientResource frontAppClient = realm.clients().get(frontID);
		final RolesResource frontAppRoles = frontAppClient.roles();
		LETTERS.chars().mapToObj(i -> String.valueOf((char) i)).forEach(roleName -> {
			RoleRepresentation role = new RoleRepresentation();
			role.setClientRole(true);
			role.setName("front-app:page-" + roleName);
			frontAppRoles.create(role);
		});
		//
		// // ------------------
		// // GATEWAY - define role, resources, policies and permissions
		// // ------------------
		final String gatewayId = realm.clients().findByClientId(GATEWAY_CLIENTID).get(0).getId();
		ClientResource gatewayClient = realm.clients().get(gatewayId);
		final RolesResource gatewayRoles = gatewayClient.roles();

		final ResourcesResource resources = gatewayClient.authorization().resources();
		final PoliciesResource policies = gatewayClient.authorization().policies();
		//
		LETTERS.chars().mapToObj(i -> String.valueOf((char) i)).forEach(roleName -> {
			// define new service role
			RoleRepresentation role = new RoleRepresentation();
			role.setClientRole(true);
			role.setName("gateway:service-" + roleName);
			gatewayRoles.create(role);
			// define the resource with a client App owner
			ResourceOwnerRepresentation clientOwner = new ResourceOwnerRepresentation();
			clientOwner.setId(gatewayId);
			clientOwner.setName(GATEWAY_CLIENTID);
			ResourceRepresentation rr = new ResourceRepresentation();
			rr.setName(roleName);
			rr.setUri("/" + roleName.toLowerCase());
			rr.setOwner(clientOwner);
			resources.create(rr).close();
			// define the policy for the resource
			PolicyRepresentation policy = new PolicyRepresentation();
			policy.setDecisionStrategy(DecisionStrategy.UNANIMOUS);
			policy.setLogic(Logic.POSITIVE);
			policy.setName("Policy-Service" + roleName);
			policy.setType("role");
			Map<String, String> config = new HashMap<>();
			config.put("roles", "[{\"id\":\"" + gatewayRoles.get(role.getName()).toRepresentation().getId() + "\"}]");
			policy.setConfig(config);
			policies.create(policy).close();
			// define the permission on the resource with the policy
			policies.policies().stream().filter((x) -> x.getName().equals(policy.getName())).map(p -> p.getId()).forEach(id -> {
				PolicyRepresentation permission = new PolicyRepresentation();
				permission.setDecisionStrategy(DecisionStrategy.UNANIMOUS);
				permission.setLogic(Logic.POSITIVE);
				permission.setName(roleName);
				permission.setType("resource");
				Map<String, String> pconfig = new HashMap<>();
				pconfig.put("applyPolicies", "[\"" + id + "\"]");
				pconfig.put("resources", "[\"" + resources.find(rr.getName(), null, null, null, null, null, 1).get(0).getId() + "\"]");
				permission.setConfig(pconfig);
				policies.create(permission).close();
			});
		});

		// -------------
		// USERS
		// -------------
		UsersResource users = realm.users();
		// for (int i = 1; i <= 40000; i++) {
		IntStream.rangeClosed(1, 40000).forEach(i -> {
			UserRepresentation userRepresentation = new UserRepresentation();
			// {enabled: true, attributes: {}, requiredActions: [], username: "USERNAME"}
			userRepresentation.setEnabled(true);
			userRepresentation.setUsername("loginuser" + i);
			users.create(userRepresentation).close();
			// read to obtain the User internal ID
			userRepresentation = realm.users().search(userRepresentation.getUsername(), null, null, null, null, 1).get(0);
			UserResource userResource = realm.users().get(userRepresentation.getId());
			// set user password
			// {type: "password", value: "test", temporary: false}
			CredentialRepresentation pwd = new CredentialRepresentation();
			pwd.setTemporary(false);
			pwd.setType("password");
			pwd.setValue(userRepresentation.getUsername());
			userResource.resetPassword(pwd);
			// set user client roles
			// [{"id":"2014e01f-9efa-4381-9fba-548a59a65991","name":"front-app:page-X","scopeParamRequired":false,"composite":false,"clientRole":true,"containerId":"b8da557e-302f-4c61-9209-2e70b27c55b1"},{"id":"d36ec0b5-9d71-431f-b7f8-01f74ccee231","name":"front-app:page-Y","scopeParamRequired":false,"composite":false,"clientRole":true,"containerId":"b8da557e-302f-4c61-9209-2e70b27c55b1"}]
			RoleScopeResource frontRoles = userResource.roles().clientLevel(frontID);
			frontRoles.add(frontAppClient.roles().list());
			RoleScopeResource gatewayScopeRoles = userResource.roles().clientLevel(gatewayId);
			gatewayScopeRoles.add(gatewayClient.roles().list());

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
