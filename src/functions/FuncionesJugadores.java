package functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.*;

public class FuncionesJugadores {
	
	private Connection connection;
	private static FuncionesJugadores instance=null;
	
	private FuncionesJugadores() {
		connection=DBConnection.getInstance();
	}
	
	public static FuncionesJugadores getInstance() {
		if(instance==null) {
			instance=new FuncionesJugadores();
		}
		return instance;
	}
	
	
	public ArrayList<Jugador> getJugadores(){
		
		String query="select * from jugadores";
		
		Statement statement;
		ResultSet resultSet;
		ArrayList<Jugador> jugadores=new ArrayList();
		
		try {
			statement=connection.createStatement();
			resultSet=statement.executeQuery(query);
			
			while(resultSet.next()) {
				
				jugadores.add(new Jugador(
						resultSet.getString("correo"),
						resultSet.getString("contrasenya"),
						resultSet.getString("nickname")
						));
			}
			statement.close();
		} catch (SQLException e) {
			return null;
		}
		return jugadores;
		
		
	}
	
	public boolean addJugador(Jugador j) {
		
		String query="insert into jugadores (correo,contrasenya,nickname) "
				+ "values (?,?,?)";
		
		try {
			PreparedStatement preparedStatement= connection.prepareStatement(query);
			preparedStatement.setString(1,j.getCorreo());
			preparedStatement.setString(2,j.getContraseña());
			preparedStatement.setString(3,j.getNickname());
			boolean response=preparedStatement.execute();
			preparedStatement.close();
			return response;


		} catch (SQLException e) {
			return false;
			
		}
		
		
		
	}
	

}
