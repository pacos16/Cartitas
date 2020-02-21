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

import functions.FuncionesCartas;
import model.Carta;

/**
 * Api Cartas
 * @author Paco Signes
 * Clase encargada de los endpoints de todas las operaciones
 * con cartas. 
 */
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
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnaCarta(@PathParam("id") int id) {
		FuncionesCartas funcionesCartas=FuncionesCartas.getInstance();
		Carta c=funcionesCartas.getUnaCarta(id);
		Gson gson=new Gson();
		return Response.status(200).entity(gson.toJson(c)).build() ;
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
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.TEXT_HTML)
	public Response deleteCarta(@PathParam("id") int id) {
		FuncionesCartas funcionesCartas=FuncionesCartas.getInstance();
		if(funcionesCartas.deleteCarta(id)) {
			return Response.status(200).entity("Success").build();
		}
		return Response.status(404).entity("404-Not found").build();
		
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCarta(@PathParam("id") int id,String json) {
		FuncionesCartas funcionesCartas=FuncionesCartas.getInstance();
		Gson gson=new Gson();
		
		if(funcionesCartas.updateCarta(id, gson.fromJson(json, Carta.class))) {
			return Response.status(200).entity(json).build();
		}else {
			return Response.status(404).entity(json).build();
		}
	}
	
	
}
