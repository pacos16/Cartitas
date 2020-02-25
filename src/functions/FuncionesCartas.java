package functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Carta;


/**
 * Funciones Cartas
 * @author Paco Signes
 * Clase singleton que gestiona las conexiones a la base de datos de las cartas
 * Obtiene las cartas y se encarga de todas las fuciones relacionadas
 * con ellas.
 */
public class FuncionesCartas {
	
	private Connection connection;
	private static FuncionesCartas instance=null;
	
	private FuncionesCartas() {
		connection=DBConnection.getInstance();
	}
	
	public static FuncionesCartas getInstance() {
		if(instance==null) {
			instance=new FuncionesCartas();
		}
		return instance;
	}
	
	
	public ArrayList<Carta> getCartas(){
		ArrayList<Carta> cartas=new ArrayList<>();

		Statement statement;
		ResultSet resultSet;
		Carta carta;
		
		int id;
		String  marca;
		String modelo;
		int motor;
		int cilindros;
		String potencia;
		int revoluciones;
		int velocidad; 
		float consumo;

		try {

			String query = "SELECT * FROM cartas";

			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {

				id = resultSet.getInt("id");
				marca = resultSet.getString("marca");
				modelo = resultSet.getString("modelo");
				motor = resultSet.getInt("motor");
				cilindros = resultSet.getInt("cilindros");
				potencia = resultSet.getString("potencia");
				revoluciones = resultSet.getInt("rev_x_min");
				velocidad = resultSet.getInt("velocidad");
				consumo = resultSet.getFloat("consumo");

				carta = new Carta(id, marca, modelo, motor, cilindros, potencia,
						revoluciones,velocidad, consumo);

				cartas.add(carta);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cartas;

	}
	
	
	public boolean addCarta(Carta carta) {
		
		String query= "INSERT INTO cartas "
				+ "(id,marca,modelo,motor,cilindros,potencia,rev_x_min,velocidad,consumo) "+
				"values (?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(query);
			ps.setInt(1, carta.getId());
			ps.setString(2,carta.getMarca());
			ps.setString(3,carta.getModelo());
			ps.setInt(4,carta.getMotor());
			ps.setInt(5, carta.getCilindros());
			ps.setString(6, carta.getPotencia());
			ps.setInt(7,carta.getRevolucinoes());
			ps.setInt(8,carta.getVelocidad());
			ps.setFloat(9,carta.getConsumo());
			ps.execute();
			ps.close();
			return true;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return false;
				
	}
	
	public boolean deleteCarta(int id) {
		
		String query="delete from cartas where id=?";
		
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(query);
			ps.setInt(1, id);
			
			ps.execute();
			ps.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
		
		
	}
	
	public boolean updateCarta(int id,Carta carta) {
		
		String query="UPDATE cartas SET marca=?,modelo=?,motor=?,cilindros=?,potencia=?,rev_x_min=?,velocidad=?,consumo=? "
				+ " WHERE id=?";
		
		try {
			PreparedStatement ps= connection.prepareStatement(query);
			ps.setString(1, carta.getMarca());
			ps.setString(2,carta.getModelo());
			ps.setInt(3,carta.getMotor());
			ps.setInt(4, carta.getCilindros());
			ps.setString(5, carta.getPotencia());
			ps.setInt(6, carta.getRevolucinoes());
			ps.setInt(7,carta.getVelocidad());
			ps.setFloat(8, carta.getConsumo());
			ps.setInt(9, id);
			ps.execute();
			ps.close();
			return true;
			
		} catch (SQLException e) {
		return false;
		}
		
	}
	
	public Carta getUnaCarta(int id) {
		String query= "SELECT * FROM cartas WHERE id=?";
		ResultSet resultSet;
	
		String  marca;
		String modelo;
		int motor;
		int cilindros;
		String potencia;
		int revoluciones;
		int velocidad; 
		float consumo;
		
		try {
			PreparedStatement ps= connection.prepareStatement(query);
			ps.setInt(1,id);
			
			resultSet=ps.executeQuery();
			resultSet.next();
			
			marca = resultSet.getString("marca");
			modelo = resultSet.getString("modelo");
			motor = resultSet.getInt("motor");
			cilindros = resultSet.getInt("cilindros");
			potencia = resultSet.getString("potencia");
			revoluciones = resultSet.getInt("rev_x_min");
			velocidad = resultSet.getInt("velocidad");
			consumo = resultSet.getFloat("consumo");
			return new Carta(id, marca, modelo, motor, cilindros, potencia,
					revoluciones,velocidad, consumo);

			
		} catch (SQLException e) {
			return null;
		}
	}

}
