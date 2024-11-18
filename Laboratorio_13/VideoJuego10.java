import java.util.*;

public class VideoJuego10 {
	private static int tamañoMax = 10;
	
	public static void Jugar(){
		Scanner sc = new Scanner(System.in);
		int turno = 1;
		
		int tamaño1 = (int)(Math.random()* tamañoMax + 1); //	Soldados del ejercito 1
		int tamaño2 = (int)(Math.random()* tamañoMax + 1); //	Soldados del ejercito 2
		Soldado[][] tablero = new Soldado[tamañoMax][tamañoMax]; // Creando un arreglo bidimensional de Soldados

		AsignarAtributos(tablero, tamaño1, 1);	
		AsignarAtributos(tablero, tamaño2, 2);	
		
		ImprimirDatos(tablero);
		ImprimirTablero(tablero);
		
		
		System.out.println("Soldado con mas vida del ejercito 1 es: ");
		EncontrarMayorVida(tablero, 1);
		System.out.println("Soldado con mas vida del ejercito 2 es: ");
		EncontrarMayorVida(tablero, 2);
		
		System.out.println("Promedio de Vida del Ejercito 1: " + PromedioVida(tablero, 1) + "\n");
		System.out.println("Promedio de Vida del Ejercito 2: " + PromedioVida(tablero, 2) + "\n");
		System.out.println("\n---------------------\n");
		
		System.out.println("Vida de todos los soldados:");
		ImprimirVida(tablero);
		
		System.out.println("Ranking de Vida segun Ordenamiento Burbuja");
		OrdenamientoBurbuja(tablero, SoldadosVivos(tablero));
		
		System.out.println("Ranking de Vida segun Ordenamiento por Insercion");
		OrdenamientoInsercion(tablero, SoldadosVivos(tablero));
		
		
		Jugada(tablero, turno);
	}
	
	public static void AsignarAtributos(Soldado[][] tablero, int n, int ejercito){
		for(int i = 0; i < n; i++){
			int x = (int)(Math.random()*tamañoMax);	// x representa Filas
			int y = (int)(Math.random()*tamañoMax);	// y representa a las columnas, siendo del 1 al 9
			if(tablero[x][y] == null){	// Si la posicion [y][x] esta vacia, crea un Soldado
				String nombre = "Soldado" + (ejercito) + "x" + (i + 1);
				int vida = (int)(Math.random()* 5 + 1);
				int ataque = (int)(Math.random()* 5 + 1);
				int defensa = (int)(Math.random()* 5 + 1);
				tablero[x][y] = new Soldado(nombre, vida, ejercito, x + 1, y + 1, ataque, defensa);
			} else {
				i--; //	Caso contrario, se regresa al ciclo
			}
		}
	}

	public static void ImprimirDatos(Soldado[][] tablero){
		for(int i = 0; i < tamañoMax; i++)		// Filas
			for(int j = 0; j < tamañoMax; j++)		// Controla las columnas
				if(tablero[i][j] != null)
					if(tablero[i][j].getVive()){
						System.out.print(tablero[i][j].getNombre() + "   ");
						System.out.print("Salud: " + tablero[i][j].getVidaActual() + "   ");
						System.out.println("Ejercito: " + tablero[i][j].getEjercito());
						System.out.print("Fila:" + tablero[i][j].getFila() + "   ");
						System.out.println("Columna: " + tablero[i][j].getColumna());
						System.out.print("ATQ:" + tablero[i][j].getAtaque() + "   ");
						System.out.println("DEF: " + tablero[i][j].getDefensa() + "\n");
					}	
		System.out.println("\n---------------------\n");
	}
	
	public static void ImprimirTablero(Soldado[][] tablero){
		for(int i = 0; i < tamañoMax; i++){	// Filas
				for(int j = 0; j < tamañoMax; j++){	// Columnas
					if(tablero[i][j] != null)	// Si el indice contiene un Soldado continua
						if(tablero[i][j].getVive()){		// Si el soldado esta vivo continua
							if(tablero[i][j].getEjercito() == 1) System.out.print("1 ");
							if(tablero[i][j].getEjercito() == 2) System.out.print("2 ");
						} else System.out.print("= ");
					else System.out.print("= ");
	
				}
				System.out.print("\n");		// Una vez acabada una fila, hace un salto de linea
		}
		System.out.println("\n---------------------\n");
	}
	
	public static void ImprimirTablero(Soldado[][] tablero, int ejercito){
		for(int i = 0; i < tamañoMax; i++){	// Filas
				for(int j = 0; j < tamañoMax; j++){	// Columnas
					if(tablero[i][j] != null)	// Si el indice contiene un Soldado continua
						if(tablero[i][j].getVive())		// Si el soldado esta vivo continua
							if(tablero[i][j].getEjercito() == ejercito) System.out.print(ejercito + " ");
							else System.out.print("= ");
						else System.out.print("= ");
					else System.out.print("= ");
				}
				System.out.print("\n");		// Una vez acabada una fila, hace un salto de linea
		}
		System.out.println("\n---------------------\n");
	}
	
	public static void EncontrarMayorVida(Soldado[][] tablero, int ejercito){
		int mayor = Integer.MIN_VALUE;

		Soldado masVida = new Soldado();
		for(int i = 0; i < tamañoMax; i++)	// Filas
			for(int j = 0; j < tamañoMax; j++)	// Columnas
				if(tablero[j][i] != null)
					if(tablero[j][i].getVive()){
						if(tablero[j][i].getEjercito() == ejercito)	// Pasa si son del mismo ejercito
							if(tablero[j][i].getVidaActual() > mayor){
								mayor = tablero[j][i].getVidaActual();	//Si es mayor, actualiza la variable
								masVida = tablero[j][i];
							}
					}
		Imprimir(masVida);
	}
	
