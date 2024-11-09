public class Soldado{
	private String nombre;
	private int nivelVida;
	private int vidaActual;
	private boolean vive;
	private int columna;
	private int fila;
	private int ejercito;
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
	
	public Soldado(String nombre, int nivelVida, int ejercito, int fila, int columna){
		this.nombre = nombre;
		this.nivelVida = nivelVida;
		this.vidaActual = nivelVida;
		this.ejercito = ejercito;
		this.fila = fila;
		this.columna = columna;
		vive = true;
	}
	
	public Soldado(String nombre, int nivelVida, int ejercito, int fila, int columna, int nivelAtaque, int nivelDefensa){
		this.nombre = nombre;
		this.nivelVida = nivelVida;
		this.vidaActual = nivelVida;
		this.ejercito = ejercito;
		this.fila = fila;
		this.columna = columna;
		this.nivelAtaque = nivelAtaque;
		this.nivelDefensa = nivelDefensa;
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
	
	public void setVidaActual(int vidaActual){
		this.vidaActual = vidaActual;
	}
	
	public int getVidaActual(){
		return vidaActual;
	}
	
	public void vivir(){
		vive = true;
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
	
	public int getColumna(){
		return columna;
	}
	
	public int getFila(){
		return fila;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public int getEjercito(){
		return ejercito;
	}
	
	public boolean getVive(){
		return vive;
	}	
}