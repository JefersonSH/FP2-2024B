public class Soldado{
	private String nombre;
	private int hp;
	private int fila;
	private int columna;
	private int ejercito;

	//Metodos para asignar atributos
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public void setHP(int hp){
		this.hp = hp;
	}

	public void setColumna(int columna){
		this.columna = columna;
	}
	
	public void setFila(int fila){
		this.fila = fila;
	}
	
	public void setEjercito(int ejercito){
		this.ejercito = ejercito;
	}

	//Metodos para obtener atributos
	public String getNombre(){
		return this.nombre;
	}
	
	public int getHP(){
		return this.hp;
	}

	public int getColumna(){
		return this.columna;
	}
	
	public int getFila(){
		return this.fila;
	}
	
	public int getEjercito(){
		return this.ejercito;
	}
}