	public static void Imprimir(Soldado soldado){
		System.out.print(soldado.getNombre() + "   ");
		System.out.print("Salud: " + soldado.getVidaActual() + "   ");
		System.out.println("Ejercito: " + soldado.getEjercito());
		System.out.print("Fila:" + soldado.getFila() + "   ");
		System.out.println("Columna: " + soldado.getColumna());
		System.out.print("ATQ:" + soldado.getAtaque() + "   ");
		System.out.println("DEF: " + soldado.getDefensa() + "\n");
	}

	public static int PromedioVida(Soldado[][] tablero, int ejercito){
		int promedio = 0;
		int size = 0;
		for(int i = 0; i < tamañoMax; i++)	// Filas
			for(int j = 0; j < tamañoMax; j++)	// Columnas
				if(tablero[j][i] != null)		
					if(tablero[j][i].getVive() && tablero[j][i].getEjercito() == ejercito){
						promedio += tablero[j][i].getVidaActual();
						size++;
					}
		return promedio/size;
	}
	
	public static void ImprimirVida(Soldado[][] tablero){	
		for(int i = 0; i < tamañoMax; i++)	// Filas
			for(int j = 0; j < tamañoMax; j++)	// Columnas
				if(tablero[j][i] != null)
					if(tablero[j][i].getVive())
						System.out.println("Vida de " + tablero[j][i].getNombre() + ": " + tablero[j][i].getVidaActual());
		System.out.println("\n---------------------\n");		
	}
	
	public static void OrdenamientoBurbuja(Soldado[][] tablero, int n){
		Soldado temp = new Soldado();
		int contador = 0;
		Soldado[] lista = new Soldado[n];
		for(int i = 0; i < tamañoMax; i++)	// Filas
			for(int j = 0; j < tamañoMax; j++)	// Columnas
				if(tablero[j][i] != null){
					if(tablero[j][i].getVive()){
						lista[contador] = tablero[j][i];
						contador++;
					}
				}

		for(int i = 0; i < n - 1; i++)
			for(int j = 0; j < n - 1; j++)
				if(lista[j].getVidaActual() > lista[j+1].getVidaActual()){
					temp = lista[j+1];
					lista[j+1] = lista[j];
					lista[j] = temp;
				}
		for(int i = n - 1; i >= 0; i--){
			Imprimir(lista[i]);
		}
		System.out.println("\n---------------------\n");
	}

	public static void OrdenamientoInsercion(Soldado[][] tablero, int n){
		Soldado temp = new Soldado();
		int contador = 0;
		Soldado[] lista = new Soldado[n];
		for(int i = 0; i < tamañoMax; i++)	// Filas
			for(int j = 0; j < tamañoMax; j++)	// Columnas
				if(tablero[j][i] != null){
					if(tablero[j][i].getVive()){
						lista[contador] = tablero[j][i];
						contador++;
					}
				}

		for(int i = 1; i < n; i++){
			Soldado key = lista[i];
			int j = i - 1;
			while(j >= 0 && lista[j].getVidaActual() > key.getVidaActual()){
				lista[j + 1] = lista[j];
				j = j - 1;
			}
			lista[j + 1] = key;
		}

		for(int i = n - 1; i >= 0; i--){
			Imprimir(lista[i]);
		}
		System.out.println("\n---------------------\n");
	}
	
	public static void Jugada(Soldado[][] tablero, int turno){
		Scanner sc = new Scanner(System.in);
		boolean condicion = true;
		System.out.println("\nSe han creado " + SoldadosVivos(tablero) + " Soldados\n");
		
		while(condicion){
			
			turno %= 2;	//Los turnos pares seran los turnos del jugador 2
			if(turno == 0) turno = 2;	//Si es turno impar, jugara el jugador 1
			//Se pide al usuario la jugada y se verifica si es valida
			System.out.println("Si quieres salir al menu principal, coloca *cancelar* en cualquier momento");
			System.out.println("Jugador " + turno + ". Ingrese el soldado a mover del Ejercito " + turno + ". (Ejm. Soldado1x1)");
			String soldado = sc.nextLine();
			boolean condicion1 = VerificarSoldado(soldado, turno, tablero);
			
			if(soldado.equals("cancelar")){
				return;
			}
			
			while(!condicion1){
				System.out.println("Soldado no encontrado, por favor ingrese otro");
				soldado = sc.nextLine();
				if(soldado.equals("cancelar")){
					return;
				}
				condicion1 = VerificarSoldado(soldado, turno, tablero);
			}
						
			System.out.println("Ahora ingrese la direccion (w:up, s:down, d:rigth, a:left)");
			String direccion = sc.nextLine();
			
			if(direccion.equals("cancelar")){
				return;
			}
			
			boolean condicion2 = VerificarDireccion(direccion, turno, tablero, soldado);
			while(!condicion2){
				System.out.println("Direccion no admitida, por favor ingrese otro");
				direccion = sc.nextLine();
				if(direccion.equals("cancelar")){
					return;
				}
				condicion2 = VerificarDireccion(direccion, turno, tablero, soldado);
			}
			//Si hay enfrentamiento, se realiza
			SoldadoMoviendose(tablero, turno, soldado, direccion);
				
			ImprimirTablero(tablero);	//Despues de la jugada, se imprime el tablero y los datos de los soldados vivos
			ImprimirDatos(tablero);
			System.out.println("\nHay " + SoldadosVivos(tablero, 1) + " Soldados vivos del ejercito 1\n");
			System.out.println("\nHay " + SoldadosVivos(tablero, 2) + " Soldados vivos del ejercito 2\n");
			
			condicion = ActualizarCondicion(tablero); // Si no hay soldados vivos en ambos ejercitos, sera false

			turno++;
		}
		
		Ganador(tablero);
		
		
	}
	
