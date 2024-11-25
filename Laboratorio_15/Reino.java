import java.util.*;

public class Reino {
	private String reino;
	private String letra;
	private ArrayList<Ejercito> ejercitos = new ArrayList<>();
	
	public Reino(String reino){
		this.reino = reino;
		this.letra = reino.substring(0,1);
	}

	public String getNombre(){
		return reino;
	}
	
	public String getLetra(){
		return letra;
	}
	
	public ArrayList<Ejercito> getEjercitos(){
		return ejercitos;
	}
	
	public String toString(){
		return "Reino: " + reino;
	}
}