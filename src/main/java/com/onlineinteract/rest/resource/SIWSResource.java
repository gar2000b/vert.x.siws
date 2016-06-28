package com.onlineinteract.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.onlineinteract.application.SIWSApplication;
import com.onlineinteract.model.Customer;
import com.onlineinteract.model.SINRequestResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

@Path("v1")
public class SIWSResource {

	@POST
	@Path("/{requestNo}/{customerId}")
	@Consumes(MediaType.TEXT_PLAIN)
	// curl -i -X POST http://192.168.0.23:8080/siws/v1/123/456
	public Response sendSINRequest(@PathParam("requestNo") String requestNo,
			@PathParam("customerId") String customerId) {
		System.out.println("\n* SIWS received instruction to request a SIN");
		System.out.println("* sinRequest requestNo: " + requestNo + ", customerId: " + customerId);

		SINRequestResponse sinRequest = insertSINRequest(requestNo, customerId);

		Customer customer = null;
		try {
			customer = fetchCustomer(customerId);
			System.out.println("* Fetched customer with SIN of: " + customer.getSin());
			System.out.println("* from CS UUID: " + customer.getCsuuid());
		} catch (Exception e) {
			System.out.println("* There was a problem, maybe CS is down.");
			System.out.println(e.getMessage());
		}

		sinRequest.setCustomer(customer);
		saveSINRequest(sinRequest);

		return Response.status(200).entity("SIN Request Received OK and forwarded to SIVS for further processing."
				+ "\nfetched customer with SIN of " + customer.getSin()
				+ "\nCS UUID: " + customer.getCsuuid()).build();
	}

	private Customer fetchCustomer(String customerId) throws Exception {

		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);

		WebResource webResource = client
				.resource("http://" + SIWSApplication.CS_ADDRESS + "/cs/v1/getCustomer/" + customerId);

		ClientResponse clientResponse = webResource.get(ClientResponse.class);
		Customer customer = clientResponse.getEntity(Customer.class);
		if (clientResponse.getStatus() != 200) {
			throw new Exception();
		}

		return customer;
	}

	private SINRequestResponse insertSINRequest(String requestNo, String customerId) {
		SINRequestResponse sinRequestResponse = new SINRequestResponse(requestNo, customerId);
		// System.out.println("** ObjectId before insert = " +
		// sinRequestResponse.getId());
		// System.out.println("** ObjectId after insert = " +
		// sinRequestResponse.getId());
		return sinRequestResponse;
	}

	private SINRequestResponse saveSINRequest(SINRequestResponse sinRequestResponse) {
		// System.out.println("** ObjectId before insert = " +
		// sinRequestResponse.getId());
		// System.out.println("** ObjectId after insert = " +
		// sinRequestResponse.getId());
		return sinRequestResponse;
	}

	// Test method for testing Jersey client / JSON.
	public static void main(String[] args) throws Exception {
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);

		WebResource webResource = client.resource("http://" + "localhost:8085" + "/cs/v1/getCustomer/" + "123");

		ClientResponse clientResponse = webResource.get(ClientResponse.class);
		Customer customer = clientResponse.getEntity(Customer.class);

		if (clientResponse.getStatus() != 200) {
			throw new Exception();
		}

		System.out.println("Customer with SIN of: " + customer.getSin());
	}
}
