package rest;



import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import functions.*;


@Path("/inicio")
public class Api{
	
	@GET
	@Path("/saluda")
	@Produces(MediaType.TEXT_PLAIN)
	public String saluda() {
		
		
		return "Fuck you";
	}
	
	
	
}
