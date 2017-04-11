package net.wl.poc;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;

import de.ahus1.keycloak.dropwizard.KeycloakBundle;
import de.ahus1.keycloak.dropwizard.KeycloakConfiguration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.wl.poc.resources.GatewayResource;

public class GatewayService
		extends io.dropwizard.Application<GatewayServiceConfig> {

	@Override
	public void initialize(Bootstrap<GatewayServiceConfig> bootstrap) {
		super.initialize(bootstrap);

		bootstrap.addBundle(new KeycloakBundle<GatewayServiceConfig>() {
			@Override
			protected KeycloakConfiguration getKeycloakConfiguration(GatewayServiceConfig configuration) {
				return configuration.getKeycloak();
			}
		});
	}

	@Override
	public void run(GatewayServiceConfig conf, Environment env) throws Exception {

		// Enable CORS headers
		final FilterRegistration.Dynamic cors = env.servlets().addFilter("CORS", CrossOriginFilter.class);
		// Configure CORS parameters
		cors.setInitParameter("allowedOrigins", "*");
		cors.setInitParameter("allowedHeaders", "Authorization, X-Requested-With,Content-Type,Accept,Origin");
		cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

		// Add URL mapping
		cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

		env.jersey().register(new GatewayResource());

		// KeycloakDeployment keycloakDeployment = KeycloakDeploymentBuilder.build(configuration.getKeycloakConfiguration());
		// JaxrsBearerTokenFilterImpl filter = new DropwizardBearerTokenFilterImpl(keycloakDeployment);
		// environment.jersey().register(filter);
	}

	public static void main(String[] args) throws Exception {
		new GatewayService().run(args);
	}
}
