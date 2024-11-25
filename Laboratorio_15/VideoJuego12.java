import java.util.*;

public class VideoJuego12 {
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
                Ejercito ejercito = new Ejercito(i + 1, "Ejercito " + reino.getLetra() + (i + 1), reino.getNombre());
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
	
	public static void CrearSoldadosAuto(ArrayList<Ejercito> ejercitos){
		for(Ejercito ejercito: ejercitos){
			boolean condicion = true;
			while(condicion){
				int fila = 0, columna = 0, contador = 0;
				System.out.println(ejercito + " - Ingrese los datos del soldado: Nombre, vida, fila, columna, ataque, defensa y velocidad");
				String nombre = sc.nextLine();
				int vida = sc.nextInt();
				sc.nextLine();
				while(true){
					contador = 0;
					fila = sc.nextInt();
					columna = sc.nextInt();
					sc.nextLine();
					if(Verificar(fila, columna)) contador++;
					// Si el contador es más de 0, significa que hay al menos 1 coincidencia de soldados con la misma fila y columna, por ende, se generan otros valores para fila y columna
					if(contador == 0) break;
					System.out.println("La posicion el soldado ya esta ocupada. Ingrese de nuevo las filas y columnas");
				}
				int ataque = sc.nextInt();
				int defensa = sc.nextInt();
				int velocidad = sc.nextInt();
				Soldado soldado = new Soldado(nombre, vida, ejercito.getEjercito(), fila, columna, ataque, defensa, velocidad);
				ejercito.getSoldados().add(soldado);        // Agregando los datos del ejército al reino
				Actualizar2(ejercitos);
				System.out.println("Soldado creado. Desea crear mas soldados para este ejercito?\n1. SI\t2.NO");
				sc.nextLine();
				String opcion = sc.nextLine();
				if(opcion.equals("2")) condicion = false; 
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
		System.out.println("Como desea jugar el enfrentamiento:\n1. Crear cada uno de los soldados\t2.Crear los soldados automaticamente");
		String opcion = sc.nextLine();
		if(opcion.equals("1")) CrearSoldadosAuto(ejercitos);
        else CrearSoldados(ejercitos);
		int turno = 0;
		
		while(true){	//Se saldra del ciclo cuando alguno de los 2 ejercitos este vacio, caso contrario se seguira preguntando por que realizar
			ImprimirTableroSoldados(ejercitos);
			System.out.println("Eliga una de las opciones:\n1.Agregar Soldado\t2. Eliminar Soldado\t3. Modificar Soldado" +
			"\n4. Soldado con mas ataque\t5. Ranking de vida\t6. Ver Datos del Ejercito y Soldados\t7. Continuar Turno");
			opcion = sc.nextLine();
			if(opcion.equals("1")){
				AgregarSoldado(ejercitos);
			} else if(opcion.equals("2")){
				EliminarSoldado(ejercitos);
			} else if(opcion.equals("3")){
				ModificarSoldado(ejercitos);
			} else if(opcion.equals("4")){
				SoldadoConMasAtaque(ejercitos);
			} else if(opcion.equals("5")){
				RankingVida(ejercitos);
			} else if(opcion.equals("6")){
				VerDatos(ejercitos);
			} else {
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
				Movimiento2(ejercitos, turno);
				turno++;
				System.out.println(ejercitos.get(0).getSoldados().size() + "x" + ejercitos.get(1).getSoldados().size());
			}
		}
		
		ImprimirTableroSoldados(ejercitos);
    }
	
	public static void AgregarSoldado(ArrayList<Ejercito> ejercitos){
		int fila = 0, columna = 0, contador = 0;
		System.out.println("Ingrese los datos del soldado: Nombre, vida, ejercito, fila, columna, ataque, defensa y velocidad");
		String nombre = sc.nextLine();
		int vida = sc.nextInt();
		int ejercito = sc.nextInt();
		sc.nextLine();
		while(true){
			contador = 0;
			fila = sc.nextInt();
			columna = sc.nextInt();
			sc.nextLine();
			if(Verificar(fila, columna)) contador++;
			// Si el contador es más de 0, significa que hay al menos 1 coincidencia de soldados con la misma fila y columna, por ende, se generan otros valores para fila y columna
			if(contador == 0) break;
			System.out.println("La posicion el soldado ya esta ocupada. Ingrese de nuevo las filas y columnas");
		}
		int ataque = sc.nextInt();
		int defensa = sc.nextInt();
		int velocidad = sc.nextInt();
		sc.nextLine();
		Soldado soldado = new Soldado(nombre, vida, ejercito, fila, columna, ataque, defensa, velocidad);
		if(soldado.getEjercito() == 1) ejercitos.get(0).getSoldados().add(soldado);
		if(soldado.getEjercito() == 2) ejercitos.get(1).getSoldados().add(soldado);
		Actualizar2(ejercitos);
		System.out.println("Soldado creado: " + soldado + "\n");
	}
	
	public static void EliminarSoldado(ArrayList<Ejercito> ejercitos){
		System.out.println("Ingrese la fila del soldado a eliminar");
		int fila = sc.nextInt();
		sc.nextLine();
		System.out.println("Ingrese la columna del soldado a eliminar");
		int columna = sc.nextInt();
		sc.nextLine();
		for(Ejercito ejercito: ejercitos){
			for(Soldado soldado: ejercito.getSoldados()){
				if(soldado.getFila() == fila && soldado.getColumna() == columna){
					ejercito.getSoldados().remove(soldado);
					break;
				}
			}
		}
		System.out.println("El soldado se ha eliminado correctamente");
	}
	
	public static void ModificarSoldado(ArrayList<Ejercito> ejercitos){
		System.out.println("Ingrese la fila del soldado a modificar");
		int fila = sc.nextInt();
		sc.nextLine();
		System.out.println("Ingrese la columna del soldado a eliminar");
		int columna = sc.nextInt();
		sc.nextLine();
		for(Ejercito ejercito: ejercitos){
			for(Soldado soldado: ejercito.getSoldados()){
				if(soldado.getFila() == fila && soldado.getColumna() == columna){
					System.out.println("Ingrese los datos del soldado: Nombre, vida, ataque, defensa y velocidad");
					String nombre = sc.nextLine();
					int vida = sc.nextInt();
					int ataque = sc.nextInt();
					int defensa = sc.nextInt();
					int velocidad = sc.nextInt();
					sc.nextLine();
					Soldado soldadoNew = new Soldado(nombre, vida, soldado.getEjercito(), fila, columna, ataque, defensa, velocidad);
					ejercito.getSoldados().set(ejercito.getSoldados().indexOf(soldado), soldadoNew);
					System.out.println("Soldado modificado: " + soldadoNew + "\n");
					break;
				}
			}
		}
	}
	
	public static void SoldadoConMasAtaque(ArrayList<Ejercito> ejercitos){
		int mayor = Integer.MIN_VALUE;
		Soldado masAtaque = new Soldado();
		for(Ejercito ejercito: ejercitos){
			for(Soldado soldado: ejercito.getSoldados()){
				if(soldado.getAtaque() > mayor){
					mayor = soldado.getAtaque();	//Si es mayor, actualiza la variable
					masAtaque = soldado;
				}
			}
		}
		System.out.println(masAtaque);
	}
	
	public static void RankingVida(ArrayList<Ejercito> ejercitos){
		Soldado[] lista = new Soldado[ejercitos.get(0).getSoldados().size() + ejercitos.get(1).getSoldados().size()];
		int contador = 0;
		for(Ejercito ejercito: ejercitos){
			for(Soldado soldado: ejercito.getSoldados()){
				lista[contador] = soldado;
				contador++;
			}
		}
		for(int i = 1; i < lista.length; i++){
			Soldado key = lista[i];
			int j = i - 1;
			while(j >= 0 && lista[j].getVidaActual() > key.getVidaActual()){
				lista[j + 1] = lista[j];
				j = j - 1;
			}
			lista[j + 1] = key;
		}
		System.out.println("Ranking de Soldados segun vida de manera descendente");
		for(int i = lista.length - 1; i >= 0; i--){
			System.out.println(lista[i]);
		}
	}
	
	public static void VerDatos(ArrayList<Ejercito> ejercitos){
		for(Ejercito ejercito: ejercitos){
			System.out.println("__________________\n" + ejercito + "\n__________________");
			for(Soldado soldado: ejercito.getSoldados()){
				System.out.println(soldado);
			}
		}
	}
}