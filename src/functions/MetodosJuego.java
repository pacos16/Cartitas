package functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import model.Caracteristicas;
import model.Carta;
import model.ResultadosPartidas;
import model.ResultadosTurnos;
import model.Turno;

public class MetodosJuego {
	
	private Connection connection;
	private static MetodosJuego instance=null;
	
	private MetodosJuego() {
		connection=DBConnection.getInstance();
	}
	
	public static MetodosJuego getInstance() {
		if(instance==null) {
			instance=new MetodosJuego();
		}
		return instance;
	}
	
	
	public Turno recibirTurno(Turno t) {
		
		if(t.isAtaque()) {
			ArrayList<Carta> manoCpu=getCpuDraft(t.getPartida());
			t.setCartaCpu(manoCpu.get(0).getId());
			setCartaJugada(t.getPartida(),t.getCartaCpu());
		}
		
		ResultadosTurnos rt=calcularResultado(t);
		t.setResultado(rt.ordinal());
		
		FuncionesTurnos ft=FuncionesTurnos.getInstance();
		ft.addTurno(t);
		
		if(t.getNumTurno()==6) {
			ResultadosPartidas resultado=calcularResultadoPartida(t.getPartida());
			FuncionesPartidas fp=FuncionesPartidas.getInstance();
			fp.setPartidaResult(t.getPartida(),resultado);
		}else {
			if(t.isAtaque()) {
				ArrayList<Carta> manoCpu=getCpuDraft(t.getPartida());
				Turno turno=new Turno();
				turno.setCartaCpu(manoCpu.get(0).getId());
				Random random=new Random();
				int r=random.nextInt(7);
				turno.setCaracteristica(r);
				turno.setResultado(t.getResultado());
				setCartaJugada(t.getPartida(),turno.getCartaCpu());
				t=turno;
			}
		}
		return t;
	}
	
	
	public ResultadosPartidas calcularResultadoPartida(int idPartida) {
		FuncionesTurnos ft=FuncionesTurnos.getInstance();
		ArrayList<Turno> turnos=ft.getTurnosPartida(idPartida);
		int ganadasJugador=0;
		int ganadasCpu=0;

		for(Turno t:turnos) {
			if(t.getResultado()==1) {
				ganadasJugador++;
			}else if(t.getResultado()==2) {
				ganadasCpu++;
			}
		}
		if(ganadasJugador>ganadasCpu) {
			return ResultadosPartidas.GANADA;
		}else if(ganadasJugador==ganadasCpu) {
			return ResultadosPartidas.EMPATE;
		}else {
			return ResultadosPartidas.PERDIDA;
		}
		
		
	}
	
	
	
	public ResultadosTurnos calcularResultado(Turno t) {
		ArrayList<Carta>cartas=FuncionesCartas.getInstance().getCartas();
		Carta cartaPlayer=cartas.get(t.getCartaJugador()-1);
		Carta cartaCpu=cartas.get(t.getCartaCpu()-1);
		Caracteristicas caracteristica=Caracteristicas.values()[t.getCaracteristica()];
		switch(caracteristica) {
		case MOTOR:
			if(cartaPlayer.getMotor()>cartaCpu.getMotor()) {
				return ResultadosTurnos.GANADA;
			}else if(cartaPlayer.getMotor()==cartaCpu.getMotor()) {
				return ResultadosTurnos.EMPATE;
			}else {
				return ResultadosTurnos.PERDIDA;
			}
	
		case CILINDROS:
			if(cartaPlayer.getCilindros()>cartaCpu.getCilindros()) {
				return ResultadosTurnos.GANADA;
			}else if(cartaPlayer.getCilindros()==cartaCpu.getCilindros()) {
				return ResultadosTurnos.EMPATE;
			}else {
				return ResultadosTurnos.PERDIDA;
			}

		case POTENCIA:
			int potenciaPlayer;
			int potenciaCpu;
			potenciaPlayer=Integer.valueOf(cartaPlayer.getPotencia().split("/")[0]);
			potenciaCpu=Integer.valueOf(cartaCpu.getPotencia().split("/")[0]);
			if(potenciaPlayer>potenciaCpu) {
				return ResultadosTurnos.GANADA;
			}else if(cartaPlayer.getPotencia().equals(cartaCpu.getPotencia())) {
				return ResultadosTurnos.EMPATE;
			}else {
				return ResultadosTurnos.PERDIDA;
			}
		case REVOLUCIONES:
			if(cartaPlayer.getRevolucinoes()>cartaCpu.getRevolucinoes()) {
				return ResultadosTurnos.GANADA;
			}else if(cartaPlayer.getRevolucinoes()==cartaCpu.getRevolucinoes()) {
				return ResultadosTurnos.EMPATE;
			}else {
				return ResultadosTurnos.PERDIDA;
			}
		case VELOCIDAD:
			if(cartaPlayer.getVelocidad()>cartaCpu.getVelocidad()) {
				return ResultadosTurnos.GANADA;
			}else if(cartaPlayer.getVelocidad()==cartaCpu.getVelocidad()) {
				return ResultadosTurnos.EMPATE;
			}else {
				return ResultadosTurnos.PERDIDA;
			}
		case CONSUMO:
			if(cartaPlayer.getConsumo()<cartaCpu.getConsumo()) {
				return ResultadosTurnos.GANADA;
			}else if(cartaPlayer.getVelocidad()==cartaCpu.getVelocidad()) {
				return ResultadosTurnos.EMPATE;
			}else {
				return ResultadosTurnos.PERDIDA;
			}

		}
		return null;
	}
	
	public ArrayList<Carta> generateDraft(){
		FuncionesCartas fc=FuncionesCartas.getInstance();
		Carta[] cartas= fc.getCartas().toArray(new Carta[fc.getCartas().size()]);
		ArrayList<Carta> alist=new ArrayList();
		int max=cartas.length;
		Random random=new Random();
		for(int i=0;i<12;i++) {
			int r=random.nextInt(max);
			alist.add(cartas[r]);
			cartas[r]=cartas[max-1];
			max--;
		}
		return alist;
	}
	
	
	public ArrayList<Carta> getCpuDraft(int idPartida){
		String query="SELECT * FROM cpu_drafts WHERE id_partida=?";
		FuncionesCartas fc=FuncionesCartas.getInstance();
		ArrayList<Carta> cartas=fc.getCartas();
		ArrayList<Carta> mano=new ArrayList();
		
		PreparedStatement ps;
		ResultSet rs;
		
		try {
			ps=connection.prepareStatement(query);
			ps.setInt(1, idPartida);
			rs=ps.executeQuery();
			
			while(rs.next()) {
				if(!rs.getBoolean("jugada")) {
					mano.add(cartas.get(rs.getInt("id_carta")-1));
				}
			}
		} catch (SQLException e) {
			return null;
		}
		
		return mano;
	}
	
	public int setCartaJugada(int idPartida,int idCarta) {
		String query="UPDATE cpu_drafts SET jugada=1 "
				+ "WHERE id_partida=? AND id_carta=?";
		PreparedStatement ps;
		
		try {
			ps=connection.prepareStatement(query);
			ps.setInt(1, idPartida);
			ps.setInt(2, idCarta);
			return ps.executeUpdate();
			
		} catch (SQLException e) {
			return 0;
		}
	}
	
	private void deleteCpuDraft(int idPartida) {
		
	}

}