	public static int SoldadosVivos(Soldado[][] tablero){
		int contador = 0;
		for(int i = 0; i < tamañoMax; i++)	// Filas
			for(int j = 0; j < tamañoMax; j++)	// Columnas
				if(tablero[i][j] != null)
					if(tablero[i][j].getVive())
						contador++;
		return contador;
	}
	
	public static int SoldadosVivos(Soldado[][] tablero, int ejercito){
		int contador = 0;
		for(int i = 0; i < tamañoMax; i++)	//Controla las filas
			for(int j = 0; j < tamañoMax; j++)	//Controla las columnas
				if(tablero[i][j] != null)
					if(tablero[i][j].getVive() && tablero[i][j].getEjercito() == ejercito)
						contador++;
		return contador;
	}
	
	public static boolean VerificarSoldado(String soldado, int turno, Soldado[][] tablero){
		for(int i = 0; i < tamañoMax; i++)	// Filas
			for(int j = 0; j < tamañoMax; j++)	// Columnas
				if(tablero[i][j] != null)
					if(tablero[i][j].getVive() && tablero[i][j].getEjercito() == turno)
						if(tablero[i][j].getNombre().equals(soldado))
							return true;
		return false;
	}
	
		public static boolean VerificarDireccion(String direccion, int turno, Soldado[][] tablero, String soldado){
		if(!(direccion.equals("w") || direccion.equals("s") || direccion.equals("a") || direccion.equals("d")))
			return false;

		for(int i = 0; i < tamañoMax; i++) {    // Filas
			for(int j = 0; j < tamañoMax; j++) {    // Columnas
				if(tablero[i][j] != null && tablero[i][j].getVive() && tablero[i][j].getEjercito() == turno && tablero[i][j].getNombre().equals(soldado)){
					if(direccion.equals("w") && i - 1 >= 0)    // Verifica movimiento hacia arriba
						return true;
					else if(direccion.equals("s") && i + 1 < tamañoMax)    // Verifica movimiento hacia abajo
						return true;
					else if(direccion.equals("a") && j - 1 >= 0)    // Verifica movimiento hacia la izquierda
						return true;
					else if(direccion.equals("d") && j + 1 < tamañoMax)    // Verifica movimiento hacia la derecha
						return true;
				}
			}
		}
		return false;	//Si no devuelve true, el movimiento no es valido
	}
	
	public static void SoldadoMoviendose(Soldado[][] tablero, int turno, String soldado, String direccion){
		int fila, columna;
		int contador = 0;	// Este contador es MUY IMPORTANTE, evitara que se llame el metodo Combate() mas de 1 vez, evitando posibles errores con s o d
		for(int i = 0; i < tamañoMax; i++)	// Filas
			for(int j = 0; j < tamañoMax; j++)	// Columnas
				if(tablero[i][j] != null && tablero[i][j].getVive() && tablero[i][j].getEjercito() == turno && tablero[i][j].getNombre().equals(soldado) && contador == 0){
					if(direccion.equals("w") && i - 1 >= 0){    // Verifica movimiento hacia arriba
						Combate(tablero, i, j, direccion);
						contador++;
					}else if(direccion.equals("s") && i + 1 < tamañoMax){    // Verifica movimiento hacia abajo
						Combate(tablero, i, j, direccion);
						contador++;
					}else if(direccion.equals("a") && j - 1 >= 0){    // Verifica movimiento hacia la izquierda
						Combate(tablero, i, j, direccion);
						contador++;
					}else if(direccion.equals("d") && j + 1 < tamañoMax){   // Verifica movimiento hacia la derecha
						Combate(tablero, i, j, direccion);
						contador++;
					}
				}
	}
	
