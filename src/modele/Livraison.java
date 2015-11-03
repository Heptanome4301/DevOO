package modele;

import java.util.Date;

public class Livraison {
	private Date horaire;
	private Adresse adresse;
	private FenetreLivraison fenetreLivraison;
	private int id;
	
	
	public int getId() {
		return id;
	}


	@Override
	public boolean equals(Object obj) {
		if( obj!=null && obj instanceof Livraison ) {
			return getId() == ((Livraison)obj).getId();

		}
		return false;
	}
	
	
	/**
	 * appelée au chargement du fichier des livraison
	 * @param id 
	 * @param horaire
	 * @param adresse
	 * @param fenetreLivraison
	 */
	public Livraison(int id, Adresse adresse,FenetreLivraison fenetreLivraison){
		this.horaire = null;
		this.adresse = adresse;
		this.fenetreLivraison = fenetreLivraison;
		this.id = id;
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
