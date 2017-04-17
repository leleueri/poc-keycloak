package net.wl.poc;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.info.SystemInfoRepresentation;

import java.util.List;

/**
 * Created by eric on 13/04/17.
 */
public class SimpleMain {
    public static void main(String[] args) {
        Keycloak keycloackClient = Keycloak.getInstance("http://localhost:8180/auth", "master", "admin", "admin", "admin-cli");

        RealmResource realm = keycloackClient.realm("my-realm");
        UsersResource users = realm.users();
        System.out.println("Show Users: ("+ users.count()+")");
        System.out.println("USERS : ");
        List<UserRepresentation> allUsers = users.search(null, null, null);
        for (UserRepresentation user: allUsers) {
            System.out.println("+++++++++ UserId : " + user.getId());
            System.out.println("Login : " + user.getUsername());
            System.out.println("Roles  : " + user.getClientRoles());
            System.out.println("Groups  : " + user.getGroups());

            UserResource userRes = users.get(user.getId());
            user.setFirstName(user.getFirstName() + " - " + System.currentTimeMillis());
            System.out.println("User in " + userRes.groups().size() + " groups");
            userRes.update(user);
        }

        UserRepresentation userTest = users.search("test", null, null, null, null, null).get(0);
        System.out.println("+++++++++ UserId (with more details?? no): " + userTest.getId());
        System.out.println("Login : " + userTest.getUsername());
        System.out.println("Roles  : " + userTest.getClientRoles());
        System.out.println("Groups  : " + userTest.getGroups());

        System.out.println("Get GroupByPath : " + realm.getGroupByPath("/GroupTest").getName());
    }
}
