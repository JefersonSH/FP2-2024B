import java.util.*;

public class VideoJuego11 {
    static Scanner sc = new Scanner(System.in);
    private static int maxSoldados = 10;
    private static int maxEjercitos = 10;
    private static int maxReinos = 2;
    private static int ladoTablero = 10;
    private static ArrayList<Integer> filas = new ArrayList<>();
    private static ArrayList<Integer> columnas = new ArrayList<>();

    public static void main(String[] args){
        Menu();
    }

    public static void Menu(){
        ArrayList<Reino> reinos = new ArrayList<>();
        CrearEjercitos(reinos);
        Jugar(reinos);
    }

    public static void CrearEjercitos(ArrayList<Reino> reinos){
        String[] nombreReinos = {"Inglaterra","Francia","Sacro Imperio","Castilla - Aragon","Moros"};
        int random1, random2;
        // Seleccionando el nombre de 2 Reinos diferentes aleatorios
        while(true){
            random1 = (int)(Math.random() * nombreReinos.length);
            random2 = (int)(Math.random() * nombreReinos.length);
            if(random1 != random2) break;
        }
        // Creando los 2 reinos
        reinos.add(new Reino(nombreReinos[random1]));
        reinos.add(new Reino(nombreReinos[random2]));
        // Creando de 1 a 10 ejércitos para cada reino
        for(Reino reino: reinos){
            for(int i = 0; i < (int)(Math.random() * maxEjercitos + 1); i++){
                int fila = 0, columna = 0, contador = 0;
                Ejercito ejercito = new Ejercito(i + 1, reino.getLetra() + (i + 1), reino.getNombre());
                while(true){
                    contador = 0;
                    ejercito.setFila((int)(Math.random() * ladoTablero + 1));
                    ejercito.setColumna((int)(Math.random() * ladoTablero + 1));
                    if(Verificar(ejercito.getFila(), ejercito.getColumna())) contador++;
                    // Si el contador es más de 0, significa que hay al menos 1 coincidencia de ejércitos con la misma fila y columna, por ende, se generan otros valores para fila y columna
                    if(contador == 0) break;    
                }
                reino.getEjercitos().add(ejercito);        // Agregando los datos del ejército al reino
                Actualizar(reinos);
            }
        }
    }

    public static void Actualizar(ArrayList<Reino> reinos){
        filas.clear();
        columnas.clear();
        for(Reino reino : reinos)
            for(Ejercito ejercito : reino.getEjercitos()){
                filas.add(ejercito.getFila());
                columnas.add(ejercito.getColumna());
            }
    }
        
    public static boolean Verificar(int fila, int columna){
        for(int i = 0; i < filas.size(); i++)
            if(filas.get(i) == fila && columnas.get(i) == columna) return true;
        return false;
    }   
    
    public static void ImprimirReinos(ArrayList<Reino> reinos){
        System.out.println("--------- SOLDADOS EN TODOS LOS REINOS ---------");
        // Recorriendo todos los reinos, ejércitos y soldados
        for (Reino reino : reinos){
            System.out.println("Reino: " + reino.getNombre() + "(" + reino.getLetra() + ")");
            for (Ejercito ejercito : reino.getEjercitos()){
                System.out.println("- Ejército: " + ejercito.getNombre() + "\tPosicion: " + ejercito.getFila() + "x" + ejercito.getColumna());
            }
        }
    }

    public static void ImprimirTableroEjercitos(ArrayList<Reino> reinos) {
        for (int i = 0; i < ladoTablero; i++) {
            System.out.println("\n+---+---+---+---+---+---+---+---+---+---+");
            System.out.print("|");
            for (int j = 0; j < ladoTablero; j++) {
                boolean encontrado = false;
                for (Reino reino : reinos) {
                    for (Ejercito ejercito : reino.getEjercitos()) {
                        if (ejercito.getFila() == (i + 1) && ejercito.getColumna() == (j + 1)) {
                            System.out.print(" " + reino.getLetra() + " ");
                            encontrado = true;
                            break; // Salir del bucle de ejércitos si se ha encontrado y mostrado el ejército
                        }
                    }
                    if (encontrado) {
                        break; // Salir del bucle de reinos si se encontró el ejército
                    }
                }
                if (!encontrado) {
                    System.out.print("   "); // Espacios si no se encontró ningún ejército en esa posición
                }
                System.out.print("|");
            }
        }
        System.out.println("\n+---+---+---+---+---+---+---+---+---+---+\n");
    }
	
