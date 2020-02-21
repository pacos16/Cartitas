package model;

public class Jugador {
	
	private String correo;
	private String contraseña;
	private String nickname;
	public Jugador(String correo, String contraseña, String nickname) {
		super();
		this.correo = correo;
		this.contraseña = contraseña;
		this.nickname = nickname;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	

}
