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
 * Clase singleton que gestiona las conexiones a la base de datos
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
			
			return ps.execute();
			
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
			
			
			return ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
		
		
	}

}
