package model;

public class Jugador {
	
	private String correo;
	private String contrase�a;
	private String nickname;
	public Jugador(String correo, String contrase�a, String nickname) {
		super();
		this.correo = correo;
		this.contrase�a = contrase�a;
		this.nickname = nickname;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getContrase�a() {
		return contrase�a;
	}
	public void setContrase�a(String contrase�a) {
		this.contrase�a = contrase�a;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	

}