	public static void JugarAutomaticamente(ArrayList<Ejercito> ejercitos){
		int[] vidaTotal = new int[2];
		int contador = 0;
		CrearSoldados(ejercitos);
		for(Ejercito ejercito: ejercitos){
			for(Soldado soldado: ejercito.getSoldados()){
				vidaTotal[contador] += soldado.getVidaActual();
			}
			contador++;
		}
		System.out.println("Vida Total del Ejercito " + ejercitos.get(0).getNombre() + ": " + vidaTotal[0] + "\nVida Total del Ejercito " + ejercitos.get(1).getNombre() + ": " + vidaTotal[1]);
		if(vidaTotal[0] > vidaTotal[1]){
			ejercitos.get(1).getSoldados().clear();
			System.out.println("Se termino el enfrentamiento. Ganador: " + ejercitos.get(0).getNombre());
		} else {
			ejercitos.get(0).getSoldados().clear();
			System.out.println("Se termino el enfrentamiento. Ganador: " + ejercitos.get(1).getNombre());
		}
	}
	
	public static void Combate(int fila, int columna, Ejercito ejercitoActual, String direccion, ArrayList<Reino> reinos) {
		boolean ejercitoEncontrado = false; // Bandera para verificar si se encontró un ejército en la posición deseada
		ArrayList<Ejercito> ejercitos = new ArrayList<>();
		int contador = reinos.size() - 1;
		for (Reino reino : reinos) {
			for (Ejercito ejercito : reino.getEjercitos()) {
				if (ejercito.getFila() == fila && ejercito.getColumna() == columna && !ejercito.getNombre().equals(ejercitoActual.getNombre()) && !ejercito.getReino().equals(ejercitoActual.getReino())) {
					//Se cruzan 2 ejercitos de diferentes reinos
					ejercitos.add(ejercito);
					ejercitos.add(ejercitoActual);
					System.out.println("\n********** QUE DESEA REALIZAR **********\n1.Jugar Manualmente\t2.Jugar Automaticamente");
					String opcion = sc.nextLine();
					if(opcion.equals("1")) Jugar2(ejercitos, reino);
					else JugarAutomaticamente(ejercitos);
					ejercitoEncontrado = true;
					if(ejercitos.get(0).getSoldados().size() == 0){
						System.out.println("El ejercito rival esta vacio");
						ejercitos.get(1).setFila(fila);
						ejercitos.get(1).setColumna(columna);
						reino.getEjercitos().remove(ejercitos.get(0));
						System.out.println("El ejercito rival esta vacio2");
					}
					if(ejercitos.get(1).getSoldados().size() == 0){
						System.out.println("El ejercito actual esta vacio");
						reinos.get(contador).getEjercitos().remove(ejercitos.get(1));
						System.out.println("El ejercito actual esta vacio2");
					}
					break;
				} else if (ejercito.getFila() == fila && ejercito.getColumna() == columna && !ejercito.getNombre().equals(ejercitoActual.getNombre()) && ejercito.getReino().equals(ejercitoActual.getReino())) {
					System.out.println("#####\nAccion no valida, no se permiten enfrentamientos locales\n#####");
					ejercitoEncontrado = true;
					break;
				}
			}
			if (ejercitoEncontrado) {
				break;
			}
			contador--;
		}
		if (!ejercitoEncontrado) {
			ejercitoActual.setFila(fila);
			ejercitoActual.setColumna(columna);
		}
	}
	
