import java.util.*;

public class VideoJuego7 {
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		boolean condicion = true;
		
		int tamaño1 = (int)(Math.random()*10+1); //Cantidad de soldados del ejercito 1
		int tamaño2 = (int)(Math.random()*10+1); //Cantidad de soldados del ejercito 2
		Soldado[][] tablero = new Soldado[10][10]; //Creando un arreglo de Soldados bidimensional

		AsignarAtributos(tablero, tamaño1, 1);	//Creando Soldados en el ejercito 0
		AsignarAtributos(tablero, tamaño2, 2);	//Creando Soldados en el ejercito 1
		
		ImprimirDatos(tablero);
		ImprimirTablero(tablero);
		
		System.out.println("El soldado con mas vida del ejercito 1 es:");
		EncontrarMayorVida(tablero, 10, 1);
		System.out.println("El soldado con mas vida del ejercito 2 es:");
		EncontrarMayorVida(tablero, 10, 2);
		
		System.out.println("Promedio de Vida del Ejercito 1: " + PromedioVida(tablero, 10, 1) + "\n");
		System.out.println("Promedio de Vida del Ejercito 2: " + PromedioVida(tablero, 10, 2) + "\n");
		System.out.println("\n---------------------\n");
		
		System.out.println("Vida de todos los soldados:");
		ImprimirVida(tablero);
		
		System.out.println("Ranking de Vida segun Ordenamiento Burbuja");
		OrdenamientoBurbuja(tablero, SoldadosVivos(tablero));
		System.out.println("Ranking de Vida segun Ordenamiento por Insercion");
		OrdenamientoInsercion(tablero, SoldadosVivos(tablero));
		
		int turno = 1;
		
