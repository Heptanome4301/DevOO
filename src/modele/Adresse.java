package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

public class Adresse extends Observable {

	@Override
	public String toString() {
		return "Adresse " + id + "\r\n"
				+ "x=" + x + ", y=" + y;
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

	private int id;
	private int x;
	private int y;
	private Collection<Troncon> tronconsSortants;

	public Adresse(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		tronconsSortants = new ArrayList<Troncon>();
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
}
