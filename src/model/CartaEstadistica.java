package model;

/**
 * Clase utilizada para calcular el winrate de las cartas
 * permite almacenar sus estadisticas
 * @author user
 *
 */
public class CartaEstadistica {
	
	private int idCarta;
	private int ganadas;
	private int perdidas;
	private int empatadas;
	public CartaEstadistica(int idCarta, int ganadas, int perdidas, int empatadas) {
		super();
		this.idCarta = idCarta;
		this.ganadas = ganadas;
		this.perdidas = perdidas;
		this.empatadas = empatadas;
	}
	public int getIdCarta() {
		return idCarta;
	}
	public void setIdCarta(int idCarta) {
		this.idCarta = idCarta;
	}
	public int getGanadas() {
		return ganadas;
	}
	public void setGanadas(int ganadas) {
		this.ganadas = ganadas;
	}
	public int getPerdidas() {
		return perdidas;
	}
	public void setPerdidas(int perdidas) {
		this.perdidas = perdidas;
	}
	public int getEmpatadas() {
		return empatadas;
	}
	public void setEmpatadas(int empatadas) {
		this.empatadas = empatadas;
	}
	
	

}