	public static void Combate(Soldado[][] tablero, int x, int y, String direccion){
		if(direccion.equals("w")){	//La estructura se repite para cada una de las 4 letras, pero con sus variaciones
			if(tablero[x - 1][y] == null || !tablero[x - 1][y].getVive()){	//Verifica que no haya un Soldado vivo en el casillero a avanzar
				tablero[x - 1][y] = new Soldado(tablero[x][y].getNombre(), tablero[x][y].getVidaActual(), tablero[x][y].getEjercito(), x + 1 - 1, y + 1);
				tablero[x][y].morir();
			} else {
				int num = (int)(Math.random() * 100 + 1);
				double total = tablero[x][y].getVidaActual() + tablero[x - 1][y].getVidaActual();
				double probabilidadA = 100 * (tablero[x][y].getVidaActual() / total);
				System.out.println("Probabilidades de ganar:\nSoldado actual: " + probabilidadA + "% --- Soldado enemigo: " + (100 - probabilidadA) + "%");
				if(num < probabilidadA){		// El ganador seria el soldado 1
					tablero[x - 1][y] = new Soldado(tablero[x][y].getNombre(), tablero[x][y].getVidaActual() + 1, tablero[x][y].getEjercito(), x + 1 - 1, y + 1);
					tablero[x][y].morir();
					System.out.println("El Soldado actual gano");
				} else {		// Si no el ganador seria el soldado 2
					tablero[x - 1][y].setVidaActual(tablero[x - 1][y].getVidaActual() + 1);
					tablero[x][y].morir();
					System.out.println("El Soldado enemigo gano");
				}
			}
		} else if(direccion.equals("s")){
			if(tablero[x + 1][y] == null || !tablero[x + 1][y].getVive()){
				tablero[x + 1][y] = new Soldado(tablero[x][y].getNombre(), tablero[x][y].getVidaActual(), tablero[x][y].getEjercito(), x + 1 + 1, y + 1);
				tablero[x][y].morir();
			} else {
				int num = (int)(Math.random() * 100 + 1);
				double total = tablero[x][y].getVidaActual() + tablero[x + 1][y].getVidaActual();
				double probabilidadA = 100 * (tablero[x][y].getVidaActual() / total);
				System.out.println("Probabilidades de ganar:\nSoldado actual: " + probabilidadA + "% --- Soldado enemigo: " + (100 - probabilidadA) + "%");
				if(num < probabilidadA){		// El ganador seria el soldado 1
					tablero[x + 1][y] = new Soldado(tablero[x][y].getNombre(), tablero[x][y].getVidaActual() + 1, tablero[x][y].getEjercito(), x + 1 - 1, y + 1);
					tablero[x][y].morir();
					System.out.println("El Soldado actual gano");
				} else {		// Si no el ganador seria el soldado 2
					tablero[x + 1][y].setVidaActual(tablero[x + 1][y].getVidaActual() + 1);
					tablero[x][y].morir();
					System.out.println("El Soldado enemigo gano");
				}
			}
		} else if(direccion.equals("a")){
			if(tablero[x][y - 1] == null || !tablero[x][y - 1].getVive()){
				tablero[x][y - 1] = new Soldado(tablero[x][y].getNombre(), tablero[x][y].getVidaActual(), tablero[x][y].getEjercito(), x + 1, y + 1 - 1 );
				tablero[x][y].morir();
			} else {
				int num = (int)(Math.random() * 100 + 1);
				double total = tablero[x][y].getVidaActual() + tablero[x][y - 1].getVidaActual();
				double probabilidadA = 100 * (tablero[x][y].getVidaActual() / total);
				System.out.println("Probabilidades de ganar:\nSoldado actual: " + probabilidadA + "% --- Soldado enemigo: " + (100 - probabilidadA) + "%");
				if(num < probabilidadA){		// El ganador seria el soldado 1
					tablero[x][y - 1] = new Soldado(tablero[x][y].getNombre(), tablero[x][y].getVidaActual() + 1, tablero[x][y].getEjercito(), x + 1 - 1, y + 1);
					tablero[x][y].morir();
					System.out.println("El Soldado actual gano");
				} else {		// Si no el ganador seria el soldado 2
					tablero[x][y - 1].setVidaActual(tablero[x][y - 1].getVidaActual() + 1);
					tablero[x][y].morir();
					System.out.println("El Soldado enemigo gano");
				}
			}
		} else if(direccion.equals("d")){
			if(tablero[x][y + 1] == null || !tablero[x][y + 1].getVive()){
				tablero[x][y + 1] = new Soldado(tablero[x][y].getNombre(), tablero[x][y].getVidaActual(), tablero[x][y].getEjercito(), x + 1, y + 1 + 1);
				tablero[x][y].morir();
			} else {
				int num = (int)(Math.random() * 100 + 1);
				double total = tablero[x][y].getVidaActual() + tablero[x][y + 1].getVidaActual();
				double probabilidadA = 100 * (tablero[x][y].getVidaActual() / total);
				System.out.println("Probabilidades de ganar:\nSoldado actual: " + probabilidadA + "% --- Soldado enemigo: " + (100 - probabilidadA) + "%");
				if(num < probabilidadA){		// El ganador seria el soldado 1
					tablero[x][y + 1] = new Soldado(tablero[x][y].getNombre(), tablero[x][y].getVidaActual() + 1, tablero[x][y].getEjercito(), x + 1 - 1, y + 1);
					tablero[x][y].morir();
					System.out.println("El Soldado actual gano");
				} else {		// Si no el ganador seria el soldado 2
					tablero[x][y + 1].setVidaActual(tablero[x][y + 1].getVidaActual() + 1);
					tablero[x][y].morir();
					System.out.println("El Soldado enemigo gano");
				}
			}
		}
	}
	
	public static boolean ActualizarCondicion(Soldado[][] tablero){
		int ejercito1 = SoldadosVivos(tablero, 1);
		int ejercito2 = SoldadosVivos(tablero, 2);
		if(ejercito1 == 0 || ejercito2 == 0)
			return false;
		return true;
	}
	
	public static void Ganador(Soldado[][] tablero){
		if(SoldadosVivos(tablero, 1) == 0)
			System.out.println("El Ganador es el Ejercito 2");
		else if(SoldadosVivos(tablero, 2) == 0)
			System.out.println("El Ganador es el Ejercito 1");
		else System.out.println("Error");
	}
	
