package functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Turno;

public class FuncionesTurnos {
	
	private Connection connection;
	private static FuncionesTurnos instance=null;
	
	private FuncionesTurnos() {
		connection=DBConnection.getInstance();
	}
	
	public static FuncionesTurnos getInstance() {
		if(instance==null) {
			instance=new FuncionesTurnos();
		}
		return instance;
	}
	

	public int addTurno(Turno t) {
		
		String query="insert into turnos (id_partida,id_carta_jugador,caracteristica,id_carta_cpu,ataque,num_turno,resultado) "
				+ "values (?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement preparedStatement= connection.prepareStatement(query);
			preparedStatement.setInt(1,t.getPartida());
			preparedStatement.setInt(2,t.getCartaJugador());
			preparedStatement.setInt(3,t.getCaracteristica());
			preparedStatement.setInt(4,t.getCartaCpu());
			preparedStatement.setBoolean(5,t.isAtaque());
			preparedStatement.setInt(6,t.getNumTurno());
			preparedStatement.setInt(7,t.getResultado());

			int response=preparedStatement.executeUpdate();
			preparedStatement.close();
			return response;


		} catch (SQLException e) {
			return 0;
			
		}
		
	}
	

}
