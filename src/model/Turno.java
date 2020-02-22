package model;

public class Turno {
	
	private int partida;
	private Carta CartaJugador;
	private Carta CartaCpu;
	private Caracteristicas caracteristica;
	private boolean ataque;
	private int resultado;
	
	public Turno() {
		
	}
	
	public Turno(int partida, Carta cartaJugador, Carta cartaCpu,Caracteristicas caracteristica, boolean ataque, int resultado) {
		super();
		this.partida = partida;
		CartaJugador = cartaJugador;
		CartaCpu = cartaCpu;
		this.caracteristica=caracteristica;
		this.ataque = ataque;
		this.resultado = resultado;
	}

	public int getPartida() {
		return partida;
	}

	public void setPartida(int partida) {
		this.partida = partida;
	}

	public Carta getCartaJugador() {
		return CartaJugador;
	}

	public void setCartaJugador(Carta cartaJugador) {
		CartaJugador = cartaJugador;
	}

	public Carta getCartaCpu() {
		return CartaCpu;
	}

	public void setCartaCpu(Carta cartaCpu) {
		CartaCpu = cartaCpu;
	}

	public Caracteristicas getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(Caracteristicas caracteristica) {
		this.caracteristica = caracteristica;
	}

	public boolean isAtaque() {
		return ataque;
	}

	public void setAtaque(boolean ataque) {
		this.ataque = ataque;
	}

	public int getResultado() {
		return resultado;
	}

	public void setResultado(int resultado) {
		this.resultado = resultado;
	}
	
	
	
	

}
