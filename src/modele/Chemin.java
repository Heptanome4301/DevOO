package modele;

import java.util.ArrayList;
import java.util.Collection;


public class Chemin {
	
	private Collection<Troncon> troncons;
	private double duree;
	
	public Chemin(){
		troncons = new ArrayList<Troncon>();
	}
	
	/**
	 * s'il existe deja, ne pas l'ajouter
	 * @param t
	 */
	public void ajouterTroncon(Troncon t){
		troncons.add(t);
		duree = duree+t.getDuree();
	}
	
	public String toString(){
		String res = new String();
		
		for(Troncon t : troncons){
			res+=t.getNomRue() + " ";
		}
		
		return res;
	}
	
	public double getDuree(){
		return duree;
	}
	
	public Collection<Troncon> getTroncons() {
		return troncons;
	}
}
