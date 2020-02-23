package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import functions.FuncionesTurnos;
import model.Turno;

@Path("turnos")
public class ApiTurnos {
	
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postTurno(String json) {
		FuncionesTurnos ft=FuncionesTurnos.getInstance();
		Turno t= new Gson().fromJson(json, Turno.class);
		if(ft.addTurno(t)>0) {
			return Response.status(200).build();
		}
		
		return Response.status(404).build();

	}
	

}
