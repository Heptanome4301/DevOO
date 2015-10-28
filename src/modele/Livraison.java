package modele;

import java.util.Date;

public class Livraison {
	private Date horaire;
	private Adresse adresse;
	private FenetreLivraison fenetreLivraison;
	
	
	/**
	 * appelée au chargement du fichier des livraison
	 * @param horaire
	 * @param adresse
	 * @param fenetreLivraison
	 */
	public Livraison(Date horaire,Adresse adresse,FenetreLivraison fenetreLivraison){
		this.horaire = horaire;
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
	
	
	
	
}
