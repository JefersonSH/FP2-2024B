import java.util.*;
public class VideoJuego5 {
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int tamaño1 = (int)(Math.random()*10+1); //Creando el numero aleatorio del 1 al 10 para ejercito 0
		int tamaño2 = (int)(Math.random()*10+1); //Creando el numero aleatorio del 1 al 10 para ejercito 1
		HashMap<String, Soldado> tablero = new HashMap<>(); //Creando un arreglo de Soldados bidimensional
		
		AsignarAtributos(tablero, tamaño1, 1);	//Creando Soldados en el ejercito 0
		AsignarAtributos(tablero, tamaño2, 2);	//Creando Soldados en el ejercito 1

		//Llamando a los metodos para mostrar resultados
		Imprimir(tablero);
		System.out.println("Soldado con Mayor Vida del Ejercito 1: ");
		EncontrarMayorVida(tablero, 10, 1);
		System.out.println("Soldado con Mayor Vida del Ejercito 2: ");
		EncontrarMayorVida(tablero, 10, 2);
		System.out.println("Promedio de Vida: " + PromedioVida(tablero, tamaño1 + tamaño2) + "\n");
		ImprimirVida(tablero);
		ImprimirDatos(tablero);
		System.out.println("Ranking de Vida por Ordenamiento Burbuja");
		OrdenamientoBurbuja(tablero, tamaño1 + tamaño2);
		System.out.println("Ranking de Vida por Ordenamiento por Insercion");
		OrdenamientoInsercion(tablero, tamaño1 + tamaño2);
		
