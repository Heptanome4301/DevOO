package modele;

import java.util.ArrayList;
import java.util.Collection;

public class Adresse {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adresse other = (Adresse) obj;
		if (id != other.id)
			return false;
		return true;
	}

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