	public static boolean CrearSoldado(String texto, Soldado[][] tablero, int ejercitoOriginal){
		//si retorna false, nos saca del bucle, pero si retorna true, el bucle sigue
		if(texto.length() - texto.replace(",", "").length() != 6){
			System.out.println("Datos NO VALIDOS, por favor ingrese nuevos datos");
			return true;
		}
		
		String nombre = texto.substring(0,texto.indexOf(","));
		texto = texto.substring(texto.indexOf(",") + 1,texto.length());
		int vida = Integer.parseInt(texto.substring(0,texto.indexOf(",")));
		texto = texto.substring(texto.indexOf(",") + 1,texto.length());
		int ejercito = Integer.parseInt(texto.substring(0,texto.indexOf(",")));
		texto = texto.substring(texto.indexOf(",") + 1,texto.length());
		int fila = Integer.parseInt(texto.substring(0,texto.indexOf(",")));
		texto = texto.substring(texto.indexOf(",") + 1,texto.length());
		int columna = Integer.parseInt(texto.substring(0,texto.indexOf(",")));
		texto = texto.substring(texto.indexOf(",") + 1,texto.length());
		int ataque = Integer.parseInt(texto.substring(0,texto.indexOf(",")));
		texto = texto.substring(texto.indexOf(",") + 1,texto.length());
		int defensa = Integer.parseInt(texto);
		if(ejercito != ejercitoOriginal){
			System.out.println("Los EJERCITOS NO COINCIDEN, por favor ingrese nuevos datos");
			return true;
		}
		if(tablero[fila - 1][columna - 1] == null){
			tablero[fila - 1][columna - 1] = new Soldado(nombre, vida, ejercito, fila, columna, ataque, defensa);
			Imprimir(tablero[fila - 1][columna - 1]);
			ImprimirTablero(tablero);
			return false;
		}else if(!tablero[fila - 1][columna - 1].getVive()){
			tablero[fila - 1][columna - 1] = new Soldado(nombre, vida, ejercito, fila, columna, ataque, defensa);
			Imprimir(tablero[fila - 1][columna - 1]);
			ImprimirTablero(tablero);
			return false;
		}else System.out.println("Posicion NO VALIDA, por favor ingrese nuevos datos");
		return true;
	}
	
	public static boolean EliminarSoldado(String texto, Soldado[][] tablero, int ejercito){
		//si retorna false, nos saca del bucle, pero si retorna true, el bucle sigue
		for(int i = 0; i < tamañoMax; i++)
			for(int j = 0; j < tamañoMax; j++)
				if(tablero[i][j] != null)
					if(tablero[i][j].getNombre().equals(texto) && tablero[i][j].getVive()){
						if(ejercito != tablero[i][j].getEjercito()){
							System.out.println("Los EJERCITOS NO COINCIDEN, por favor ingrese nuevos datos");
							return true;
						}
						tablero[i][j].morir();
						ImprimirTablero(tablero);
						return false;
					}
		System.out.println("Soldado no encontrado, por favor ingrese un nombre valido");
		return true;			
	}
	
	public static boolean ClonarSoldado(String texto, Soldado[][] tablero, int ejercito){
		//si retorna false, nos saca del bucle, pero si retorna true, el bucle sigue
		for(int i = 0; i < tamañoMax; i++)
			for(int j = 0; j < tamañoMax; j++)
				if(tablero[i][j] != null)
					if(tablero[i][j].getNombre().equals(texto) && tablero[i][j].getVive()){
						if(ejercito != tablero[i][j].getEjercito()){
							System.out.println("Los EJERCITOS NO COINCIDEN, por favor ingrese nuevos datos");
							return true;
						}
						int x = tamañoMax - 1, y = tamañoMax - 1;
						while(x >= 0){
							if(tablero[x][y] == null || !tablero[x][y].getVive()){
								tablero[x][y] = new Soldado(tablero[i][j].getNombre(), tablero[i][j].getVidaActual(), ejercito, x + 1, y + 1, tablero[i][j].getAtaque(), tablero[i][j].getDefensa());
								ImprimirTablero(tablero);
								return false;
							} else {
								if(y > 0) y--;
								else{
									x--;
									y = tamañoMax - 1;
								}
							}
						}
					}
		System.out.println("Soldado no encontrado, por favor ingrese un nombre valido");
		return true;			
	}
	
	public static boolean ModificarSoldado(String texto, Soldado[][] tablero, int ejercito){
		//si retorna false, nos saca del bucle, pero si retorna true, el bucle sigue
		Scanner sc = new Scanner(System.in);
		for(int i = 0; i < tamañoMax; i++)
			for(int j = 0; j < tamañoMax; j++)
				if(tablero[i][j] != null)
					if(tablero[i][j].getNombre().equals(texto) && tablero[i][j].getVive()){
						boolean condicion1 = true;
						while(condicion1){	// El bucle termina cuando se ingrese un numero valido
							System.out.println("Seleccione que quiere modificar:\n1. VIDA\n2. ATAQUE\n3. DEFENSA\n4. SALIR\n");
							int opcion = sc.nextInt();
							boolean condicion2 = true;
							if(opcion == 1){
								while(condicion2){	// EL bucle termina cuando se ingrese un valor valido, mayor a 0 y menor a 6
									System.out.println("Ingrese el nuevo valor de VIDA");
									int vida = sc.nextInt();
									tablero[i][j].setVidaActual(vida);
									if(0 < vida && vida < 6) condicion2 = false;
								}
							} else if(opcion == 2){
								while(condicion2){	// EL bucle termina cuando se ingrese un valor valido, mayor a 0 y menor a 6
									System.out.println("Ingrese el nuevo valor de ATAQUE");
									int ataque = sc.nextInt();
									tablero[i][j].setAtaque(ataque);
									if(0 < ataque && ataque < 6) condicion2 = false;
								}
							} else if(opcion == 3){
								while(condicion2){	// EL bucle termina cuando se ingrese un valor valido, mayor a 0 y menor a 6
									System.out.println("Ingrese el nuevo valor de DEFENSA");
									int defensa = sc.nextInt();
									tablero[i][j].setDefensa(defensa);
									if(0 < defensa && defensa < 6) condicion2 = false;
								}
							} else if(opcion == 4){
								condicion1 = false;
							} else {
								System.out.println("Valor no valido. Ingrese de nuevo una opcion valida");
							}
						}
						return false;
					}
		System.out.println("Soldado no encontrado, por favor ingrese un nombre valido");
		return true;			
	}
	
