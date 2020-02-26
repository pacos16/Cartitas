# Api Cartas

## 1. Introducción

El objetivo de esta práctica es consturir un servicio ApiRest para jugar a un juego de cartas de coches. Para ello hemos construido un sistema de endpoints que permiten el CRUD para todas las tablas existentes y otros endpoints para la mecánica de juego.

En el archivo de la entrega encontrará un documento Javadoc con toda la información sobre las funciones del proyecto.
Este archivo se encuentra en ApiRESTFul/doc/index.html

## 2. Herramientas y requerimientos

Para este proyecto hemos utilizado un servidor Tomcat 9 para correr nuestra aplicación. Como motor de base de datos hemos elegido MySql.

Esta ha sido desarrollada en Java con la libreria 1.8. Las librerias externas ulilizadas para el desarrollo del servidor son :

<!-- https://mvnrepository.com/artifact/org.glassfish.jersey.core/jersey-client -->
	<dependency>
	    <groupId>org.glassfish.jersey.core</groupId>
	    <artifactId>jersey-client</artifactId>
	    <version>2.30</version>
	</dependency>
	<!-- https://mvnrepository.com/artifact/org.glassfish.jersey.containers/jersey-container-servlet -->
	<dependency>
	    <groupId>org.glassfish.jersey.containers</groupId>
	    <artifactId>jersey-container-servlet</artifactId>
	    <version>2.30</version>
	</dependency>
	<dependency>
	    <groupId>org.glassfish.jersey.inject</groupId>
	    <artifactId>jersey-hk2</artifactId>
	    <version>2.28</version>
	</dependency>
	<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>8.0.19</version>
	</dependency>	
		<!-- https://mvnrepository.com/artifact/com.google.code.gson/gson -->
	<dependency>
	    <groupId>com.google.code.gson</groupId>
	    <artifactId>gson</artifactId>
	    <version>2.8.6</version>
	</dependency>

## 3. Estructura del código

Por motivos de escalabilidad y visibilidad hemos decidido crear varios paquetes para estructurar nuestro código:

### Paquete rest

Este paquete contiene todos los endpoints a las funciones del juego, contiene las clases ApiCartas, ApiEstadisticas, ApiJugadores, ApiMetodosJuego, ApiPartidas, ApiTurnos.
Cada una de ellas tiene un path diferente y contiene los métodos GET, POST, PUT y DELETE para cada tabla.

### Paquete functions

Aquí se encuentran todas las clases que proporcionan las funciones a los endpoints. Debido a que son clases funcionales, hemos aplicado el patron de diseño singleton a cada una de ellas. Por motivos de estructura del código hemos ordenado las fuciones en las siguientes clases.
Estadisticas, FuncionesCartas, FuncionesJugadores, FuncionesPartidas, FuncionesTurnos, MetodosJuego.
Este paquete también contiene la clase DBConnection, que proporciona conexión a la base de datos mediante jdbc.

### Paquete model

En este paquete representamos todos los objetos que vamos a utilizar en nuestro servidor.

#### Clases

Carta, CartaEstadistica, Jugador, JugadorEstadisticas,Partida ,Turno.

#### Enums

Caracteristicas, ResultadosPartidas, Resultados turnos;
Estas enums aportan legibilidad al código.

## 4. Funcionamiento del juego

Esta es clase responsable de la mayoria de métodos del juego. Esta funcion recibe un objeto de la clase turno enviado por el cliente, lo procesa y lo guarda en la base de datos.

Además, segun el turno ,si esta en modo ataque o si se debe terminar la partida, envia un paquete al usuario con la información necesaria para poder continuar.

A continuación podemos ver los métodos mas relevantes de esta clase.

Esta clase utiliza los métodos del paquete functions vistos anteriormente para nutrirse de los datos que necesita.


