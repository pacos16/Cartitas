package functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import model.Caracteristicas;
import model.Carta;
import model.Partida;
import model.ResultadosPartidas;
import model.ResultadosTurnos;
import model.Turno;
/**
 * Clase singleton para el manejo de la partida
 * @author user
 *
 */
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
	
	
	public ArrayList<Object> iniciarPartida(String correoJugador) {
		
		String query= "SELECT MAX(id_partida) as max from partidas";
		Statement s;
		ResultSet rs;
		int idPartida;
		Turno turno;
		//sorteamos quien empieza
		Random random=new Random();
		boolean esPrimero=random.nextBoolean();
		try {
			s=connection.createStatement();
			rs=s.executeQuery(query);
			rs.next();
			idPartida=rs.getInt("max")+1;
			//generamos partida
			Partida partida=new Partida(idPartida,correoJugador,
					esPrimero,ResultadosPartidas.EN_CURSO.ordinal());
			FuncionesPartidas.getInstance().postPartida(partida);
			ArrayList<Object> response=new ArrayList();
			//Generamos el draft
			ArrayList<Carta> draft=generateDraft();
			//assignamos la mitad a la respuesta y la mitad la tabla
			//del server
			for(int i=0;i<draft.size();i++) {
				if(i<draft.size()/2) {
					response.add(draft.get(i));
				}else {
					insertaCpuDraft(idPartida,draft.get(i));
				}
			}
			
			/**
			 * Si es primero se le devuelve un objeto con el turno, la id partida
			 * y el valor booleano de esAtaque en true para que el cliente sepa
			 * que le toca atacar.
			 */
			if(esPrimero) {
				turno= new Turno(idPartida,0,0,0,1,esPrimero,0);
			}else {
				turno=new Turno();
				ArrayList<Carta> manoCpu=getCpuDraft(idPartida);
				turno.setCartaCpu(manoCpu.get(0).getId());
				int r=random.nextInt(Caracteristicas.values().length);
				turno.setCaracteristica(r);
				turno.setPartida(idPartida);
				setCartaJugada(idPartida,turno.getCartaCpu());
			}
			
			response.add(turno);
			return response;
		}catch (Exception e){
			return null;
		}
	
		
	}
	
	/**
	 * Recibe turno
	 * Esta funcion es la funcion de juego principal.
	 * y 
	 * @param t  Recibe un objeto de la clase turno
	 * @return devuelve un objeto de la clase turno segun la informacion que contenga
	 * Aqui decide que objeto envia de vuelta y que acciones se toman en la base de datos
	 * si devuelve defensa
	 * 
	 */
	public Turno recibirTurno(Turno t) {
		
		System.out.println(t.toString());
		
		/**
		 * Asigna una carta para combatir
		 */
		if(t.isAtaque()) {
			ArrayList<Carta> manoCpu=getCpuDraft(t.getPartida());
			t.setCartaCpu(manoCpu.get(0).getId());
			setCartaJugada(t.getPartida(),t.getCartaCpu());
		}
		/**
		 * Calcula y guarda el resultado
		 */
		ResultadosTurnos rt=calcularResultado(t);
		t.setResultado(rt.ordinal());
		
		FuncionesTurnos ft=FuncionesTurnos.getInstance();
		ft.addTurno(t);
		
		/**
		 * Si es fin partida devolvera un objeto
		 * como el recibido (es decir todos los valores inicializados) 
		 * pero le asignara resultado.
		 * A continuación guardara el resultado de la partida en la tabla partidas
		 */
		if(t.getNumTurno()==6) {
			ResultadosPartidas resultado=calcularResultadoPartida(t.getPartida());
			FuncionesPartidas fp=FuncionesPartidas.getInstance();
			fp.setPartidaResult(t.getPartida(),resultado);
		}else {
			/**
			 * Aqui genera una mano de vuelta
			 * Si es ataque significa que luego atacamos nosotros,
			 * debemos devolver un objeto con el resultado de la ronda anterior
			 * y con todo a 0 excepto caracteristica y carta cpu 
			 * is ataque sera false con este constructor, esto es
			 * lo que indicara al cliente que tiene que defender el siguiente turno.
			 * 
			 */
			if(t.isAtaque()) {
				Turno turno=new Turno();
				ArrayList<Carta> manoCpu=getCpuDraft(t.getPartida());
				turno.setCartaCpu(manoCpu.get(0).getId());
				Random random=new Random();
				int r=random.nextInt(Caracteristicas.values().length);
				turno.setCaracteristica(r);
				turno.setResultado(t.getResultado());
				turno.setPartida(t.getPartida());
				setCartaJugada(t.getPartida(),turno.getCartaCpu());
				//truquito para mandar de vuelta la carta que necesito
				turno.setCartaJugador(t.getCartaCpu());
				t=turno;
			}else {
				/**
				 * Si entra por este else significa que el cliente estaba
				 * defendiendo. Por tanto le seteamos ataque  true para que nos ataque el
				 */
				t.setAtaque(true);
			}
		}
		System.out.println(t.toString());

		return t;
	}
	
	/**
	 * Calcula el resultado de las partidas
	 * Lo hace recorriendo la tabla turnos y sumando los resultados.
	 * @param idPartida id de la partida 
	 * @return devuelbve el resultado 
	 */
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
	
	
	/**
	 * 
	 * @param t  Recibe un turno
	 * @return devuelve el resultado
	 */
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
	
	/**
	 * Generadores de drafts 
	 * @return devuelve un ArrayList Carta con 12 cartas diferentes
	 */
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
	
	
	
	/**
	 * Busca en la tabla cpu_drafts para ver que cartas 
	 * tiene disponibles la cpu para jugar
	 * @param idPartida
	 * @return
	 */
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
	
	/**
	 * Funcion que actualiza el valor de la tabla cpu_drafts para marcar la carta
	 * como jugada
	 * @param idPartida idpartida
	 * @param idCarta idCarta
	 * @return
	 */
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
	
	
	private void insertaCpuDraft(int idPartida,Carta c) {
		String query="INSERT INTO cpu_drafts (id_partida,id_carta,jugada) "
				+ "VALUES (?,?,?) ";
		PreparedStatement ps;
		
		try {
			ps=connection.prepareStatement(query);
			ps.setInt(1, idPartida);
			ps.setInt(2, c.getId());
			ps.setBoolean(3, false);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			
		}
		
	}
	/**
	 * Borra de la tabla cpu_drafts todas entradas de la partida
	 * pasada por parametro.
	 * La información de esta tabla es util para la partida, para el 
	 * funcionamiento de la aplicación no tiene sentido guardarla
	 * @param idPartida idPartida
	 */
	private void deleteCpuDraft(int idPartida) {
		//todo deleteCpuDraft
	}

}
