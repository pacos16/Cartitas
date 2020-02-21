package rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;


import functions.FuncionesJugadores;
import model.Jugador;

@Path("jugadores")
public class ApiJugadores {
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getJugadores() {
		FuncionesJugadores funcionesJugadores=FuncionesJugadores.getInstance();
		Gson gson= new Gson();
		return Response.status(200)
				.entity(gson.toJson(funcionesJugadores.getJugadores()))
				.build();
		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addJugador(String json) {
		FuncionesJugadores funcionesJugadores=FuncionesJugadores.getInstance();
		Gson gson=new Gson();
		Jugador j= gson.fromJson(json, Jugador.class);
		if(funcionesJugadores.addJugador(j)) {
			return Response.status(200).entity(json).build();
		}else {
			return Response.status(500).entity(gson.toJson("fail")).build();
		}
	}

	
	

}
