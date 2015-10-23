package modele;

import java.util.ArrayList;

public class Chemin {
	
	private ArrayList<Troncon> troncons;
	private double duree;
	
	public Chemin(){
		troncons = new ArrayList<Troncon>();
	}
	
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
}
