package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

public class Adresse extends Observable {

	private int id;
	private int x;
	private int y;
	private Livraison livraison;
	private Collection<Troncon> tronconsSortants;
	
	protected void setLivraison(Livraison l){
		this.livraison = l;
	}
	
	public boolean estAssocierAvecLivraison(){
		return livraison != null;
	}
	
	public Adresse(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		tronconsSortants = new ArrayList<Troncon>();
		livraison = null;
	}
	
	
	@Override
	public String toString() {
		String res = "Adresse " + id + "\r\n"
				+ "x=" + x + ", y=" + y;
		if(estAssocierAvecLivraison() ){
			res += "\n"+livraison;
		}
		return res;
	}

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





	public void ajouterTroncon(Troncon t) {
		tronconsSortants.add(t);
		notifyObservers(t);
	}

	protected void retirerTroncon(Troncon t) {
		tronconsSortants.remove(t);
		notifyObservers(t);
	}

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Collection<Troncon> getTroncons() {
		return tronconsSortants;
	}

	public void accept(Visiteur visiteur,boolean estEntrepot) {
		for (Troncon t : getTroncons()) {
			t.accept(visiteur);
		}
		visiteur.visite(this, estEntrepot);
		
	}
}