		while(condicion){
			Jugada(tablero, turno);		//Este metodo ejecutara toda la jugada de cada turno
			ImprimirTablero(tablero);	//Imprimir el tablero y datos despues de cada turno
			ImprimirDatos(tablero);
			
			condicion = ActualizarCondicion(tablero); //Si hay un ejercito con 0 soldados, sera false
			turno++;
		}
		Ganador(tablero);
	}

	public static void AsignarAtributos(Soldado[][] tablero, int n, int ejercito){
		for(int i = 0; i < n; i++){
			int x = (int)(Math.random()*10);	//x representa a las filas, siendo del 1 al 9
			int y = (int)(Math.random()*10);	//y representa a las columnas, siendo del 1 al 9
			if(tablero[x][y] == null){	//Si la posicion [y][x] esta vacia, crea un Soldado en esa posicion
				String nombre = "Soldado" + (ejercito) + "x" + (i + 1);
				int vida = (int)(Math.random()*5+1);
				int ataque = (int)(Math.random()*5+1);
				int defensa = (int)(Math.random()*5+1);
				tablero[x][y] = new Soldado(nombre, vida, ejercito, x + 1, y + 1, ataque, defensa);
			} else {
				i--; //Si no hay un Soldado, se vuelve al ciclo
			}
		}
	}

	public static void ImprimirDatos(Soldado[][] tablero){
		for(int i = 0; i < 10; i++)		//Controla las filas
			for(int j = 0; j < 10; j++)		//Controla las columnas
				if(tablero[i][j] != null)
					if(tablero[i][j].getVive()){
						System.out.println("Nombre: " + tablero[i][j].getNombre());
						System.out.println("Salud: " + tablero[i][j].getVidaActual());
						System.out.println("Ejercito: " + tablero[i][j].getEjercito());
						System.out.println("Fila:" + tablero[i][j].getFila());
						System.out.println("Columna: " + tablero[i][j].getColumna());
					}	
		System.out.println("\n---------------------\n");
	}
	
	public static void ImprimirTablero(Soldado[][] tablero){
		for(int i = 0; i < 10; i++){	//filas
				for(int j = 0; j < 10; j++){	//columnas
					if(tablero[i][j] != null)	//Si el indice contiene un Soldado continua
						if(tablero[i][j].getVive()){		//Si el soldado esta vivo continua
							if(tablero[i][j].getEjercito() == 1) System.out.print("1 ");
							if(tablero[i][j].getEjercito() == 2) System.out.print("2 ");
						} else System.out.print("= ");
					else System.out.print("= ");
	
				}
				System.out.print("\n");		//Una vez acabada una fila, se pasa a la siguiente linea
		}
		System.out.println("\n---------------------\n");
	}
	
	public static void EncontrarMayorVida(Soldado[][] tablero, int n, int ejercito){
		int mayor = Integer.MIN_VALUE;

		Soldado masVida = new Soldado();
		for(int i = 0; i < n; i++)	//Filas
			for(int j = 0; j < n; j++)	//Columnas
				if(tablero[j][i] != null)
					if(tablero[j][i].getVive()){
						if(tablero[j][i].getEjercito() == ejercito)	//Para solamente comparar soldados del mismo ejercito
							if(tablero[j][i].getVidaActual() > mayor){
								mayor = tablero[j][i].getVidaActual();	//Si es mayor, actualiza la variable
								masVida = tablero[j][i];
							}
					}
		System.out.println("Nombre: " + masVida.getNombre());
		System.out.println("Vida: " + masVida.getVidaActual());
		System.out.println("Fila: " + masVida.getFila());
		System.out.println("Fila: " + masVida.getFila());
		System.out.println("Columna: " + masVida.getColumna());
		System.out.println("Ejercito" + masVida.getEjercito());
		System.out.println("\n---------------------\n");
	}
	
	public static int PromedioVida(Soldado[][] tablero, int n, int ejercito){
		int promedio = 0;
		int size = 0;
		for(int i = 0; i < n; i++)	//Filas
			for(int j = 0; j < n; j++)	//Columnas
				if(tablero[j][i] != null)		
					if(tablero[j][i].getVive() && tablero[j][i].getEjercito() == ejercito){
						promedio += tablero[j][i].getVidaActual();
						size++;
					}
		return promedio/size;
	}
	
	public static void ImprimirVida(Soldado[][] tablero){	
		for(int i = 0; i < 10; i++)	//Controla las filas
			for(int j = 0; j < 10; j++)	//Controla las columnas
				if(tablero[j][i] != null)
					if(tablero[j][i].getVive())
						System.out.println("Vida de " + tablero[j][i].getNombre() + ": " + tablero[j][i].getVidaActual());
		System.out.println("\n---------------------\n");		
	}
	
	public static int SoldadosVivos(Soldado[][] tablero){
		int contador = 0;
		for(int i = 0; i < 10; i++)	//Controla las filas
			for(int j = 0; j < 10; j++)	//Controla las columnas
				if(tablero[i][j] != null)
					if(tablero[i][j].getVive())
						contador++;
		return contador;
	}
	
	public static int SoldadosVivos(Soldado[][] tablero, int ejercito){
		int contador = 0;
		for(int i = 0; i < 10; i++)	//Controla las filas
			for(int j = 0; j < 10; j++)	//Controla las columnas
				if(tablero[i][j] != null)
					if(tablero[i][j].getVive() && tablero[i][j].getEjercito() == ejercito)
						contador++;
		return contador;
	}
	
	public static void OrdenamientoBurbuja(Soldado[][] tablero, int n){
		Soldado temp = new Soldado();
		int contador = 0;
		Soldado[] lista = new Soldado[n];
		for(int i = 0; i < 10; i++)	//Controla las filas
			for(int j = 0; j < 10; j++)	//Controla las columnas
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
			System.out.println("Nombre: " + lista[i].getNombre());
			System.out.println("Vida: " + lista[i].getVidaActual());
			System.out.println("Fila: " + lista[i].getFila());
			System.out.println("Columna" + lista[i].getColumna());
			System.out.println("Ejercito" + lista[i].getEjercito());
		}
		System.out.println("\n---------------------\n");
	}

	public static void OrdenamientoInsercion(Soldado[][] tablero, int n){
		Soldado temp = new Soldado();
		int contador = 0;
		Soldado[] lista = new Soldado[n];
		for(int i = 0; i < 10; i++)	//Controla las filas
			for(int j = 0; j < 10; j++)	//Controla las columnas
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
			System.out.println("Nombre: " + lista[i].getNombre());
			System.out.println("Vida: " + lista[i].getVidaActual());
			System.out.println("Fila: " + lista[i].getFila());
			System.out.println("Columna" + lista[i].getColumna());
			System.out.println("Ejercito" + lista[i].getEjercito());
		}
		System.out.println("\n---------------------\n");
	}
	
	public static void Jugada(Soldado[][] tablero, int turno){
		Scanner sc = new Scanner(System.in);
		turno %= 2;	//Solo habra 2 turnos, turnos pares e impares
		if(turno == 0) turno = 2;
		//Pidiendo al usuario valores y verificando que sean correctos
		System.out.println("Jugador " + turno + ". Ingrese el soldado a mover(Soldado1x1) del Ejercito " + turno);
		String soldado = sc.nextLine();
		boolean condicion1 = VerificarSoldado(soldado, turno, tablero);
		while(!condicion1){
			System.out.println("Soldado no encontrado, por favor ingrese otro");
			soldado = sc.nextLine();
			condicion1 = VerificarSoldado(soldado, turno, tablero);
		}
		
		System.out.println("Ahora ingrese la direccion (w:up, s:down, d:rigth, a:left)");
		String direccion = sc.nextLine();
		boolean condicion2 = VerificarDireccion(direccion, turno, tablero, soldado);
		while(!condicion2){
			System.out.println("Direccion no admitida, por favor ingrese otro");
			direccion = sc.nextLine();
			condicion2 = VerificarDireccion(direccion, turno, tablero, soldado);
		}
		//Llevando a cabo la batalla en caso hubiese
		SoldadoMoviendose(tablero, turno, soldado, direccion);
	}
	
	public static boolean VerificarSoldado(String soldado, int turno, Soldado[][] tablero){
		for(int i = 0; i < 10; i++)	//Controla las filas
			for(int j = 0; j < 10; j++)	//Controla las columnas
				if(tablero[i][j] != null)
					if(tablero[i][j].getVive() && tablero[i][j].getEjercito() == turno)
						if(tablero[i][j].getNombre().equals(soldado))
							return true;
		return false;
	}
	
	public static boolean VerificarDireccion(String direccion, int turno, Soldado[][] tablero, String soldado){
		if(!(direccion.equals("w") || direccion.equals("s") || direccion.equals("a") || direccion.equals("d")))
			return false;

		for(int i = 0; i < 10; i++) {    //Controla las filas
			for(int j = 0; j < 10; j++) {    //Controla las columnas
				if(tablero[i][j] != null && tablero[i][j].getVive() && tablero[i][j].getEjercito() == turno && tablero[i][j].getNombre().equals(soldado)){
					if(direccion.equals("w") && i - 1 >= 0)    // Verifica movimiento hacia arriba
						return true;
					else if(direccion.equals("s") && i + 1 < 10)    // Verifica movimiento hacia abajo
						return true;
					else if(direccion.equals("a") && j - 1 >= 0)    // Verifica movimiento hacia la izquierda
						return true;
					else if(direccion.equals("d") && j + 1 < 10)    // Verifica movimiento hacia la derecha
						return true;
				}
			}
		}
		return false;
	}
	
	public static void SoldadoMoviendose(Soldado[][] tablero, int turno, String soldado, String direccion){
		int fila, columna;
		int contador = 0;	//Este contador es MUY IMPORTANTE, evitara que se llame el metodo Combate() mas de 1 vez, evitando errores con s y d
		for(int i = 0; i < 10; i++)	//Controla las filas
			for(int j = 0; j < 10; j++)	//Controla las columnas
				if(tablero[i][j] != null && tablero[i][j].getVive() && tablero[i][j].getEjercito() == turno && tablero[i][j].getNombre().equals(soldado) && contador == 0){
					if(direccion.equals("w") && i - 1 >= 0){    // Verifica movimiento hacia arriba
						Combate(tablero, i, j, direccion);
						contador++;
					}else if(direccion.equals("s") && i + 1 < 10){    // Verifica movimiento hacia abajo
						Combate(tablero, i, j, direccion);
						contador++;
					}else if(direccion.equals("a") && j - 1 >= 0){    // Verifica movimiento hacia la izquierda
						Combate(tablero, i, j, direccion);
						contador++;
					}else if(direccion.equals("d") && j + 1 < 10){   // Verifica movimiento hacia la derecha
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
			} else if(tablero[x][y].getVidaActual() < tablero[x - 1][y].getVidaActual()){		//Verifica que el soldado actual tenga menos vida que el soldado enemigo
				tablero[x - 1][y].setVidaActual(tablero[x - 1][y].getVidaActual() - tablero[x][y].getVidaActual());
				tablero[x][y].morir();
			} else if(tablero[x][y].getVidaActual() == tablero[x - 1][y].getVidaActual()){		//Verifica que el soldado actual tenga la misma vida que el soldado enemigo
				tablero[x][y].morir();
				tablero[x - 1][y].morir();
			} else if(tablero[x][y].getVidaActual() > tablero[x - 1][y].getVidaActual()){		//Verifica que el soldado actual tenga mas vida que el soldado enemigo
				tablero[x - 1][y] = new Soldado(tablero[x][y].getNombre(), tablero[x][y].getVidaActual() - tablero[x - 1][y].getVidaActual(), tablero[x][y].getEjercito(), x + 1 - 1, y + 1);
				tablero[x][y].morir();
			}
		} else if(direccion.equals("s")){
			if(tablero[x + 1][y] == null || !tablero[x + 1][y].getVive()){
				tablero[x + 1][y] = new Soldado(tablero[x][y].getNombre(), tablero[x][y].getVidaActual(), tablero[x][y].getEjercito(), x + 1 + 1, y + 1);
				tablero[x][y].morir();
			} else if(tablero[x][y].getVidaActual() < tablero[x + 1][y].getVidaActual()){
				tablero[x + 1][y].setVidaActual(tablero[x + 1][y].getVidaActual() - tablero[x][y].getVidaActual());
				tablero[x][y].morir();
			} else if(tablero[x][y].getVidaActual() == tablero[x + 1][y].getVidaActual()){
				tablero[x][y].morir();
				tablero[x + 1][y].morir();
			} else if(tablero[x][y].getVidaActual() > tablero[x + 1][y].getVidaActual()){
				tablero[x + 1][y] = new Soldado(tablero[x][y].getNombre(), tablero[x][y].getVidaActual() - tablero[x + 1][y].getVidaActual(), tablero[x][y].getEjercito(), x + 1 + 1, y + 1);
				tablero[x][y].morir();
			}
		} else if(direccion.equals("a")){
			if(tablero[x][y - 1] == null || !tablero[x][y - 1].getVive()){
				tablero[x][y - 1] = new Soldado(tablero[x][y].getNombre(), tablero[x][y].getVidaActual(), tablero[x][y].getEjercito(), x + 1, y + 1 - 1 );
				tablero[x][y].morir();
			} else if(tablero[x][y].getVidaActual() < tablero[x][y - 1].getVidaActual()){
				tablero[x][y - 1].setVidaActual(tablero[x][y - 1].getVidaActual() - tablero[x][y].getVidaActual());
				tablero[x][y].morir();
			} else if(tablero[x][y].getVidaActual() == tablero[x][y - 1].getVidaActual()){
				tablero[x][y].morir();
				tablero[x][y - 1].morir();
			} else if(tablero[x][y].getVidaActual() > tablero[x][y - 1].getVidaActual()){
				tablero[x][y - 1] = new Soldado(tablero[x][y].getNombre(), tablero[x][y].getVidaActual() - tablero[x][y - 1].getVidaActual(), tablero[x][y].getEjercito(), x + 1, y + 1 - 1 );
				tablero[x][y].morir();
			}
		} else if(direccion.equals("d")){
			if(tablero[x][y + 1] == null || !tablero[x][y + 1].getVive()){
				tablero[x][y + 1] = new Soldado(tablero[x][y].getNombre(), tablero[x][y].getVidaActual(), tablero[x][y].getEjercito(), x + 1, y + 1 + 1);
				tablero[x][y].morir();
			} else if(tablero[x][y].getVidaActual() < tablero[x][y + 1].getVidaActual()){
				tablero[x][y + 1].setVidaActual(tablero[x][y + 1].getVidaActual() - tablero[x][y].getVidaActual());
				tablero[x][y].morir();
			} else if(tablero[x][y].getVidaActual() == tablero[x][y + 1].getVidaActual()){
				tablero[x][y].morir();
				tablero[x][y + 1].morir();
			} else if(tablero[x][y].getVidaActual() > tablero[x][y + 1].getVidaActual()){
				tablero[x][y + 1] = new Soldado(tablero[x][y].getNombre(), tablero[x][y].getVidaActual() - tablero[x][y + 1].getVidaActual(), tablero[x][y].getEjercito(), x + 1, y + 1 + 1 );
				tablero[x][y].morir();
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
}