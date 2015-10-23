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
	
	public double getDuree(){
		return duree;
	}
}