```java

/**
 * Recibe turno
 * Esta funcion es la funcion de juego principal.
 * @param t  Recibe un objeto de la clase turno
 * @return devuelve un objeto de la clase turno segun lainformacion que contenga
 * Aqui decide que objeto envia de vuelta y que accionesse toman en la base de datos
 */
public Turno recibirTurno(Turno t) {
	
	/**
	 * Asigna una carta para combatir
	 */
	if(t.isAtaque()) {
		ArrayList<Carta> manoCpu=getCpuDraft(t.getPartid());
		t.setCartaCpu(manoCpu.get(0).getId());
		setCartaJugada(t.getPartida(),t.getCartaCpu());
	}
	/**
	 * Calcula y guarda el resultado
	 */
	ResultadosTurnos rt=calcularResultado(t);
	t.setResultado(rt.ordinal());
	
	FuncionesTurnos ft=FuncionesTurnos.getInstance();
	ft.addTurno(t);
	
	/**
	 * Si es fin partida
	 */
	if(t.getNumTurno()==6) {
		ResultadosPartidasresultado=calcularResultadoPartida(t.getPartida();
		FuncionesPartidas fp=FuncionesPartidasgetInstance();
		fp.setPartidaResult(t.getPartida(),resultado);
	}else {
		/**
		 * Aqui genera una mano de vuelta
		 */
		if(t.isAtaque()) {
			ArrayList<Carta> manoCpu=getCpuDraft(tgetPartida());
			Turno turno=new Turno();
			turno.setCartaCpu(manoCpu.get(0).getId());
			Random random=new Random();
			int r=random.nextInt(7);
			turno.setCaracteristica(r);
			turno.setResultado(t.getResultado());
			setCartaJugada(t.getPartida(),turnogetCartaCpu());
			t=turno;
		}
	}
	return t;
}

/**
 * Calcula el resultado de las partidas
 * Lo hace recorriendo la tabla turnos y sumando losresultados.
 * @param idPartida id de la partida 
 * @return devuelbve el resultado 
 */
public ResultadosPartidas calcularResultadoPartida(intidPartida) {
	FuncionesTurnos ft=FuncionesTurnos.getInstance();
	ArrayList<Turno> turnos=ft.getTurnosPartid(idPartida);
	int ganadasJugador=0;
	int ganadasCpu=0;
	for(Turno t:turnos) {
		if(t.getResultado()==1) {
			ganadasJugador++;
		}else if(t.getResultado()==2) {
			ganadasCpu++;
		}
	}
	if(ganadasJugador>ganadasCpu) {
		return ResultadosPartidas.GANADA;
	}else if(ganadasJugador==ganadasCpu) {
		return ResultadosPartidas.EMPATE;
	}else {
		return ResultadosPartidas.PERDIDA;
	}
	
	
}


/**
 * 
 * @param t  Recibe un turno
 * @return devuelve el resultado
 */
public ResultadosTurnos calcularResultado(Turno t) {
	ArrayList<Carta>cartas=FuncionesCartas.getInstance()getCartas();
	Carta cartaPlayer=cartas.get(t.getCartaJugador()-1);
	Carta cartaCpu=cartas.get(t.getCartaCpu()-1);
	Caracteristicas caracteristica=Caracteristicas.value()[t.getCaracteristica()];
	switch(caracteristica) {
	case MOTOR:
		if(cartaPlayer.getMotor()>cartaCpu.getMotor()) {
			return ResultadosTurnos.GANADA;
		}else if(cartaPlayer.getMotor()==cartaCpugetMotor()) {
			return ResultadosTurnos.EMPATE;
		}else {
			return ResultadosTurnos.PERDIDA;
		}

	case CILINDROS:
		if(cartaPlayer.getCilindros()>cartaCpugetCilindros()) {
			return ResultadosTurnos.GANADA;
		}else if(cartaPlayer.getCilindros()==cartaCpugetCilindros()) {
			return ResultadosTurnos.EMPATE;
		}else {
			return ResultadosTurnos.PERDIDA;
		}
	case POTENCIA:
		int potenciaPlayer;
		int potenciaCpu;
		potenciaPlayer=Integer.valueOf(cartaPlayergetPotencia().split("/")[0]);
		potenciaCpu=Integer.valueOf(cartaCpu.getPotenci().split("/")[0]);
		if(potenciaPlayer>potenciaCpu) {
			return ResultadosTurnos.GANADA;
		}else if(cartaPlayer.getPotencia().equal(cartaCpu.getPotencia())) {
			return ResultadosTurnos.EMPATE;
		}else {
			return ResultadosTurnos.PERDIDA;
		}
	case REVOLUCIONES:
		if(cartaPlayer.getRevolucinoes()>cartaCpugetRevolucinoes()) {
			return ResultadosTurnos.GANADA;
		}else if(cartaPlayer.getRevolucinoes()==cartaCpugetRevolucinoes()) {
			return ResultadosTurnos.EMPATE;
		}else {
			return ResultadosTurnos.PERDIDA;
		}
	case VELOCIDAD:
		if(cartaPlayer.getVelocidad()>cartaCpugetVelocidad()) {
			return ResultadosTurnos.GANADA;
		}else if(cartaPlayer.getVelocidad()==cartaCpugetVelocidad()) {
			return ResultadosTurnos.EMPATE;
		}else {
			return ResultadosTurnos.PERDIDA;
		}
	case CONSUMO:
		if(cartaPlayer.getConsumo()<cartaCpu.getConsumo() {
			return ResultadosTurnos.GANADA;
		}else if(cartaPlayer.getVelocidad()==cartaCpugetVelocidad()) {
			return ResultadosTurnos.EMPATE;
		}else {
			return ResultadosTurnos.PERDIDA;
		}
	}
	return null;
}

/**
 * Generadores de drafts 
 * @return devuelve un ArrayList Carta con 12 cartasdiferentes
 */
public ArrayList<Carta> generateDraft(){
	FuncionesCartas fc=FuncionesCartas.getInstance();
	Carta[] cartas= fc.getCartas().toArray(new Carta[fcgetCartas().size()]);
	ArrayList<Carta> alist=new ArrayList();
	int max=cartas.length;
	Random random=new Random();
	for(int i=0;i<12;i++) {
		int r=random.nextInt(max);
		alist.add(cartas[r]);
		cartas[r]=cartas[max-1];
		max--;
	}
	return alist;
}

/**
 * Busca en la tabla cpu_drafts para ver que cartas 
 * tiene disponibles la cpu para jugar
 * @param idPartida
 * @return
 */
public ArrayList<Carta> getCpuDraft(int idPartida){
	String query="SELECT * FROM cpu_drafts WHEREid_partida=?";
	FuncionesCartas fc=FuncionesCartas.getInstance();
	ArrayList<Carta> cartas=fc.getCartas();
	ArrayList<Carta> mano=new ArrayList();
	
	PreparedStatement ps;
	ResultSet rs;
	
	try {
		ps=connection.prepareStatement(query);
		ps.setInt(1, idPartida);
		rs=ps.executeQuery();
		
		while(rs.next()) {
			if(!rs.getBoolean("jugada")) {
				mano.add(cartas.get(rs.getInt("id_carta"-1));
			}
		}
	} catch (SQLException e) {
		return null;
	}
	
	return mano;
}

/**
 * Funcion que actualiza el valor de la tabla cpu_draftspara marcar la carta
 * como jugada
 * @param idPartida idpartida
 * @param idCarta idCarta
 * @return
 */
public int setCartaJugada(int idPartida,int idCarta) {
	String query="UPDATE cpu_drafts SET jugada=1 "
			+ "WHERE id_partida=? AND id_carta=?";
	PreparedStatement ps;
	
	try {
		ps=connection.prepareStatement(query);
		ps.setInt(1, idPartida);
		ps.setInt(2, idCarta);
		return ps.executeUpdate();
		
	} catch (SQLException e) {
		return 0;
	}
}
```