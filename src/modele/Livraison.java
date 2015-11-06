package modele;

import java.util.Date;

public class Livraison {


	private Date horaire;
	private Adresse adresse;
	private FenetreLivraison fenetreLivraison;
	private int id;
	private boolean isRetard;
	
	
	public int getId() {
		return id;
	}


	/*@Override
	public boolean equals(Object obj) {
		if( obj!=null && obj instanceof Livraison ) {
			return getId() == ((Livraison)obj).getId() ;

		}
		return false;
	} */
	
	
	/**
	 * appel�e au chargement du fichier des livraison
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
		this.isRetard = false;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime
				* result
				+ ((fenetreLivraison == null) ? 0 : fenetreLivraison.hashCode());
		result = prime * result + ((horaire == null) ? 0 : horaire.hashCode());
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
		Livraison other = (Livraison) obj;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} else if (!adresse.equals(other.adresse))
			return false;
		if (fenetreLivraison == null) {
			if (other.fenetreLivraison != null)
				return false;
		} else if (!fenetreLivraison.equals(other.fenetreLivraison))
			return false;
		if (horaire == null) {
			if (other.horaire != null)
				return false;
		} else if (!horaire.equals(other.horaire))
			return false;
		if (id != other.id)
			return false;
		return true;
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


	public boolean isRetard() {
		return isRetard;
	}
	
	public void positionnerRetard() {
		if (horaire.after(getFenetreLivraison().getHeureFin()))  {
        	isRetard = true;
        }
	}
	
	
	@Override
	public String toString() {
		return "Livraison [horaire=" + horaire + ", adresse=" + adresse.getId()
				+ ", fenetreLivraison=" + fenetreLivraison + ", id=" + id
				+ ", isRetard=" + isRetard + "]";
	}
	
	
	
}
