package modele;

public interface VisiteurPlan {
	public void visite(Adresse a,boolean estEntrepot);
	public void visite(Troncon c,boolean estDansTournee);
	// public void visite(Tournee t);
	
}
