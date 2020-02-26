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
import com.google.gson.GsonBuilder;

import functions.MetodosJuego;
import model.Carta;
import model.ResultadosTurnos;
import model.Turno;
/**
 * Clase encargada de los endpoints de los metodos de juego
 * @author user
 *
 */
@Path("metodosJuego")
public class ApiMetodosJuego {
	
	/**
	 * Recibe un turno y devuelve resultado
	 * @param json  recibe un json de la clas Turno
	 * @return devuelve un entity con el mismo json pero con el resultado
	 */
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
	
	/**
	 * Le pasamos un id partida por parametro y nos calcula el resultado 
	 * mediante la funcion calcularResultadoPartida de la clase MetodosJuego
	 * @param idPartida recibe la id partida
	 * @return devuelve el resultado con el status. Si no existe tambien devuelve 203
	 */
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
		
	/**
	 * Endpoint que llama a la funcion generate draft
	 * 
	 * @return devuelve un json con doce cartas aleatorias
	 */
	@GET
	@Path("draft")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDraft() {
		MetodosJuego mj=MetodosJuego.getInstance();
		ArrayList<Carta> cartas=mj.generateDraft();
		return Response.status(200).entity(new Gson().toJson(cartas)).build();
		
	}
	
	/**
	 * Endpoint que por el cual recibiremos el turno que envie el jugador
	 * llamamos a la funcion recibirTurno de la clase MetodosJuego.
	 * @param json recibe un json de la clase Turno
	 * @return genera una respuesta json de la clase turno y la envia al cliente
	 */
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
	
	@GET
	@Path("iniciarPartida/{correo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response iniciarPartida(@PathParam("correo") String correo) {
		MetodosJuego mj=MetodosJuego.getInstance();
		Gson gson=new Gson();
		String json=gson.toJson(mj.iniciarPartida(correo));
		return Response.status(200).entity(json).build();

	}
	

}
