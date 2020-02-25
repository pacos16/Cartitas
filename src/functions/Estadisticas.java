package functions;

import java.sql.Connection;
import java.util.ArrayList;

import model.Jugador;
import model.JugadorEstadisticas;
import model.Partida;

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
	
}