		EjercitoGanador(tablero);
	}
	 
	public static void AsignarAtributos(HashMap<String, Soldado> tablero, int n, int ejercito){
		for(int i = 0; i < n; i++){
			int x = (int)(Math.random()*10);	//Las filas estan enumeradas del 0 al 9
			int y = (int)(Math.random()*10);	//Las columnas estan enumeradas del 0 al 9
			String t = y + "x" + x;
			if(!tablero.containsKey(t)){	//Si la posicion x y esta vacia, asigna los valores aleatorios
				//Soldado soldado = new Soldado();
				Soldado soldado = new Soldado();
				soldado.setNombre("Soldado" + (ejercito) + "x" + (i + 1));
				soldado.setHP((int)(Math.random()*5+1));
				soldado.setFila(x + 1);
				soldado.setColumna(y + 1);
				soldado.setEjercito(ejercito);
				tablero.put(t, soldado);
			} else {	//Si la posicion x no esta vacia, el contador se resta 1, para no desordenar el ciclo for
				i--;
			}
		}
	}

	public static void Imprimir(HashMap<String, Soldado> tablero){
		for(int i = 0; i < 10; i++){	//Este ciclo controla las filas 
				for(int j = 0; j < 10; j++){  //Este ciclo controla las columnas
					String t = j + "x" + i;		//Esta variable contiene el identificar del Soldado
					if(tablero.containsKey(t)){	//Si el indice contiene un Soldado, coloca "O"
						if(tablero.get(t).getEjercito() == 1) System.out.print("1 ");
						if(tablero.get(t).getEjercito() == 2) System.out.print("2 ");
					}
					else System.out.print("= ");
	
				}
				System.out.print("\n");		//Una vez acabada una fila, se pasa a la siguiente linea
		}
	}
	
	public static void ImprimirDatos(HashMap<String, Soldado> tablero){
		for(int i = 0; i < 10; i++){	//Este ciclo controla las filas 
			for(int j = 0; j < 10; j++){	 //Este ciclo controla las columnas
				String t = j + "x" + i;		//Esta variable contiene el identificar del Soldado
				if(tablero.containsKey(t)){
					System.out.println("Nombre: " + tablero.get(t).getNombre());
					System.out.println("Salud: " + tablero.get(t).getHP());
					System.out.println("Fila: " + tablero.get(t).getFila());
					System.out.println("Columna: " + tablero.get(t).getColumna());
					System.out.println("Ejercito: " + tablero.get(t).getEjercito());
				}
			}	
		}		
	}
	
	public static void EncontrarMayorVida(HashMap<String, Soldado> tablero, int n, int ejercito){
		int mayor = Integer.MIN_VALUE;

		Soldado masVida = new Soldado();
		for(int i = 0; i < n; i++)	//Filas
			for(int j = 0; j < n; j++){	//Columnas
				String t = j + "x" + i;		//Esta variable contiene el identificar del Soldado
				if(tablero.containsKey(t))
					if(tablero.get(t).getEjercito() == ejercito)	//Para solamente comparar soldados del mismo ejercito
						if(tablero.get(t).getHP() > mayor){
							mayor = tablero.get(t).getHP();	//Si es mayor, actualiza la variable
							masVida = tablero.get(t);
						}
			}
		System.out.println("Nombre: " + masVida.getNombre());
		System.out.println("Vida: " + masVida.getHP());
		System.out.println("Fila: " + masVida.getFila());
		System.out.println("Columna" + masVida.getColumna());
		System.out.println("Ejercito" + masVida.getEjercito() + "\n");
	}
	
	public static int PromedioVida(HashMap<String, Soldado> tablero, int n){
		int promedio = 0;	//contador
		for(int i = 0; i < 10; i++)		//Filas
			for(int j = 0; j < 10; j++){		//Columnas
				String t = j + "x" + i;		//Esta variable contiene el identificar del Soldado
				if(tablero.containsKey(t))		
					promedio += tablero.get(t).getHP();
			}
		return promedio/n;
	}
	
	public static void ImprimirVida(HashMap<String, Soldado> tablero){	
		for(int i = 0; i < 10; i++)	//Filas
			for(int j = 0; j < 10; j++){	//Columnas
				String t = j + "x" + i;		//Esta variable contiene el identificar del Soldado
				if(tablero.containsKey(t))		
					System.out.println("Vida de " + tablero.get(t).getNombre() + ": " + tablero.get(t).getHP());
			}
		System.out.println("\n");
	}

	public static void OrdenamientoBurbuja(HashMap<String, Soldado> tablero, int n){
		Soldado temp = new Soldado();
		int contador = 0;
		Soldado[] lista = new Soldado[n];
		for(int i = 0; i < 10; i++)	//Filas
			for(int j = 0; j < 10; j++){	//Columnas
				String t = j + "x" + i;		//Esta variable contiene el identificar del Soldado
				if(tablero.containsKey(t)){		
					lista[contador] = tablero.get(t);
					contador++;
				}
			}
		for(int i = 0; i < n - 1; i++)
			for(int j = 0; j < n - 1; j++)
				if(lista[j].getHP() > lista[j+1].getHP()){
					temp = lista[j+1];
					lista[j+1] = lista[j];
					lista[j] = temp;
				}
		for(int i = n - 1; i >= 0; i--){
			System.out.println("Nombre: " + lista[i].getNombre());
			System.out.println("Vida: " + lista[i].getHP());
			System.out.println("Fila: " + lista[i].getFila());
			System.out.println("Columna" + lista[i].getColumna());
			System.out.println("Ejercito" + lista[i].getEjercito());
		}
	}
	
	public static void OrdenamientoInsercion(HashMap<String, Soldado> tablero, int n){
		Soldado temp = new Soldado();
		int contador = 0;
		Soldado[] lista = new Soldado[n];
		for(int i = 0; i < 10; i++)	//Filas
			for(int j = 0; j < 10; j++){	//Columnas
				String t = j + "x" + i;		//Esta variable contiene el identificar del Soldado
				if(tablero.containsKey(t)){		
					lista[contador] = tablero.get(t);
					contador++;
				}
			}
		for(int i = 1; i < n; i++){
			Soldado key = lista[i];
			int j = i - 1;
			while(j >= 0 && lista[j].getHP() > key.getHP()){
				lista[j + 1] = lista[j];
				j = j - 1;
			}
			lista[j + 1] = key;
		}
		for(int i = n - 1; i >= 0; i--){
			System.out.println("Nombre: " + lista[i].getNombre());
			System.out.println("Vida: " + lista[i].getHP());
			System.out.println("Fila: " + lista[i].getFila());
			System.out.println("Columna" + lista[i].getColumna());
			System.out.println("Ejercito" + lista[i].getEjercito());
		}
	}
	
	public static void EjercitoGanador(HashMap<String, Soldado>	tablero){
		int ejercito1 = 0;
		int ejercito2 = 0;
		for(int i = 0; i < 10; i++)	//Filas
				for(int j = 0; j < 10; j++){	//Columnas
					String t = j + "x" + i;		//Esta variable contiene el identificar del Soldado
					if(tablero.containsKey(t)){	//Recorre todo el arreglo bidimensional
						if(tablero.get(t).getEjercito() == 1) ejercito1 += tablero.get(t).getHP();
						//Si es del ejercito 1, la salud se le suma al total de ese ejercito
						if(tablero.get(t).getEjercito() == 2) ejercito2 += tablero.get(t).getHP();
						//Si es del ejercito 2, la salud se le suma al total de ese ejercito
					}
				}
		//Imprimir quien es el ganador, teniendo en cuenta la vida total de cada uno de los ejercitos
		if(ejercito1>ejercito2) System.out.println("Ganador: Ejercito 1");
		else if(ejercito1 < ejercito2) System.out.println("Ganador: Ejercito 2");
		else System.out.println("Empate, no hay ganador");
	}
}
	