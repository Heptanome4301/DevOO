package modele;

import java.util.Date;

public class Livraison {
	private Date horaire;
	private Adresse adresse;
	private FenetreLivraison fenetreLivraison;
	
	
	@Override
	public boolean equals(Object obj) {
		if( obj!=null && obj instanceof Livraison ) {
			return 
			(((Livraison)obj).getAdresse().getId() == adresse.getId())
			&&
			(((Livraison)obj).getFenetreLivraison().getId() == fenetreLivraison.getId());
		}
		return false;
	}
	
	
	/**
	 * appelée au chargement du fichier des livraison
	 * @param horaire
	 * @param adresse
	 * @param fenetreLivraison
	 */
	public Livraison(Adresse adresse,FenetreLivraison fenetreLivraison){
		this.horaire = null;
		this.adresse = adresse;
		this.fenetreLivraison = fenetreLivraison;
	}


	public Date getHoraire() {
		return horaire;
	}


	public Adresse getAdresse() {
		return adresse;
	}


	public FenetreLivraison getFenetreLivraison() {
		return fenetreLivraison;
	}


	public void setHoraire(Date horaire) {
		this.horaire = horaire;
	}
	
	
	
	
}
