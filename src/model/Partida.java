package model;

public class Partida {
	
	
	public enum Resultados{
		EN_CURSO,GANADA,PERDIDA,EMPATE
	}
	
	private int idPartida;
	private int idPlayer;
	private boolean esPrimero;
	private Resultados resultado;
	public Partida(int idPartida, int idPlayer, boolean esPrimero, int resultado) {
		super();
		this.idPartida = idPartida;
		this.idPlayer = idPlayer;
		this.esPrimero = esPrimero;
		this.resultado= Resultados.values()[resultado];
		
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
	public Resultados getResultado() {
		return resultado;
	}
	public void setResultado(Resultados resultado) {
		this.resultado = resultado;
	}
	
	
	
	
	

}
