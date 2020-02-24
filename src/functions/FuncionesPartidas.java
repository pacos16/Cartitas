package functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Partida;
import model.ResultadosPartidas;

public class FuncionesPartidas {
	
	private Connection connection;
	private static FuncionesPartidas instance=null;
	
	private FuncionesPartidas() {
		connection=DBConnection.getInstance();
	}
	
	public static FuncionesPartidas getInstance() {
		if(instance==null) {
			instance=new FuncionesPartidas();
		}
		return instance;
	}
	
	
	
	public ArrayList<Partida> getPartidas(){
		String query="SELECT * FROM partidas";
		
		Statement s;
		ResultSet rs;
		ArrayList<Partida> partidas=new ArrayList();
		
		try {
			s=connection.createStatement();
			rs=s.executeQuery(query);
			while(rs.next()) {
				partidas.add(new Partida(
						rs.getInt("id_partida"),
						rs.getInt("id_player"),
						rs.getBoolean("esPrimero"),
						rs.getInt("resultado")
						));
			}
			return partidas;
		} catch (SQLException e) {
			return null;
			
		}
		
	}
	
	public Partida getUnaPartida(int idPartida){
		String query="SELECT * FROM partidas where id_partida=?";
		
		PreparedStatement ps;
		ResultSet rs;
		Partida p;
		
		try {
			ps=connection.prepareStatement(query);
			ps.setInt(1, idPartida);
			rs=ps.executeQuery();
			rs.next();
			p=new Partida(
					rs.getInt("id_partida"),
					rs.getInt("id_player"),
					rs.getBoolean("esPrimero"),
					rs.getInt("resultado")
					);
			ps.close();
			return p;
			
		
			
			
		} catch (SQLException e) {
			return null;
			
		}
		
	}
	
	public boolean postPartida(Partida partida) {
		
		String query="INSERT INTO partidas (id_partida,id_player,esPrimero,resultado) "
				+ " VALUES(null,?,?,?)";
		
		PreparedStatement ps;
		
		try {
			ps=connection.prepareStatement(query);
			ps.setInt(1,partida.getIdPlayer());
			ps.setBoolean(2, partida.isEsPrimero());
			ps.setInt(3, partida.getResultado());
			ps.execute();
			ps.close();
			return true;
		}catch(SQLException e) {
			return false;
		}
		
	}
	
	public int putPartida(int idPartida,Partida partida) {
		String query="UPDATE partidas SET id_player=?,esPrimero=?,resultado=? "
				+ "WHERE id_partida=?";
		
		PreparedStatement ps;
		
		try {
			ps=connection.prepareStatement(query);
			ps.setInt(1,partida.getIdPlayer());
			ps.setBoolean(2, partida.isEsPrimero());
			ps.setInt(3, partida.getResultado());
			int result=ps.executeUpdate();
			ps.close();
			return result;
		}catch(SQLException e) {
			return 0;
		}
		
	}
	
	public int setPartidaResult(int idPartida, ResultadosPartidas resultado) {
		String query="UPDATE partidas SET resultado=? "
				+ "WHERE id_partida=?";
		
		PreparedStatement ps;
		
		try {
			ps=connection.prepareStatement(query);
			ps.setInt(1, resultado.ordinal());
			ps.setInt(2,idPartida);
			int result=ps.executeUpdate();
			ps.close();
			return result;
		}catch(SQLException e) {
			return 0;
		}
		
		
	}
	
	public int deletePartida(int idPartida) {
		String query="DELETE FROM partidas  "
				+ "WHERE id_partida=?";
		
		PreparedStatement ps;
		
		try {
			ps=connection.prepareStatement(query);
			ps.setInt(1,idPartida);
			
			int result=ps.executeUpdate();
			ps.close();
			return result;
		}catch(SQLException e) {
			return 0;
		}
		
	}
	
	
}
