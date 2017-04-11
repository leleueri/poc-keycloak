package net.wl.poc.sy;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import de.ahus1.keycloak.dropwizard.DropwizardBearerTokenFilterImpl;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import de.ahus1.keycloak.dropwizard.KeycloakBundle;
import de.ahus1.keycloak.dropwizard.KeycloakConfiguration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.jaxrs.JaxrsBearerTokenFilterImpl;

public class HelloService
		extends io.dropwizard.Application<HelloConfig> {

	@Override
	public void initialize(Bootstrap<HelloConfig> bootstrap) {
		super.initialize(bootstrap);

		// TODO enable to access Principal through @Auth
		/*bootstrap.addBundle(new KeycloakBundle<HelloConfig>() {
			@Override
			protected KeycloakConfiguration getKeycloakConfiguration(HelloConfig configuration) {
				return configuration.getKeycloak();
			}
		});*/
	}

	@Override
	public void run(HelloConfig conf, Environment env) throws Exception {

		// Enable CORS headers
		final FilterRegistration.Dynamic cors = env.servlets().addFilter("CORS", CrossOriginFilter.class);
		// Configure CORS parameters
		cors.setInitParameter("allowedOrigins", "*");
		cors.setInitParameter("allowedHeaders", "Authorization, X-Requested-With,Content-Type,Accept,Origin");
		cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");

		// Add URL mapping
		cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

		env.jersey().register(new net.wl.poc.sy.resources.HelloResource());

		KeycloakDeployment keycloakDeployment = KeycloakDeploymentBuilder.build(conf.getKeycloak());
		JaxrsBearerTokenFilterImpl filter = new DropwizardBearerTokenFilterImpl(keycloakDeployment);
		env.jersey().register(filter);
	}

	public static void main(String[] args) throws Exception {
		new HelloService().run(args);
	}
}
