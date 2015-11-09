package modele;

import java.util.Date;

/**
 * Cette classe représente à la fois une demande de livraison sur une adresse et la livraison qui sera effectuée.
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
         * La fenêtre de livraison désirée pour cette demande de livraison.
         */
	private FenetreLivraison fenetreLivraison;
        /**
         * L'identifiant de la demande de livraison.
         */
	private int id;
        /**
         * Booléen positionné lors du calcul de l'heure effective de passage : si true, l'horaire de livraison
         * est en dehors de la fenêtre de livraison.
         */
	private boolean isRetard;

	
	
	/**
	 * Constructeur de la classe Livraison. L'horaire de livraison et le retard ne sont pas positionnés par
         * le constructeur.
	 * 
	 * @param id l'id de la livraison.
	 * @param adresse l'adresse de livraison.
	 * @param fenetreLivraison la fenêtre de livraison désirée.
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
         * Surcharge du calcul de l'index de hachage afin de pouvoir utiliser un HashSet.
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
         * Surcharge de la méthode d'égalité.
         * @param obj l'objet à comparer.
         * @return true si l'adresse de livraison, la fenêtre de livraison ainsi que l'id est identique.
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
         * @return l'horaire de livraison effective.
         */
	public Date getHoraire() {
		return horaire;
	}

        /**
         * Accesseur de l'attribut adresse.
         * @return l'adresse de livraison.
         */
	public Adresse getAdresse() {
		return adresse;
	}
        
        /**
         * Accesseur de l'attribut fenetreLivraison.
         * @return la fenêtre de livraison.
         */
	public FenetreLivraison getFenetreLivraison() {
		return fenetreLivraison;
	}

        /**
         * Mutateur de l'attribut horaire. Cette méthode est appelée lors du calcul de la tournée et de la mise
         * à jour de la tournée.
         * @param horaire l'horaire à établir en tant qu'heure de passage.
         */
	public void setHoraire(Date horaire) {
		this.horaire = horaire;
	}
        
        /**
         * Accesseur de l'attribut isRetard.
         * @return true si la livraison sera en retard par rapport à l'horaire désiré.
         */
	public boolean isRetard() {
		return isRetard;
	}
        
        /**
         * Accesseur de l'attribut id.
         * @return l'id de la livraison.
         */
        public int getId() {
                return id;
        }

        /**
         * Calcul du booléen isRetard : si l'horaire de livraison prévu est hors de la fenêtre de livraison
         * alors ce boolen est établi à true.
         */
	public void positionnerRetard() {
		if (horaire.after(getFenetreLivraison().getHeureFin())) {
			isRetard = true;
		}
	}
        
        /**
         * Surcharge de la méthode d'affichage.
         * @return une chaîne de caractère décrivant la livraison.
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
			Strhoraire= "(pas encore calculÃ©)";
		else
			Strhoraire = horaire.getHours()+":"+horaire.getMinutes()+":"+horaire.getSeconds();
		res += Strhoraire + "\nisRetard=" + isRetard;
		
		return res;
	}

}
