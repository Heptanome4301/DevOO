package controleur;

import modele.Adresse;
import modele.Livraison;
import modele.Plan;

public class EtatAjouter2 extends EtatIni {

	// l'adresse selectionnée lors de l'état 1 
	private Adresse adresse;
	
	public EtatAjouter2() {
		adresse=null;
	}
	
	public void setAdresse(Adresse adresse) {
		this.adresse=adresse;
	}
	
	public void clicNoeud(Adresse adresse, Plan plan, ListeDeCmd listeCmd) {
		//TODO
	}

}
