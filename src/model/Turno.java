package model;

/**
 * Clase turno
 * Esta clase es de suma importancia para el funcionamiento de la aplicación
 * ya que se usa como medio de comunicación entre el cliente y el servidor 
 * además de modelo para guardarlo en la base de datos.
 * @author user
 *
 */
public class Turno {
	
	private int partida;
	private int cartaJugador;
	private int cartaCpu;
	private int caracteristica;
	private int numTurno;
	private boolean ataque;
	private int resultado;
	
	public Turno() {
		int partida=0;
		int cartaJugador=0;
		int cartaCpu=0;
		int caracteristica=0;
		int numTurno=0;
		boolean ataque=false;
		int resultado=0;
		
	}
	
	public Turno(int partida, int cartaJugador, int cartaCpu,int caracteristica,int numTurno, boolean ataque, int resultado) {
		super();
		this.partida = partida;
		this.cartaJugador = cartaJugador;
		this.cartaCpu = cartaCpu;
		this.numTurno=numTurno;
		this.caracteristica=caracteristica;
		this.ataque = ataque;
		this.resultado = resultado;
	}

	public int getNumTurno() {
		return numTurno;
	}

	public void setNumTurno(int numTurno) {
		this.numTurno = numTurno;
	}

	public int getPartida() {
		return partida;
	}

	public void setPartida(int partida) {
		this.partida = partida;
	}

	public int getCartaJugador() {
		return cartaJugador;
	}

	public void setCartaJugador(int cartaJugador) {
		this.cartaJugador = cartaJugador;
	}

	public int getCartaCpu() {
		return cartaCpu;
	}

	public void setCartaCpu(int cartaCpu) {
		this.cartaCpu = cartaCpu;
	}

	public int getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(int caracteristica) {
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

	@Override
	public String toString() {
		return "Turno [partida=" + partida + ", cartaJugador=" + cartaJugador + ", cartaCpu=" + cartaCpu
				+ ", caracteristica=" + caracteristica + ", numTurno=" + numTurno + ", ataque=" + ataque
				+ ", resultado=" + resultado + "]";
	}
	
	
	
	

}
