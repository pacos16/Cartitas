package rest;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import functions.FuncionesCartas;
import model.Carta;

@Path("cartas")
public class ApiCartas {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCartas() {
		FuncionesCartas funcionesCartas=FuncionesCartas.getInstance();
		ArrayList<Carta> cartas=funcionesCartas.getCartas();
		Gson gson=new Gson();
		return Response.status(200).entity(gson.toJson(cartas)).build() ;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCarta(String json) {
		FuncionesCartas funcionesCartas=FuncionesCartas.getInstance();
		Gson gson=new Gson();
		Carta c= gson.fromJson(json,Carta.class);
		if(funcionesCartas.addCarta(c)) {
			return Response.status(200).entity(json).build();
		}else {
			return Response.status(500).entity(json).build();
		}
		
	}
	
	
}
