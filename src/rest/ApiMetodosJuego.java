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

import functions.MetodosJuego;
import model.Carta;
import model.ResultadosTurnos;
import model.Turno;

@Path("metodosJuego")
public class ApiMetodosJuego {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response calcularResultadoTurno(String json) {
		MetodosJuego mj=MetodosJuego.getInstance();
		Turno t=new Gson().fromJson(json,Turno.class);
		ResultadosTurnos rt=mj.calcularResultado(t);
		t.setResultado(rt.ordinal());
		return Response.status(200).entity(new Gson().toJson(t)).build();
		
	}
	
	@GET
	@Path("resultado/{idPartida}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResultadoPartida(@PathParam("idPartida") int idPartida) {
		MetodosJuego mj=MetodosJuego.getInstance();
		
		switch(mj.calcularResultadoPartida(idPartida)) {
		case EMPATE:
			return Response.status(203).build();
		case EN_CURSO:
			return Response.status(200).build();
		case GANADA:
			return Response.status(201).build();
		case PERDIDA:
			return Response.status(202).build();
		default:
			return Response.status(404).build();
		
		}
		
	}
		
	
	@GET
	@Path("draft")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDraft() {
		MetodosJuego mj=MetodosJuego.getInstance();
		ArrayList<Carta> cartas=mj.generateDraft();
		return Response.status(200).entity(new Gson().toJson(cartas)).build();
		
	}
	
	@POST
	@Path("jugarTurno")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postTurno(String json) {
		Turno t=new Gson().fromJson(json,Turno.class);
		MetodosJuego mj=MetodosJuego.getInstance();
		t=mj.recibirTurno(t);
		return Response.status(200).entity(new Gson().toJson(t)).build();
	}
	

}