	public static boolean CompararSoldados(String[] nombres, Soldado[][] tablero, int ejercito){
		//si retorna false, nos saca del bucle, pero si retorna true, el bucle sigue
		Scanner sc = new Scanner(System.in);
		int contador = 0;
		int[][] datos = new int[2][3];
		String[] soldados = new String[2];
		for(int i = 0; i < tamañoMax; i++)
			for(int j = 0; j < tamañoMax; j++)
				if(tablero[i][j] != null)
					if(contador < nombres.length && (tablero[i][j].getNombre().equals(nombres[0]) || tablero[i][j].getNombre().equals(nombres[1]) ) && tablero[i][j].getVive()){
						if(ejercito != tablero[i][j].getEjercito()){
							System.out.println("Los EJERCITOS NO COINCIDEN, por favor ingrese nuevos datos");
							return true;
						}
						if(contador < 2){	
							soldados[contador] = tablero[i][j].getNombre();
							datos[contador][0] = tablero[i][j].getVidaActual();
							datos[contador][1] = tablero[i][j].getAtaque();
							datos[contador][2] = tablero[i][j].getDefensa();
							contador++;
						}
					}
		if(contador < 2){
			System.out.println("Soldados no encontrados. Por favor ingrese nombres validos");
			return true;
		}
		String[] condiciones = {"NO","NO","NO","NO"};
		if(soldados[0].equals(soldados[1])) condiciones[0] = "SI";
		if(datos[0][0] == datos[1][0]) condiciones[1] = "SI";
		if(datos[0][1] == datos[1][1]) condiciones[2] = "SI";
		if(datos[0][2] == datos[1][2]) condiciones[3] = "SI";
		System.out.println("Los nombres " + condiciones[0] + " son iguales\n" +
		"Los atributos de VIDA " + condiciones[1] + " son iguales\n" +
		"Los atributos de ATAQUE " + condiciones[2] + " son iguales\n" +
		"Los atributos de DEFENSA " + condiciones[3] + " son iguales\n");
		return false;
	}
	
	public static boolean IntercambiarSoldados(String[] nombres, Soldado[][] tablero, int ejercito){
		//si retorna false, nos saca del bucle, pero si retorna true, el bucle sigue
		Scanner sc = new Scanner(System.in);
		int contador = 0;
		int[][] datos = new int[2][2];
		for(int i = 0; i < tamañoMax; i++)
			for(int j = 0; j < tamañoMax; j++)
				if(tablero[i][j] != null)
					if(contador < nombres.length && (tablero[i][j].getNombre().equals(nombres[0]) || tablero[i][j].getNombre().equals(nombres[1]) ) && tablero[i][j].getVive()){
						if(ejercito != tablero[i][j].getEjercito()){
							System.out.println("Los EJERCITOS NO COINCIDEN, por favor ingrese nuevos datos");
							return true;
						}
						if(contador < 2){
							datos[contador][0] = i;
							datos[contador][1] = j;
							contador++;
						}
					}
		if(contador < 2){
			System.out.println("Soldados no encontrados. Por favor ingrese nombres validos");
			return true;
		}
		Soldado soldado1 = new Soldado(tablero[datos[0][0]][datos[0][1]].getNombre(), tablero[datos[0][0]][datos[0][1]].getVidaActual(), ejercito, datos[1][0] + 1, datos[1][1] + 1, tablero[datos[0][0]][datos[0][1]].getAtaque(), tablero[datos[0][0]][datos[0][1]].getDefensa());
		Soldado soldado2 = new Soldado(tablero[datos[1][0]][datos[1][1]].getNombre(), tablero[datos[1][0]][datos[1][1]].getVidaActual(), ejercito, datos[0][0] + 1, datos[0][1] + 1, tablero[datos[1][0]][datos[1][1]].getAtaque(), tablero[datos[1][0]][datos[1][1]].getDefensa());
		tablero[datos[0][0]][datos[0][1]] = soldado2;
		tablero[datos[1][0]][datos[1][1]] = soldado1;
		System.out.println("Los soldados se han intercambiado de posicion\n");
		ImprimirTablero(tablero);
		return false;
	}
	
	public static boolean ImprimirSoldado(String texto, Soldado[][] tablero, int ejercito){
		//si retorna false, nos saca del bucle, pero si retorna true, el bucle sigue
		for(int i = 0; i < tamañoMax; i++)
			for(int j = 0; j < tamañoMax; j++)
				if(tablero[i][j] != null)
					if(tablero[i][j].getNombre().equals(texto) && tablero[i][j].getVive()){
						if(ejercito != tablero[i][j].getEjercito()){
							System.out.println("Los EJERCITOS NO COINCIDEN, por favor ingrese nuevos datos");
							return true;
						}
						Imprimir(tablero[i][j]);
						return false;
					}
		System.out.println("Soldado no encontrado, por favor ingrese un nombre valido");
		return true;
	}
	