	public static boolean CumpleCondicion(String t, String direccion, ArrayList<Reino> reinos, String reinoActual){
		int fila = Integer.parseInt(t.substring(0,t.indexOf("x")));
		int columna = Integer.parseInt(t.substring(t.indexOf("x") + 1, t.length()));
		for(Reino reino : reinos)
            for(Ejercito ejercito : reino.getEjercitos())
				if(ejercito.getFila() == fila && ejercito.getColumna() == columna && reino.getNombre().equals(reinoActual)){
					if(direccion.equals("w") && ejercito.getFila() - 2 >= 0){
						Combate(fila - 1, columna, ejercito, direccion, reinos);
						return true;
					} else if(direccion.equals("s") && ejercito.getFila() < ladoTablero){
						Combate(fila + 1, columna, ejercito, direccion, reinos);
						return true;
					} else if(direccion.equals("a") && ejercito.getColumna() - 2 >= 0){
						Combate(fila, columna - 1, ejercito, direccion, reinos);
						return true;
					} else if(direccion.equals("d") && ejercito.getColumna() < ladoTablero){
						Combate(fila, columna + 1, ejercito, direccion, reinos);
						return true;
					} else{
						System.out.println("Direccion invalida, por favor ingrese los valores de nuevo");
						return false;
					}
				}
		System.out.println("Ejercito no encontrado, por favor ingrese los datos de nuevo");
		return false;
	}
	
	public static void Movimiento(ArrayList<Reino> reinos, int turno){
		String jugador = "", posicion = "", direccion = "";
		System.out.println("Jugador 1: " + reinos.get(0).getNombre() + "\tJugador2: " + reinos.get(1).getNombre());
		System.out.println("Ingrese la posicion del ejercito a mover. Ejemplo (1x2)");
		
		turno %= 2;
		if(turno == 0) jugador = reinos.get(0).getNombre();
		if(turno == 1) jugador = reinos.get(1).getNombre();
		System.out.println("Reino Actual: " + jugador);
		
		while(true){	//Verifica que los datos ingresados sean validos, tanto el ejercito como la direccion elegida
			posicion = sc.nextLine();
			System.out.println("Ingrese la direccion de movimiento ( w: arriba; s: abajo; a: izquierda; d: derecha)");
			direccion = sc.nextLine();
			if(CumpleCondicion(posicion, direccion, reinos, jugador)) break;
		}
	}

    public static void Jugar(ArrayList<Reino> reinos){
        ImprimirReinos(reinos);
        ImprimirTableroEjercitos(reinos);
		int turno = 0;
		while(true){
			Movimiento(reinos, turno);
			turno++;
			ImprimirTableroEjercitos(reinos);
			if(reinos.get(0).getEjercitos().size() == 0){
				System.out.println("Se termino el juego. Ganador: " + reinos.get(1).getNombre());
				break;
			}
			if(reinos.get(1).getEjercitos().size() == 0){
				System.out.println("Se termino el juego. Ganador: " + reinos.get(0).getNombre());
				break;
			}
		}
	}
	
    public static void Actualizar2(ArrayList<Ejercito> ejercitos){
        filas.clear();
        columnas.clear();
		for(Ejercito ejercito : ejercitos)
            for(Soldado soldado : ejercito.getSoldados()){
                filas.add(soldado.getFila());
                columnas.add(soldado.getColumna());
            }
    }
	
	public static void ImprimirTableroSoldados(ArrayList<Ejercito> ejercitos) {
		for (int i = 0; i < ladoTablero; i++) {
			int cont = 1;
            System.out.println("\n+---+---+---+---+---+---+---+---+---+---+");
            System.out.print("|");
            for (int j = 0; j < ladoTablero; j++) {
				cont = 1;
                boolean encontrado = false;
                for (Ejercito ejercito: ejercitos) {
                    for (Soldado soldado : ejercito.getSoldados()) {
                        if (soldado.getFila() == (i + 1) && soldado.getColumna() == (j + 1)) {
                            System.out.print(" " + cont + " ");
                            encontrado = true;
                            break; // Salir del bucle de ejércitos si se ha encontrado y mostrado el ejército
                        }
                    }
                    if (encontrado) {
                        break; // Salir del bucle de reinos si se encontró el ejército
                    }
					cont = 2;
                }
                if (!encontrado) {
                    System.out.print("   "); // Espacios si no se encontró ningún ejército en esa posición
                }
                System.out.print("|");
            }
        }
        System.out.println("\n+---+---+---+---+---+---+---+---+---+---+\n");
    }

