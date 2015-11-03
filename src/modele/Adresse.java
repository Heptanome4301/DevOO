package modele;

import java.util.ArrayList;
import java.util.Collection;

public class Adresse {

	private int id;
	private int x;
	private int y;
	private Collection<Troncon> tronconsSortants;
	
	public Adresse(int id, int x, int y){
            this.id= id;
            this.x = x;
            this.y = y;
            tronconsSortants = new ArrayList<Troncon>();
	}
	
	public void ajouterTroncon(Troncon t){
            tronconsSortants.add(t);
	}
	
	
	public int getId(){
            return id;
	}
	
	public int getX(){
            return x;
	}
	
	public int getY(){
            return y;
	}
	
	public Collection<Troncon> getTroncons(){
            return tronconsSortants;
	}
}