	public static boolean SumarNiveles(Soldado[][] tablero, int ejercito){
		//si retorna false, nos saca del bucle, pero si retorna true, el bucle sigue
		Scanner sc = new Scanner(System.in);
		Soldado soldado = new Soldado("SumaDeNiveles");
		for(int i = 0; i < tamañoMax; i++)
			for(int j = 0; j < tamañoMax; j++)
				if(tablero[i][j] != null)
					if(tablero[i][j].getVive() && tablero[i][j].getEjercito() == ejercito){
						soldado.setVidaActual(soldado.getVidaActual() + tablero[i][j].getVidaActual());
						soldado.setAtaque(soldado.getAtaque() + tablero[i][j].getAtaque());
						soldado.setDefensa(soldado.getDefensa() + tablero[i][j].getDefensa());
						soldado.setVelocidad(soldado.getVelocidad() + tablero[i][j].getVelocidad());
					}
		System.out.println("Desea ver los datos del soldado creado?\n1. SI\n2. NO");
		int opcion = sc.nextInt();
		sc.nextLine();
		if(opcion == 1) Imprimir(soldado);
		return false;			
	}
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		boolean condicion1 = true;
		while(condicion1){	// El bucle termina cuando se eliga la opcion 3. SALIR
			boolean condicion2 = true; 
			System.out.println("\n**** ---------- MENU DEL JUEGO ---------- ****\n\n" +
			"Escriba el numero de la opcion que desea elegir\n" +
			"1. JUEGO RAPIDO\n2. JUEGO PERSONALIZADO\n3. SALIR");
			int opcion = sc.nextInt();
			if (opcion == 1){
				while(condicion2){	// El bucle termina cuando se eliga la opcion 2. SALIR AL MENU
					Jugar();
					System.out.println("Seleccione:\n1. NUEVO JUEGO\n2. SALIR AL MENU");
					opcion = sc.nextInt();
					if(opcion == 1){
						condicion2 = true;	// Se mantiene en el bucle while
					} else if (opcion == 2) {
						condicion2 = false;	// Sale del bucle while
					}
				}
			} else if (opcion == 2){
					int tamaño1 = (int)(Math.random()* tamañoMax + 1); //	Soldados del ejercito 1
					int tamaño2 = (int)(Math.random()* tamañoMax + 1); //	Soldados del ejercito 2
					Soldado[][] tablero = new Soldado[tamañoMax][tamañoMax]; // Creando un arreglo bidimensional de Soldados

					AsignarAtributos(tablero, tamaño1, 1);
					AsignarAtributos(tablero, tamaño2, 2);	
					
					ImprimirTablero(tablero);
					
					int ejercito = 0;
				
					while(condicion2){
						boolean condicion3 = true, condicion4 = true;
						while(condicion3){
							System.out.println("\nSeleccione el ejercito al cual quiere modificar\n");
							ejercito = sc.nextInt();
							if(ejercito == 1 || ejercito == 2){
								System.out.println("\nSe ha seleccionado el ejercito " + ejercito + "\n");
								condicion3 = false;
							} else System.out.println("\nERROR, Ejercito NO VALIDO. Ingrese los valores otra vez\n");
						}
						System.out.println("Seleccione la accion que desea realizar:\n\n" +
						"1. CREAR SOLDADO\n2. ELIMINAR SOLDADO\n3. CLONAR SOLDADO\n4. MODIFICAR SOLDADO\n" +
						"5. COMPARAR SOLDADOS\n6. INTERCAMBIAR SOLDADOS\n7. VER SOLDADO\n8. VER EJERCITO\n" +
						"9. SUMAR NIVELES\n10. JUGAR\n11. VOLVER\n");
						opcion = sc.nextInt();
						String texto;
						String[] nombres = new String[2];
						if(opcion == 1){
							sc.nextLine();
							if(SoldadosVivos(tablero, ejercito) >= 10){
								condicion4 = false;
								System.out.println("EL ejercito elegido no puede tener mas de 10 soldados, no se pueden crear mas");
							}
							while(condicion4){	
								System.out.println("Ingrese los datos del soldado de esta manera:" +
								"(Ejm. nombre,vida,ejercito,fila,columna,ataque,defensa)");
								texto = sc.nextLine();
								condicion4 = CrearSoldado(texto, tablero, ejercito);
							}
						} else if (opcion == 2) {
							sc.nextLine();
							if(SoldadosVivos(tablero, ejercito) < 1){
								condicion4 = false;
								System.out.println("EL ejercito no tiene soldados vivos, no se pueden eliminar mas");
							}
							System.out.println("Desea ver los datos de los soldados del tablero?\n1. SI\n2. NO");
							opcion = sc.nextInt();
							sc.nextLine();
							if(opcion == 1) ImprimirDatos(tablero);
							while(condicion4){
								System.out.println("Ingrese el nombre del soldado a eliminar");
								texto = sc.nextLine();
								condicion4 = EliminarSoldado(texto, tablero, ejercito);
							}
						} else if (opcion == 3) {
							sc.nextLine();
							if(SoldadosVivos(tablero, ejercito) < 1){
								condicion4 = false;
								System.out.println("EL ejercito no tiene soldados vivos, no se puede clonar");
							}
							System.out.println("Desea ver los datos de los soldados del tablero?\n1. SI\n2. NO");
							opcion = sc.nextInt();
							sc.nextLine();
							if(opcion == 1) ImprimirDatos(tablero);
							while(condicion4){	
								System.out.println("Ingrese el nombre del soldado a clonar");
								texto = sc.nextLine();
								condicion4 = ClonarSoldado(texto, tablero, ejercito);
							}
							System.out.println("Desea ver los datos de los soldados del tablero?\n1. SI\n2. NO");
							opcion = sc.nextInt();
							sc.nextLine();
							if(opcion == 1) ImprimirDatos(tablero);
						} else if (opcion == 4) {
							sc.nextLine();
							if(SoldadosVivos(tablero, ejercito) < 1){
								condicion4 = false;
								System.out.println("EL ejercito no tiene soldados vivos, no se pueden modificar");
							}
							System.out.println("Desea ver los datos de los soldados del tablero?\n1. SI\n2. NO");
							opcion = sc.nextInt();
							sc.nextLine();
							if(opcion == 1) ImprimirDatos(tablero);
							while(condicion4){	
								System.out.println("Ingrese el nombre del soldado a modificar");
								texto = sc.nextLine();
								condicion4 = ModificarSoldado(texto, tablero, ejercito);
							}
							System.out.println("Desea ver los datos de los soldados del tablero?\n1. SI\n2. NO");
							opcion = sc.nextInt();
							sc.nextLine();
							if(opcion == 1) ImprimirDatos(tablero);
						} else if (opcion == 5) {
							sc.nextLine();
							if(SoldadosVivos(tablero, ejercito) < 2){
								condicion4 = false;
								System.out.println("EL ejercito no tiene los soldados necesarios para comparar sus datos");
							}
							System.out.println("Desea ver los datos de los soldados del tablero?\n1. SI\n2. NO");
							opcion = sc.nextInt();
							sc.nextLine();
							if(opcion == 1) ImprimirDatos(tablero);
							while(condicion4){	
								System.out.println("Ingrese el nombre del primer soldado");
								nombres[0] = sc.nextLine();
								System.out.println("Ingrese el nombre del segundo soldado");
								nombres[1] = sc.nextLine();
								condicion4 = CompararSoldados(nombres, tablero, ejercito);
							}
							System.out.println("Desea ver los datos de los soldados del tablero?\n1. SI\n2. NO");
							opcion = sc.nextInt();
							sc.nextLine();
							if(opcion == 1) ImprimirDatos(tablero);
						} else if (opcion == 6) {
							sc.nextLine();
							if(SoldadosVivos(tablero, ejercito) < 2){
								condicion4 = false;
								System.out.println("EL ejercito no tiene los soldados necesarios para intercambiar sus posiciones");
							}
							System.out.println("Desea ver los datos de los soldados del tablero?\n1. SI\n2. NO");
							opcion = sc.nextInt();
							sc.nextLine();
							if(opcion == 1) ImprimirDatos(tablero);
							while(condicion4){	
								System.out.println("Ingrese el nombre del primer soldado");
								nombres[0] = sc.nextLine();
								System.out.println("Ingrese el nombre del segundo soldado");
								nombres[1] = sc.nextLine();
								condicion4 = IntercambiarSoldados(nombres, tablero, ejercito);
							}
							System.out.println("Desea ver los datos de los soldados del tablero?\n1. SI\n2. NO");
							opcion = sc.nextInt();
							sc.nextLine();
							if(opcion == 1) ImprimirDatos(tablero);
						} else if (opcion == 7) {
							sc.nextLine();
							if(SoldadosVivos(tablero, ejercito) < 1){
								condicion4 = false;
								System.out.println("EL ejercito no tiene soldados vivos");
							}
							
							while(condicion4){	
								System.out.println("Ingrese el nombre del soldado a imprimir");
								texto = sc.nextLine();
								condicion4 = ImprimirSoldado(texto, tablero, ejercito);
							}
							System.out.println("Desea ver los datos de los soldados del tablero?\n1. SI\n2. NO");
							opcion = sc.nextInt();
							sc.nextLine();
							if(opcion == 1) ImprimirDatos(tablero);
						} else if (opcion == 8) {
							sc.nextLine();
							if(SoldadosVivos(tablero, ejercito) < 1){
								condicion4 = false;
								System.out.println("EL ejercito no tiene soldados vivos");
							}
							ImprimirTablero(tablero, ejercito);
							System.out.println("Desea ver los datos de los soldados del tablero?\n1. SI\n2. NO");
							opcion = sc.nextInt();
							sc.nextLine();
							if(opcion == 1) ImprimirDatos(tablero);
						} else if (opcion == 9) {
							sc.nextLine();
							if(SoldadosVivos(tablero, ejercito) < 1){
								condicion4 = false;
								System.out.println("EL ejercito no tiene soldados vivos, no se puede trabajar");
							}
							System.out.println("Desea ver los datos de los soldados del tablero?\n1. SI\n2. NO");
							opcion = sc.nextInt();
							sc.nextLine();
							if(opcion == 1) ImprimirDatos(tablero);
							while(condicion4){
								condicion4 = SumarNiveles(tablero, ejercito);
							}
						} else if (opcion == 10) {
							sc.nextLine();
							condicion4 = ActualizarCondicion(tablero);
							if(!condicion4){
								System.out.println("EL ejercito no tiene soldados vivos, no se puede jugar");
							}
							while(condicion4){
								ImprimirTablero(tablero);
								ImprimirDatos(tablero);
								Jugada(tablero, 1);
								condicion4 = false;
							}
						} else if (opcion == 11) {
							condicion2 = false;
						} else {
							System.out.println("Opcion NO VALIDA. Ingrese de nuevo");
							condicion2 = true; 
						}
					}
			} else if (opcion == 3){
				System.out.println("\nGRACIAS por jugar\n");
				condicion1 = false;
			} else {
				System.out.println("\nOpcion NO VALIDA\n");
			}
		}
	}
}