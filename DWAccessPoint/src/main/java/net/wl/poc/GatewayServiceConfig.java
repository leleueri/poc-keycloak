package net.wl.poc;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import de.ahus1.keycloak.dropwizard.KeycloakConfiguration;
import io.dropwizard.Configuration;

public class GatewayServiceConfig
		extends Configuration {

	@NotNull
	@Valid
	protected KeycloakConfiguration keycloak = new KeycloakConfiguration();

	public KeycloakConfiguration getKeycloak() {
		return keycloak;
	}

	public void setKeycloak(KeycloakConfiguration keycloak) {
		this.keycloak = keycloak;
	}
}
