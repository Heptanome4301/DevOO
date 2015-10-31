package modele;

import java.util.Date;

public class FenetreLivraison {
	//private Collection<Livraison> livraisons;
	private Date heureDebut;
	private Date heureFin;
	private int id;
	
	public FenetreLivraison(int id,Date heureDebut,Date heureFin){
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		//this.livraisons = new ArrayList<Livraison>();
		this.id = id;
	}
	
	public int getId() {
		return id;
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
