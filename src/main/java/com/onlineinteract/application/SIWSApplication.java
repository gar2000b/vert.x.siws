package com.onlineinteract.application;

import java.util.UUID;

import com.onlineinteract.verticles.SocialInsuranceRestServiceVerticle;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class SIWSApplication {

	private String deploymentID;
	private Vertx vertx;
	// public static String CS_ADDRESS = "cs.experian.apcera-platform.io";
	public static String CS_ADDRESS = "localhost:8085";
	public static String BASE_PORT_NUMBER = "8080";
	public static UUID APP_ID = UUID.randomUUID();

	public SIWSApplication() {
		
		vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));

		vertx.deployVerticle(new SocialInsuranceRestServiceVerticle(), res -> {
			if (res.succeeded()) {
				deploymentID = res.result();
				System.out.println("Deployment id is: " + res.result());
			} else {
				System.out.println("Deployment failed!");
			}
		});
	}
	
	@SuppressWarnings("unused")
	private void undeployVerticles() {
		System.out.println("Do we even get here");
		vertx.undeploy(deploymentID, res -> {
			System.out.println("* undeploying verticles");
			if (res.succeeded()) {
				System.out.println("Undeployed ok");
			} else {
				System.out.println("Undeploy failed!");
			}
		});
	}

	public static void main(String[] args) {
		
		System.out.println("Running Social Insurance Workflow Service (SIWS) Application with UUID of " + APP_ID + ". \nCustomer Service (CS) Address is: " + CS_ADDRESS);
		
		new SIWSApplication();
	}
}
