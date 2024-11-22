import java.util.*;

public class Ejercito {
	private int ejercito;
	private String nombre;
	private int fila;
	private int columna;
	private String reino;
	private ArrayList<Soldado> soldados = new ArrayList<>();
	
	public Ejercito(int ejercito, String nombre, String reino){
		this.ejercito = ejercito;
		this.nombre = nombre;
		this.reino = reino;
	}
	
	public int getEjercito(){
		return ejercito;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public String getReino(){
		return reino;
	}	
	
	public void setFila(int fila){
		this.fila = fila;
	}

	public void setColumna(int columna){
		this.columna = columna;
	}
	
	public int getFila(){
		return fila;
	}
	
	public int getColumna(){
		return columna;
	}
	
	public ArrayList<Soldado> getSoldados(){
		return soldados;
	}
}