	public static void CrearSoldados(ArrayList<Ejercito> ejercitos){
		for(Ejercito ejercito: ejercitos){
			for(int i = 0; i < (int)(Math.random() * maxSoldados + 1); i++){
				int fila = 0, columna = 0, contador = 0;
				while(true){
					contador = 0;
					fila = (int)(Math.random() * ladoTablero + 1);
					columna = (int)(Math.random() * ladoTablero + 1);
					if(Verificar(fila, columna)) contador++;
					// Si el contador es más de 0, significa que hay al menos 1 coincidencia de ejércitos con la misma fila y columna, por ende, se generan otros valores para fila y columna
					if(contador == 0) break;    
				}
				int vida = (int)(Math.random() * 5 + 1), ataque = (int)(Math.random() * 5 + 1), defensa = (int)(Math.random() * 5 + 1), velocidad = (int)(Math.random() * 5 + 1);
				Soldado soldado = new Soldado("Soldado " + ejercito.getEjercito() + "x" + (i + 1), vida, ejercito.getEjercito(), fila, columna, ataque, defensa, velocidad);
				ejercito.getSoldados().add(soldado);        // Agregando los datos del ejército al reino
				Actualizar2(ejercitos);
			}
		}
    }
	
	public static void Combate2(int fila, int columna, Soldado soldadoActual, int index, String direccion, String ejercitoActual, ArrayList<Ejercito> ejercitos){
		int contador = 2;
		boolean soldadoEncontrado = false; // Bandera para verificar si se encontró un ejército en la posición deseada
		for(Ejercito ejercito : ejercitos){
			contador--;
            for(Soldado soldado : ejercito.getSoldados()){
				if(soldado.getFila() == fila && soldado.getColumna() == columna && !soldado.getNombre().equals(soldadoActual.getNombre()) && !ejercito.getNombre().equals(ejercitoActual)){
					//Empieza el combate entre soldados
					int num = (int)(Math.random() * 100 + 1);
					double total = soldadoActual.getVidaActual() + soldado.getVidaActual();
					double probabilidadA = 100 * (soldadoActual.getVidaActual() / total);
					System.out.println("Probabilidades de ganar:\nSoldado actual: " + probabilidadA + "% --- Soldado enemigo: " + (100 - probabilidadA) + "%");
					if(num < probabilidadA){		// El ganador seria el soldado actual
						ejercitos.get(contador).getSoldados().get(index).cambiarPosicion(soldadoActual.getVidaActual() + 1, soldado.getFila(), soldado.getColumna());
						ejercito.getSoldados().remove(soldado);
						System.out.println("El Soldado actual gano");
					} else {		// Si no el ganador seria el soldado rival
						soldado.setVidaActual(soldado.getVidaActual() + 1);
						ejercitos.get(contador).getSoldados().remove(index);
						System.out.println("El Soldado enemigo gano");
					}
					soldadoEncontrado = true;
					break;
				} else if(soldado.getFila() == fila && soldado.getColumna() == columna && !soldado.getNombre().equals(soldadoActual.getNombre()) && ejercito.getNombre().equals(ejercitoActual)){
					System.out.println("Accion no valida, no se permiten enfrentamientos locales");
					soldadoEncontrado = true;
					break;
				}
			}
			if (soldadoEncontrado) {
				break;
			}
		}
		if (!soldadoEncontrado) {
			soldadoActual.setFila(fila);
			soldadoActual.setColumna(columna);
		}
	}	
	
