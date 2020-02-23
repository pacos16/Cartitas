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
	
	public Jugador getUnJugador(String correo) {
		
		String query="SELECT * FROM jugadores WHERE correo=?";
		
		PreparedStatement statement;
		ResultSet resultSet;
		
		
		try {
			
			
			statement=connection.prepareStatement(query);
			statement.setString(1, correo);
			resultSet=statement.executeQuery();
			resultSet.next();
			
			return new Jugador(
					resultSet.getString("correo"),
					resultSet.getString("contrasenya"),
					resultSet.getString("nickname")
					);
			
			
			
		} catch (SQLException e) {
			System.out.println("Why?");
			return null;
		}
		
	
	}
	
	public boolean addJugador(Jugador j) {
		
		String query="insert into jugadores (correo,contrasenya,nickname) "
				+ "values (?,?,?)";
		
		try {
			PreparedStatement preparedStatement= connection.prepareStatement(query);
			preparedStatement.setString(1,j.getCorreo());
			preparedStatement.setString(2,j.getContraseña());
			preparedStatement.setString(3,j.getNickname());
			preparedStatement.execute();
			preparedStatement.close();
			return true;


		} catch (SQLException e) {
			return false;
			
		}
		
		
		
	}
	
	public int deleteJugador(String correo) {
		
		String query="delete from jugadores where correo=?";
		
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(query);
			ps.setString(1, correo);
			
			int x=ps.executeUpdate();
			ps.close();
			return x;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return 0;
		}
		
		
	}
	public boolean updateJugador(String correo,Jugador jugador) {
		
		String query="UPDATE jugadores SET contrasenya=?,nickname=? "
				+ " WHERE correo=?";
		
		try {
			PreparedStatement ps= connection.prepareStatement(query);
			ps.setString(1,jugador.getContraseña());
			ps.setString(2, jugador.getNickname());
			ps.setString(3, correo);
			boolean result=ps.execute();
			ps.close();
			return result;
			
		} catch (SQLException e) {
		return false;
		}
		
	}
	

}
