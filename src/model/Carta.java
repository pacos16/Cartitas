package model;

/**
 *Modelo carta
 * @author user
 *
 */
public class Carta  {
	
	
	private int id;
	private String marca;
	private String modelo;
	private int motor;
	private int cilindros;
	private String potencia;
	private int revolucinoes;
	private int velocidad;
	private float consumo;
	
	public Carta() {
		
	}
	
	public Carta(int id, String marca, String modelo, int motor, int cilindros, String potencia, int revolucinoes,
			int velocidad, float consumo) {
		super();
		this.id=id;
		this.marca = marca;
		this.modelo = modelo;
		this.motor = motor;
		this.cilindros = cilindros;
		this.potencia = potencia;
		this.revolucinoes = revolucinoes;
		this.velocidad = velocidad;
		this.consumo = consumo;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public int getMotor() {
		return motor;
	}
	public void setMotor(int motor) {
		this.motor = motor;
	}
	public int getCilindros() {
		return cilindros;
	}
	public void setCilindros(int cilindros) {
		this.cilindros = cilindros;
	}
	public String getPotencia() {
		return potencia;
	}
	public void setPotencia(String potencia) {
		this.potencia = potencia;
	}
	public int getRevolucinoes() {
		return revolucinoes;
	}
	public void setRevolucinoes(int revolucinoes) {
		this.revolucinoes = revolucinoes;
	}
	public int getVelocidad() {
		return velocidad;
	}
	public void setVelocidad(int velocidad) {
		this.velocidad = velocidad;
	}
	public float getConsumo() {
		return consumo;
	}
	public void setConsumo(float consumo) {
		this.consumo = consumo;
	}
	
	@Override
	public String toString() {
		return "Carta [id=" + id + ", marca=" + marca + ", modelo=" + modelo + ", motor=" + motor + ", cilindros="
				+ cilindros + ", potencia=" + potencia + ", revolucinoes=" + revolucinoes + ", velocidad=" + velocidad
				+ ", consumo=" + consumo + "]";
	}
	
	

}
