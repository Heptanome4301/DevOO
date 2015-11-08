package modele;

import java.util.ArrayList;
import java.util.Collection;


public class Chemin {
	
	private Collection<Troncon> troncons;
	private int duree;
	private Adresse depart,arrivee;
	
	public Chemin(Adresse depart, Adresse arrivee){
		troncons = new ArrayList<Troncon>();
		this.depart = depart;
		this.arrivee = arrivee;
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
			//res+=t.getNomRue() + " ";
			res = t.getNomRue() + " " + res;
		}
		
		return res;
	}
	
	public int getDuree(){
		return duree;
	}
	
	public Collection<Troncon> getTroncons() {
		return troncons;
	}

	public Adresse getDepart() {
		return depart;
	}

	public Adresse getArrivee() {
		return arrivee;
	}

	public boolean contient(Troncon t) {
		return troncons.contains(t);
	}
}
