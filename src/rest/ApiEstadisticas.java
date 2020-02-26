package rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import functions.Estadisticas;
import model.JugadorEstadisticas;

@Path("estadisticas")
public class ApiEstadisticas {
	
	@GET
	@Path("clasificacionJugadores")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEstadisticasJugadores() {
		Estadisticas est=Estadisticas.getInstance();
		JugadorEstadisticas[] jugadorEst=est.getEstadisticasJugadores()
				.toArray(new JugadorEstadisticas[est.getEstadisticasJugadores().size()]);
		Arrays.sort(jugadorEst, Collections.reverseOrder());
	
		String json=new Gson().toJson(jugadorEst);
		return Response.status(200).entity(json).build();
	}
	
	
	@GET
	@Path("estadisticasCartas")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEstadisticasCartas() {
		Estadisticas est=Estadisticas.getInstance();
	
	
		String json=new Gson().toJson(est.getEstadisticasCartas());
		return Response.status(200).entity(json).build();
	}
	
	

}