	public static boolean CumpleCondicion2(String t, String direccion, ArrayList<Ejercito> ejercitos, String ejercitoActual){
		int fila = Integer.parseInt(t.substring(0,t.indexOf("x")));
		int columna = Integer.parseInt(t.substring(t.indexOf("x") + 1, t.length()));
		for(Ejercito ejercito : ejercitos)
            for(Soldado soldado : ejercito.getSoldados())
				if(soldado.getFila() == fila && soldado.getColumna() == columna && ejercito.getNombre().equals(ejercitoActual)){
					if(direccion.equals("w") && soldado.getFila() - 2 >= 0){
						Combate2(fila - 1, columna, soldado, ejercito.getSoldados().indexOf(soldado), direccion, ejercito.getNombre(), ejercitos);
						return true;
					} else if(direccion.equals("s") && soldado.getFila() < ladoTablero){
						Combate2(fila + 1, columna, soldado, ejercito.getSoldados().indexOf(soldado), direccion, ejercito.getNombre(), ejercitos);
						return true;
					} else if(direccion.equals("a") && soldado.getColumna() - 2 >= 0){
						Combate2(fila, columna - 1, soldado, ejercito.getSoldados().indexOf(soldado), direccion, ejercito.getNombre(), ejercitos);
						return true;
					} else if(direccion.equals("d") && ejercito.getColumna() < ladoTablero){
						Combate2(fila, columna + 1, soldado, ejercito.getSoldados().indexOf(soldado), direccion, ejercito.getNombre(), ejercitos);
						return true;
					} else{
						System.out.println("Direccion invalida, por favor ingrese los valores de nuevo");
						return false;
					}
				}
		System.out.println("Soldado no encontrado, por favor ingrese los datos de nuevo");
		return false;
	}
	
	public static void Movimiento2(ArrayList<Ejercito> ejercitos, int turno){
		String jugador = "", posicion = "", direccion = "";
		System.out.println("Jugador 1: Ejercito " + ejercitos.get(0).getNombre() + "\tJugador2: Ejercito " + ejercitos.get(1).getNombre());
		System.out.println("Ingrese la posicion del soldado a mover. Ejemplo (1x2)");
		
		turno %= 2;
		if(turno == 0) jugador = ejercitos.get(0).getNombre();
		if(turno == 1) jugador = ejercitos.get(1).getNombre();
		System.out.println("Ejercito Actual: " + jugador);
		
		while(true){	//Verifica que los datos ingresados sean validos, tanto el ejercito como la direccion elegida
			posicion = sc.nextLine();
			System.out.println("Ingrese la direccion de movimiento ( w: arriba; s: abajo; a: izquierda; d: derecha)");
			direccion = sc.nextLine();
			if(CumpleCondicion2(posicion, direccion, ejercitos, jugador)) break;
		}
		
	}
	
	public static void Jugar2(ArrayList<Ejercito> ejercitos, Reino reino){
        CrearSoldados(ejercitos);
		ImprimirTableroSoldados(ejercitos);
		int turno = 0;
		while(true){
			Movimiento2(ejercitos, turno);
			turno++;
			ImprimirTableroSoldados(ejercitos);
			System.out.println(ejercitos.get(0).getSoldados().size() + "x" + ejercitos.get(1).getSoldados().size());
			if(ejercitos.get(0).getSoldados().size() == 0){
				System.out.println("Se termino el enfrentamiento. Ganador: " + ejercitos.get(1).getNombre());
				reino.getEjercitos().remove(reino.getEjercitos().indexOf(ejercitos.get(0)));
				break;
			}
			if(ejercitos.get(1).getSoldados().size() == 0){
				System.out.println("Se termino el enfrentamiento. Ganador: " + ejercitos.get(0).getNombre());
				reino.getEjercitos().remove(reino.getEjercitos().indexOf(ejercitos.get(1)));
				break;
			}
		}
    }
}