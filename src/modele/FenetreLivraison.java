package modele;

import java.util.Date;

public class FenetreLivraison {
	//private Collection<Livraison> livraisons;
	private Date heureDebut;
	private Date heureFin;
	
	public FenetreLivraison(Date heureDebut,Date heureFin){
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		//this.livraisons = new ArrayList<Livraison>();
		
	}


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
	 * ajouter une livraison a la fenetre 
	 * @param livraison
	 * @throws Exception si livraison deja existante
	 */
	//void ajouterLivraison(Livraison livraison) throws Exception{
		//this.livraisons.add(livraison);
	//}

	//public Collection<Livraison> getLivraisons() {
	//	return livraisons;
	//}

	public Date getHeureDebut() {
		return heureDebut;
	}

	public Date getHeureFin() {
		return heureFin;
	}
	
	
}
