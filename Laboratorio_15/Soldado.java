import java.util.*;

public class Soldado{
	private String nombre;
	private int nivelVida;
	private int vidaActual;
	private boolean vive;
	private int ejercito;
	private int fila;
	private int columna;
	private int nivelAtaque;
	private int nivelDefensa;
	private int velocidad;
	private String actitud;

	public Soldado(){
		nivelVida = 0;
		vidaActual = 0;
		nivelAtaque = 0;
		nivelDefensa = 0;
		vive = true;
	}

	public Soldado(String nombre){
		this.nombre = nombre;
		nivelVida = 0;
		vidaActual = 0;
		nivelAtaque = 0;
		nivelDefensa = 0;
		vive = true;
	}

	public Soldado(String nombre, int nivelVida, int ejercito, int fila, int columna){
		this.nombre = nombre;
		this.nivelVida = nivelVida;
		this.vidaActual = nivelVida;
		this.ejercito = ejercito;
		this.fila = fila;
		this.columna = columna;
		vive = true;
	}

	public Soldado(String nombre, int nivelVida, int ejercito, int fila, int columna, int nivelAtaque, int nivelDefensa, int velocidad){
		this.nombre = nombre;
		this.nivelVida = nivelVida;
		this.vidaActual = nivelVida;
		this.ejercito = ejercito;
		this.fila = fila;
		this.columna = columna;
		this.nivelAtaque = nivelAtaque;
		this.nivelDefensa = nivelDefensa;
		this.velocidad = velocidad;
		vive = true;
	}

	public void atacar(){
		avanzar();
		actitud = "ofensiva";
	}

	public void defender(){
		velocidad = 0;
		actitud = "defensiva";
	}

	public void avanzar(){
		velocidad++;
	}

	public void retroceder(){
		if(velocidad > 0){
			velocidad = 0;
			actitud = "defensiva";
		} else velocidad --; 
	}

	public void serAtacado(int num){
		vidaActual -= num;
	}

	public void huir(){
		velocidad += 2;
		actitud = "fuga";
	}

	public void morir(){
		vidaActual = 0;
		vive = false;
	}
	
	public void cambiarPosicion(int vidaActual, int fila, int columna){
		this.vidaActual = nivelVida;
		this.fila = fila;
		this.columna = columna;
	}

	public void vivir(){
		vive = true;
	}

	public void setNombre(String nombre){
		this.nombre = nombre;
	}

	public void setVidaActual(int vidaActual){
		this.vidaActual = vidaActual;
	}

	public void setEjercito(int ejercito){
		this.ejercito = ejercito;
	}

	public void setFila(int fila){
		this.fila = fila;
	}

	public void setColumna(int columna){
		this.columna = columna;
	}

	public void setAtaque(int nivelAtaque){
		this.nivelAtaque = nivelAtaque;
	}

	public void setDefensa(int nivelDefensa){
		this.nivelDefensa = nivelDefensa;
	}

	public void setVelocidad(int velocidad){
		this.velocidad = velocidad;
	}

	public String getNombre(){
		return nombre;
	}

	public int getVidaActual(){
		return vidaActual;
	}

	public int getFila(){
		return fila;
	}

	public int getEjercito(){
		return ejercito;
	}

	public int getColumna(){
		return columna;
	}

	public int getAtaque(){
		return nivelAtaque;
	}

	public int getDefensa(){
		return nivelDefensa;
	}

	public int getVelocidad(){
		return velocidad;
	}

	public boolean getVive(){
		return vive;
	}
	
	public String toString(){
		return nombre + "Vida: " + vidaActual + "\tEjercito: " + ejercito + "\tFila y Columna: " + fila + "x" +columna +
		"\nAtaque: " + nivelAtaque + "\tDefensa: " + nivelDefensa + "\tVelocidad: " + velocidad;
	}
}