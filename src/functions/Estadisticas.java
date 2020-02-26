package functions;

import java.sql.Connection;
import java.util.ArrayList;

import model.Carta;
import model.CartaEstadistica;
import model.Jugador;
import model.JugadorEstadisticas;
import model.Partida;
import model.Turno;

public class Estadisticas {
	
	private Connection connection;
	private static Estadisticas instance=null;
	
	private Estadisticas() {
		connection=DBConnection.getInstance();
	}
	
	public static Estadisticas getInstance() {
		if(instance==null) {
			instance=new Estadisticas();
		}
		return instance;
	}

	public ArrayList<JugadorEstadisticas> getEstadisticasJugadores() {
		
		ArrayList<JugadorEstadisticas> jugadores=new ArrayList();
		
		FuncionesPartidas fp=FuncionesPartidas.getInstance();
		ArrayList<Partida> partidas=fp.getPartidas();
		for(Partida p:partidas) {
			
			String idPlayer=p.getIdPlayer();
			int resultado=p.getResultado();
			boolean encontrado=false;
			for(JugadorEstadisticas je: jugadores) {
				if(idPlayer.equals(je.getCorreo())) {
					je.setResultado(resultado);
					encontrado=true;
				}
			}
			if(!encontrado) {
				JugadorEstadisticas jugador=new JugadorEstadisticas(idPlayer);
				jugador.setResultado(p.getResultado());
				jugadores.add(jugador);
			}
			
		}
		return jugadores;
		
	}
	
	public ArrayList<CartaEstadistica> getEstadisticasCartas(){
		FuncionesTurnos ft=FuncionesTurnos.getInstance();
		FuncionesCartas fc=FuncionesCartas.getInstance();
		ArrayList<Carta> cartas=fc.getCartas();
		ArrayList<CartaEstadistica> estadisticas=new ArrayList<>();
		
		for(int i=0;i<cartas.size();i++) {
			estadisticas.add(new CartaEstadistica(cartas.get(i).getId()));
		}
		ArrayList<Turno> turnos=ft.getTurnos();
		
		for(Turno t:turnos) {
			if(t.getResultado()==1) {
				estadisticas.get(t.getCartaJugador()-1).addGanadas();
				estadisticas.get(t.getCartaCpu()-1).addPerdidas();
			}else if(t.getResultado()==0) {
				estadisticas.get(t.getCartaJugador()-1).addEmpatadas();
				estadisticas.get(t.getCartaCpu()-1).addEmpatadas();
			}else if(t.getResultado()==2){
				estadisticas.get(t.getCartaJugador()-1).addPerdidas();
				estadisticas.get(t.getCartaCpu()-1).addGanadas();
			}
		}
		
		return estadisticas;
		
	}
	
}
