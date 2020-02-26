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
	public CartaEstadistica(int idCarta) {
		super();
		this.idCarta = idCarta;
		this.ganadas =0;
		this.perdidas = 0;
		this.empatadas = 0;
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
	
	public void addEmpatadas() {
		empatadas++;
	}
	public void addGanadas() {
		ganadas++;
	}
	public void addPerdidas() {
		perdidas++;
	}
	
	

}
