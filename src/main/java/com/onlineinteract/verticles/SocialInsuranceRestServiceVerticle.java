package com.onlineinteract.verticles;

import java.net.URI;

import javax.ws.rs.ext.ContextResolver;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.onlineinteract.application.SIWSApplication;

import io.vertx.core.AbstractVerticle;

public class SocialInsuranceRestServiceVerticle extends AbstractVerticle {

	public static final String BASE_URI = "http://0.0.0.0:" + SIWSApplication.BASE_PORT_NUMBER + "/siws/";
	private HttpServer server;

	public void start() {

		vertx.executeBlocking(future -> {
			// Call blocking API to set up Jersey Rest Service.
			try {
				System.out.println("Setting up Jersey Rest Service for SIWS");
				final ResourceConfig rc = new ResourceConfig().packages("com.onlineinteract.rest.resource");
				final MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig();
				final ContextResolver jsonConfigResolver = moxyJsonConfig.resolver();
				rc.register(MoxyJsonFeature.class);
				rc.register(jsonConfigResolver);
				// rc.register(JacksonFeature.class);
				server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
				System.out.println(String.format("SIWS Jersey Rest app started with WADL available at "
						+ "%sapplication.wadl\nHit enter to stop it...", BASE_URI));

			} catch (Exception e) {
				e.printStackTrace();
			}
			future.complete("Jersey Rest Service now running");
		}, false, res -> {
			System.out.println("The result is: " + res.result());
			vertx.close();
		});
	}

	public void stop() {
		// System.out.println("Server shutting down");
		// server.shutdown();
	}
}
