package modele;

import java.util.Comparator;
import java.util.Date;

/**
 * Cette classe repr�sente � la fois une demande de livraison sur une adresse et
 * la livraison qui sera effectu�e.
 * 
 * @param <T>
 */
public class Livraison {

	/**
	 * L'horaire effective de livraison.
	 */
	private Date horaire;
	/**
	 * L'adresses de livraison.
	 */
	private Adresse adresse;
	/**
	 * La fen�tre de livraison d�sir�e pour cette demande de livraison.
	 */
	private FenetreLivraison fenetreLivraison;
	/**
	 * L'identifiant de la demande de livraison.
	 */
	private int id;
	/**
	 * Bool�en positionn� lors du calcul de l'heure effective de passage : si
	 * true, l'horaire de livraison est en dehors de la fen�tre de livraison.
	 */
	private boolean isRetard;

	/**
	 * Constructeur de la classe Livraison. L'horaire de livraison et le retard
	 * ne sont pas positionn�s par le constructeur.
	 * 
	 * @param id
	 *            l'id de la livraison.
	 * @param adresse
	 *            l'adresse de livraison.
	 * @param fenetreLivraison
	 *            la fen�tre de livraison d�sir�e.
	 */
	public Livraison(int id, Adresse adresse, FenetreLivraison fenetreLivraison) {
		this.horaire = null;
		this.adresse = adresse;
		this.fenetreLivraison = fenetreLivraison;
		this.id = id;
		this.isRetard = false;
		adresse.setLivraison(this);
	}

	/**
	 * Surcharge du calcul de l'index de hachage afin de pouvoir utiliser un
	 * HashSet.
	 * 
	 * @return l'index de hachage.
	 */
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

	/**
	 * Surcharge de la m�thode d'�galit�.
	 * 
	 * @param obj
	 *            l'objet � comparer.
	 * @return true si l'adresse de livraison, la fen�tre de livraison ainsi que
	 *         l'id est identique.
	 */
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

	/**
	 * Accesseur de l'attribut horaire.
	 * 
	 * @return l'horaire de livraison effective.
	 */
	public Date getHoraire() {
		return horaire;
	}

	/**
	 * Accesseur de l'attribut adresse.
	 * 
	 * @return l'adresse de livraison.
	 */
	public Adresse getAdresse() {
		return adresse;
	}

	/**
	 * Accesseur de l'attribut fenetreLivraison.
	 * 
	 * @return la fen�tre de livraison.
	 */
	public FenetreLivraison getFenetreLivraison() {
		return fenetreLivraison;
	}

	/**
	 * Mutateur de l'attribut horaire. Cette m�thode est appel�e lors du calcul
	 * de la tourn�e et de la mise � jour de la tourn�e.
	 * 
	 * @param horaire
	 *            l'horaire � �tablir en tant qu'heure de passage.
	 */
	public void setHoraire(Date horaire) {
		this.horaire = horaire;
	}

	/**
	 * Accesseur de l'attribut isRetard.
	 * 
	 * @return true si la livraison sera en retard par rapport � l'horaire
	 *         d�sir�.
	 */
	public boolean isRetard() {
		return isRetard;
	}

	/**
	 * Accesseur de l'attribut id.
	 * 
	 * @return l'id de la livraison.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Calcul du bool�en isRetard : si l'horaire de livraison pr�vu est hors de
	 * la fen�tre de livraison alors ce boolen est �tabli � true.
	 */
	protected void positionnerRetard() {
		if (horaire.after(getFenetreLivraison().getHeureFin())) {
			isRetard = true;
		} else {
			isRetard = false;
		}
	}

	/**
	 * Surcharge de la m�thode d'affichage.
	 * 
	 * @return une cha�ne de caract�re d�crivant la livraison.
	 */
	@Override
	public String toString() {
		/*
		 * return "Livraison [horaire=" + horaire + ", adresse=" +
		 * adresse.getId() + ", fenetreLivraison=" + fenetreLivraison + ", id="
		 * + id + ", isRetard=" + isRetard + "]";
		 */
		String res = "Livraison id=" + id + "\nhoraire=";
		String Strhoraire = "";
		if (horaire == null)
			Strhoraire= "(pas encore calculé)";
		else{
			int value = horaire.getHours();
			if(value<10) Strhoraire+="0"+value;
			else Strhoraire+=value+"";
			
			Strhoraire+=":";
			
			value = horaire.getMinutes();
			if(value<10) Strhoraire+="0"+value;
			else Strhoraire+=value+"";
			
			Strhoraire+=":";
			
			value = horaire.getSeconds();
			if(value<10) Strhoraire+="0"+value;
			else Strhoraire+=value+"";
	
		}
		res += Strhoraire + "\nisRetard=" + isRetard;
		
		return res;
	}

}
