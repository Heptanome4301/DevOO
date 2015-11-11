package modele;

import java.util.Date;

/**
 * Cette classe repr�sente un intervalle de temps souhait� pour une livraison. Deux fen�tres de livraison ne doivent
 * pas se chavaucher (effets de bord non pr�vus), mais plusieurs livraisons peuvent recqu�rir une fen�tre de livraison
 * identique.
 */

public class FenetreLivraison {
	
        /**
         * L'heure de d�but de la fen�tre.
         */
	private Date heureDebut;
        /**
         * L'heure de fin de la fen�tre.
         */
	private Date heureFin;
	
        /**
         * Le constructeur de la classe FenetreLivraison.
         * @param heureDebut l'heure de d�but.
         * @param heureFin l'heure de fin.
         */
	public FenetreLivraison(Date heureDebut,Date heureFin){
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		
	}

        
        /**
        * Surcharge du calcul de l'index de hachage afin de pouvoir utiliser un HashSet.
        * @return l'index de hachage.
        */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((heureDebut == null) ? 0 : heureDebut.hashCode());
		result = prime * result
				+ ((heureFin == null) ? 0 : heureFin.hashCode());
		return result;
	}

        
        /**
         * Surcharge de la m�thode d'�galit�.
         * @param obj l'objet � comparer � cette fen�tre de livraison.
         * @return true si l'heure de d�but et l'heure de fin correspondent.
         */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FenetreLivraison other = (FenetreLivraison) obj;
		if (heureDebut == null) {
			if (other.heureDebut != null)
				return false;
		} else if (!heureDebut.equals(other.heureDebut))
			return false;
		if (heureFin == null) {
			if (other.heureFin != null)
				return false;
		} else if (!heureFin.equals(other.heureFin))
			return false;
		return true;
	}


        /**
         * Accesseur de l'attribut heureDebut.
         * @return l'heure de debut de la fen�tre.
         */
	public Date getHeureDebut() {
		return heureDebut;
	}
        
        /**
         * Accesseur de l'attribut heureFin.
         * @return l'heure de fin de la fen�tre.
         */
	public Date getHeureFin() {
		return heureFin;
	}
	
        /**
         * Surcharge de la m�thode d'affichage.
         * @return une cha�ne de caract�re d�crivant la fen�tre.
         */
        @Override
        public String toString() {
                return "FenetreLivraison [heureDebut=" + heureDebut + ", heureFin="
                                + heureFin + "]";
        }
        


}
