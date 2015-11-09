package modele;

public interface Visiteur {
	public void visite(Adresse a,boolean estEntrepot);
	public void visite(Troncon c,boolean estDansTournee);
	// public void visite(Tournee t);
	
}
