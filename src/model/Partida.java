package model;

public class Partida {
	

	
	private int idPartida;
	private int idPlayer;
	private boolean esPrimero;
	private int resultado;
	public Partida(int idPartida, int idPlayer, boolean esPrimero, int resultado) {
		super();
		this.idPartida = idPartida;
		this.idPlayer = idPlayer;
		this.esPrimero = esPrimero;
		this.resultado= resultado;
		
	}
	public int getIdPartida() {
		return idPartida;
	}
	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
	}
	public int getIdPlayer() {
		return idPlayer;
	}
	public void setIdPlayer(int idPlayer) {
		this.idPlayer = idPlayer;
	}
	public boolean isEsPrimero() {
		return esPrimero;
	}
	public void setEsPrimero(boolean esPrimero) {
		this.esPrimero = esPrimero;
	}
	public int getResultado() {
		return resultado;
	}
	public void setResultado(int resultado) {
		this.resultado = resultado;
	}
	
	
	
	
	

}
