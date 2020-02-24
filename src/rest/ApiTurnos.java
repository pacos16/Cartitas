package rest;

import java.util.ArrayList;

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

import functions.FuncionesTurnos;
import model.Turno;

@Path("turnos")
public class ApiTurnos {
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTurnos() {
		FuncionesTurnos ft=FuncionesTurnos.getInstance();
		ArrayList<Turno> turnos=ft.getTurnos();
		if(turnos!=null && turnos.size()>0) {
			String json=new Gson().toJson(turnos);
			return Response.status(200).entity(json).build();
		}
		return Response.status(404).build();
		
		
	}
	
	@GET
	@Path("{id_partida}/{num_turno}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTurno(@PathParam("id_partida") int idPartida,
			@PathParam("num_turno") int numTurno) {
		FuncionesTurnos ft=FuncionesTurnos.getInstance();
		Turno t=ft.getUnTurno(idPartida, numTurno);
		if(t!=null) {
			String json= new Gson().toJson(t);
			return Response.status(200).entity(json).build();
		}
		return Response.status(404).build();
		
	}
	
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
	
	@PUT
	@Path("{id_partida}/{num_turno}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateTurno(@PathParam("id_partida") int idPartida,
			@PathParam("num_turno") int numTurno, String json){
		
		FuncionesTurnos ft=FuncionesTurnos.getInstance();
		Turno t=new Gson().fromJson(json,Turno.class);
		int response=ft.updateTurno(idPartida, numTurno,t);
		if(response>0) {
			return Response.status(200).build();
		}
		return Response.status(404).build();
	}
	
	@DELETE
	@Path("{id_partida}/{num_turno}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTurno(@PathParam("id_partida") int idPartida,
			@PathParam("num_turno") int numTurno) {
		FuncionesTurnos ft=FuncionesTurnos.getInstance();
		if(ft.deleteTurno(idPartida, numTurno)>0) {
			return Response.status(200).build();
		}
		return Response.status(404).build();

	}
}
