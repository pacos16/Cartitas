package rest;

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
	
	@GET
	@Path("{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnJugador(@PathParam("correo") String correo) {
		FuncionesJugadores funcionesJugadores=FuncionesJugadores.getInstance();
		Gson gson= new Gson();
		return Response.status(200)
				.entity(gson.toJson(funcionesJugadores.getUnJugador(correo)))
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
			return Response.status(404).entity(json).build();
		}
	}
	
	@DELETE
	@Path("{correo}")
	@Produces(MediaType.TEXT_HTML)
	public Response deleteCarta(@PathParam("correo") String correo) {
		FuncionesJugadores funcionesJugadores=FuncionesJugadores.getInstance();
		if(funcionesJugadores.deleteJugador(correo)>0) {
			return Response.status(200).entity("Success").build();
		}
		return Response.status(404).entity("404-Not found").build();
		
	}
	
	@PUT
	@Path("{correo}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCarta(@PathParam("correo") String correo,String json) {
		FuncionesJugadores funcionesJugadores=FuncionesJugadores.getInstance();

		Gson gson=new Gson();
		
		if(funcionesJugadores.updateJugador(correo, gson.fromJson(json, Jugador.class))) {
			return Response.status(200).entity(json).build();
		}else {
			return Response.status(404).entity(null).build();
		}
	}

	
	

}
