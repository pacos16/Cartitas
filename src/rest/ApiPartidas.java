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

import functions.FuncionesPartidas;
import model.Partida;
/**
 * Clase responsable de los endpoints de las partidas
 * @author user
 *
 */
@Path("partidas")
public class ApiPartidas {
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response  getPartidas(){
		FuncionesPartidas fp=FuncionesPartidas.getInstance();
		
		String json=new Gson().toJson(fp.getPartidas());
		return Response.status(200).entity(json).build();
	}
	
	@GET
	@Path("{idPartida}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnaPartida(@PathParam("idPartida") int idPartida) {
		FuncionesPartidas fp=FuncionesPartidas.getInstance();
		Partida p=fp.getUnaPartida(idPartida);
		if(p!=null) {
			return Response.status(200).entity(new Gson().toJson(p)).build();
		}
		return Response.status(404).build();
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response postPartida(String json) {
		FuncionesPartidas fp=FuncionesPartidas.getInstance();
		Partida p=new Gson().fromJson(json,Partida.class);
		if(fp.postPartida(p)) {
			return Response.status(200).build();
		}
		return Response.status(404).build();

		
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response putPartida(String json) {
		FuncionesPartidas fp=FuncionesPartidas.getInstance();
		Partida p=new Gson().fromJson(json,Partida.class);
		if(fp.postPartida(p)) {
			return Response.status(200).build();
		}
		return Response.status(404).build();

		
	}
	
	@DELETE
	@Path("{idPartida}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePartida(@PathParam("idPartida") int idPartida) {
		FuncionesPartidas fp=FuncionesPartidas.getInstance();
		
		if(fp.deletePartida(idPartida)>0) {
			return Response.status(200).build();
		}
		return Response.status(404).build();

		
	}
	
	
}
