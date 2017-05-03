import { Injectable } from '@angular/core';

declare var Keycloak: any;

@Injectable()
export class KeycloakService {
  static auth: any = {};

  static init(): Promise<any> {
    console.log('*** INIT');

   let keycloakAuth: any = new Keycloak({
  "realm": "test2-realm",
  "auth-server-url": "http://localhost:8180/auth",
  "ssl-required": "external",
  "resource": "front-app",
  "clientId": "front-app",
  "credentials": {
    "secret": "8936a14a-3acd-4a6c-ae96-556c6f969526"
  },
  "use-resource-role-mappings": true,
  "policy-enforcer": {}
});

    KeycloakService.auth.loggedIn = false;

      return new Promise((resolve, reject) => {
        keycloakAuth.init({ onLoad: 'login-required' })
          .success((test) => {
            console.log(">>>>INIT.success:" +JSON.stringify(test))
            KeycloakService.auth.loggedIn = true;
            KeycloakService.auth.authz = keycloakAuth;
            KeycloakService.auth.logoutUrl = keycloakAuth.authServerUrl + "/realms/my-realm/protocol/openid-connect/logout?redirect_uri=http://localhost:4200/front-app";
            resolve();
          })
          .error(() => {
            reject();
          });
      });
  }

  logout() {
    console.log('*** LOGOUT');
    KeycloakService.auth.loggedIn = false;
    KeycloakService.auth.authz = null;

    window.location.href = KeycloakService.auth.logoutUrl;
  }

  getToken(): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      if (KeycloakService.auth.authz.token) {
        KeycloakService.auth.authz.updateToken(5)
          .success(() => {
            resolve(<string>KeycloakService.auth.authz.token);
          })
          .error(() => {
            reject('Failed to refresh token');
          });
      }
    });
  }

  getUserName(): string {
    KeycloakService.auth.authz.idTokenParsed
    console.log("AuthToken : " + KeycloakService.auth.authz.token)
    console.log("IDToken : " + KeycloakService.auth.authz.idToken)
    console.log("IDToken.parsed : " + JSON.stringify(KeycloakService.auth.authz.idTokenParsed))
    console.log("--> ID: : " + KeycloakService.auth.authz.idTokenParsed.preferred_username)
    console.log("--> FirstName: : " + KeycloakService.auth.authz.idTokenParsed.given_name)

    console.log("user.hasRole('adlin') = " + KeycloakService.auth.authz.hasResourceRole("front:admin", "front-app"))
    console.log("user.hasRole('x') = " + KeycloakService.auth.authz.hasResourceRole("front:page-x", "front-app"))
    console.log("user.hasRole('y') = " + KeycloakService.auth.authz.hasResourceRole("front:page-y", "front-app"))
    return KeycloakService.auth.authz.idTokenParsed.given_name
  }
  
  hasRole(): boolean {
    return KeycloakService.auth.authz.hasResourceRole("front:page-y", "front-app")
  }
}