import java.util.*;

public class VideoJuego6 {
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		int tamaño1 = (int)(Math.random()*10+1); //Cantidad de soldados del ejercito 1
		int tamaño2 = (int)(Math.random()*10+1); //Cantidad de soldados del ejercito 2
		Soldado[][] tablero = new Soldado[10][10]; //Creando un arreglo de Soldados bidimensional
		
		AsignarAtributos(tablero, tamaño1, 1);	//Creando Soldados en el ejercito 0
		AsignarAtributos(tablero, tamaño2, 2);	//Creando Soldados en el ejercito 1
		
		//Metodos pedidos en la practica
		ImprimirTablero(tablero);
		ImprimirDatos(tablero);
		System.out.println("Soldado con Mas Vida del Ejercito 1: ");
		EncontrarMayorVida(tablero, 10, 1);
		System.out.println("Soldado con Mas Vida del Ejercito 2: ");
		EncontrarMayorVida(tablero, 10, 2);
		System.out.println("Promedio de Vida: " + PromedioVida(tablero, tamaño1 + tamaño2));
		System.out.println("\n---------------------\n");
		ImprimirVida(tablero);
		System.out.println("Ranking de Vida por Ordenamiento Burbuja");
		OrdenamientoBurbuja(tablero, tamaño1 + tamaño2);
		System.out.println("Ranking de Vida por Ordenamiento por Insercion");
		OrdenamientoInsercion(tablero, tamaño1 + tamaño2);
		
		EjercitoGanador(tablero);
	}
	
	public static void AsignarAtributos(Soldado[][] tablero, int n, int ejercito){
		for(int i = 0; i < n; i++){
			int x = (int)(Math.random()*10);	//x representa a las columnas, siendo del 1 al 9
			int y = (int)(Math.random()*10);	//y representa a las filas, siendo del 1 al 9
			if(tablero[y][x] == null){	//Si la posicion y x esta vacia, crea un Soldado en esa posicion
				String nombre = "Soldado" + (ejercito) + "x" + (i + 1);
				int vida = (int)(Math.random()*5+1);
				int ataque = (int)(Math.random()*5+1);
				int defensa = (int)(Math.random()*5+1);
				tablero[y][x] = new Soldado(nombre, vida, ataque, defensa, x + 1, y + 1, ejercito);
			} else {	//Si la posicion y x no esta vacia, el ciclo continua por el i--
				i--;
			}
		}
	}

	public static void ImprimirTablero(Soldado[][] tablero){
		for(int i = 0; i < 10; i++){	//filas
				for(int j = 0; j < 10; j++){	//columnas
					if(tablero[j][i] != null)	//Si el indice contiene un Soldado continua
						if(tablero[j][i].getVive()){		//Si el soldado esta vivo continua
							if(tablero[j][i].getEjercito() == 1) System.out.print("1 ");
							if(tablero[j][i].getEjercito() == 2) System.out.print("2 ");
						} else System.out.print("= ");
					else System.out.print("= ");
	
				}
				System.out.print("\n");		//Una vez acabada una fila, se pasa a la siguiente linea
		}
		System.out.println("\n---------------------\n");
	}
	
	public static void ImprimirDatos(Soldado[][] tablero){
		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 10; j++)
				if(tablero[j][i] != null)
					if(tablero[j][i].getVive()){
						System.out.println("Nombre: " + tablero[j][i].getNombre());
						System.out.println("Salud: " + tablero[j][i].getVidaActual());
						System.out.println("Fila:" + tablero[j][i].getFila());
						System.out.println("Columna: " + tablero[j][i].getColumna());
						System.out.println("Ejercito: " + tablero[j][i].getEjercito());
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
	
	public static int PromedioVida(Soldado[][] tablero, int n){
		int promedio = 0;
		for(int i = 0; i < 10; i++)	//Filas
			for(int j = 0; j < 10; j++)	//Columnas
				if(tablero[j][i] != null)		
					if(tablero[j][i].getVive())
						promedio += tablero[j][i].getVidaActual();
		return promedio/n;
	}
	
	public static void ImprimirVida(Soldado[][] tablero){	
		for(int i = 0; i < 10; i++)	//Filas
			for(int j = 0; j < 10; j++)	//Columnas
				if(tablero[j][i] != null)
					if(tablero[j][i].getVive())
						System.out.println("Vida de " + tablero[j][i].getNombre() + ": " + tablero[j][i].getVidaActual());
		System.out.println("\n---------------------\n");		
	}
	
	public static void OrdenamientoBurbuja(Soldado[][] tablero, int n){
		Soldado temp = new Soldado();
		int contador = 0;
		Soldado[] lista = new Soldado[n];
		for(int i = 0; i < 10; i++)	//Filas
			for(int j = 0; j < 10; j++)	//Columnas
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
		for(int i = 0; i < 10; i++)	//Filas
			for(int j = 0; j < 10; j++)	//Columnas
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
	
	public static void EjercitoGanador(Soldado[][] tablero){
		int ejercito1 = 0;
		int ejercito2 = 0;
		for(int i = 0; i < 10; i++)	//Filas
				for(int j = 0; j < 10; j++)	//Columnas
					if(tablero[j][i] != null)
						if(tablero[j][i].getVive()){
							if(tablero[j][i].getEjercito() == 1) ejercito1 += tablero[j][i].getVidaActual();
							//Si es del ejercito 1, la salud se le suma al total de ese ejercito
							if(tablero[j][i].getEjercito() == 2) ejercito2 += tablero[j][i].getVidaActual();
							//Si es del ejercito 2, la salud se le suma al total de ese ejercito
						}
		//Imprimir quien es el ganador, teniendo en cuenta la vida total de cada uno de los ejercitos
		if(ejercito1>ejercito2) System.out.println("Ganador: Ejercito 1");
		else if(ejercito1 < ejercito2) System.out.println("Ganador: Ejercito 2");
		else System.out.println("Empate, no hay ganador");
		System.out.println("\n---------------------\n");
	}
}