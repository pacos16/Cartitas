package functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Jugador;
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
	
	
public ArrayList<Turno> getTurnos(){
		
		String query="select * from turnos";
		
		Statement statement;
		ResultSet resultSet;
		ArrayList<Turno> turnos=new ArrayList();
		
		try {
			statement=connection.createStatement();
			resultSet=statement.executeQuery(query);
			
			while(resultSet.next()) {
				
				turnos.add(new Turno(
							resultSet.getInt("id_partida"),
							resultSet.getInt("id_carta_jugador"),
							resultSet.getInt("id_carta_cpu"),
							resultSet.getInt("caracteristica"),
							resultSet.getInt("num_turno"),
							resultSet.getBoolean("ataque"),
							resultSet.getInt("resultado")
							
						));
			}
			statement.close();
		} catch (SQLException e) {
			return null;
		}
		return turnos;
		
		
	}
/**
 * 
 * @param idPartida
 * @return
 */
public ArrayList<Turno> getTurnosPartida(int idPartida){
	
	String query="select * from turnos WHERE id_partida=?";
	
	PreparedStatement ps;
	ResultSet resultSet;
	ArrayList<Turno> turnos=new ArrayList();
	
	try {
		ps=connection.prepareStatement(query);
		ps.setInt(1, idPartida);
		resultSet=ps.executeQuery();
		
		while(resultSet.next()) {
			
			turnos.add(new Turno(
						resultSet.getInt("id_partida"),
						resultSet.getInt("id_carta_jugador"),
						resultSet.getInt("id_carta_cpu"),
						resultSet.getInt("caracteristica"),
						resultSet.getInt("num_turno"),
						resultSet.getBoolean("ataque"),
						resultSet.getInt("resultado")
						
					));
		}
		ps.close();
	} catch (SQLException e) {
		return null;
	}
	return turnos;
	
	
}
	
	public Turno getUnTurno(int idPartida , int numTurno) {
		
		String query="SELECT * FROM turnos WHERE id_partida=? and num_turno=?";
		
		PreparedStatement statement;
		ResultSet resultSet;
		
		
		try {
			
			
			statement=connection.prepareStatement(query);
			statement.setInt(1, idPartida);
			statement.setInt(2, numTurno);
			resultSet=statement.executeQuery();
			resultSet.next();
			Turno t;
			t=new Turno(
						resultSet.getInt("id_partida"),
						resultSet.getInt("id_carta_jugador"),
						resultSet.getInt("id_carta_cpu"),
						resultSet.getInt("caracteristica"),
						resultSet.getInt("num_turno"),
						resultSet.getBoolean("ataque"),
						resultSet.getInt("resultado")
						);
						
			
			return t;
			
		} catch (SQLException e) {
			return null;
		}
		
	
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
	
	public int updateTurno(int idPartida,int numTurno,Turno t) {
		
		String query="UPDATE turnos SET id_carta_jugador=?,caracteristica=?,id_carta_cpu=?,ataque=?,resultado=? "
				+ " WHERE id_partida=? and num_turno=?";
		
		try {
			PreparedStatement ps= connection.prepareStatement(query);
			ps.setInt(1, t.getCartaJugador());
			ps.setInt(2, t.getCaracteristica());
			ps.setInt(3, t.getCartaCpu());
			ps.setBoolean(4,t.isAtaque());
			ps.setInt(5, t.getResultado());
			ps.setInt(6, idPartida);
			ps.setInt(7, numTurno);
			int result=ps.executeUpdate();
			ps.close();
			return result;
			
		} catch (SQLException e) {
		return 0;
		}
		
	}
	
	public int deleteTurno(int idPartida,int numTurno) {
		
		String query ="DELETE FROM turnos WHERE id_partida =? and num_turno =?";
		
		try {
			PreparedStatement ps= connection.prepareStatement(query);
			ps.setInt(1, idPartida);
			ps.setInt(2, numTurno);
			
			int result=ps.executeUpdate();
			ps.close();
			return result;

			
		}catch(SQLException e) {
			return 0;
		}
	}
	

}
