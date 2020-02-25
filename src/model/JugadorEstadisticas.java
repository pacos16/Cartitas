package model;
/**
 * Clase que almacena las estadisticas de los jugadores
 * @author user
 *
 */
public class JugadorEstadisticas implements Comparable<JugadorEstadisticas> {
	private String correo;
	int ganadas;
	int perdidas;
	int empatadas;
	
	public JugadorEstadisticas(String correo) {
		this.correo=correo;
		ganadas=0;
		perdidas=0;
		empatadas=0;
	}

	@Override
	public int compareTo(JugadorEstadisticas arg0) {
		if(this.ganadas>arg0.ganadas) {
			return 1;
		}else if(this.ganadas==arg0.ganadas) {
			return this.empatadas-arg0.empatadas;
		}
		return -1 ;
	}
	
	public void setResultado(int result) {
		if (result==1) {
			ganadas++;
		}else if(result==2) {
			perdidas++;
		}else if(result==3) {
			empatadas++;
		}
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
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
