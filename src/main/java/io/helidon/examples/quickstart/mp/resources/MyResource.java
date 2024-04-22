package io.helidon.examples.quickstart.mp.resources;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/messages")
@RequestScoped
public class MyResource {

	@GET
	@Path("/mymessage")
	public String showMessage() {
		
		return "welcome to REST APP";
	}
}

