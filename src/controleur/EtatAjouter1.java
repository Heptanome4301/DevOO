package controleur;

import modele.Adresse;
import modele.Livraison;
import modele.Plan;

public class EtatAjouter1 extends EtatIni {

	public EtatAjouter1() {
		// TODO Auto-generated constructor stub
	}
	
	public void clicNoeud(Adresse adresse, Livraison livraison,Tournee tournee, ListeDeCmd listeCmd) {
		Controleur.etatAjouter2.setAdresse(adresse);
		Controleur.setEtatCourant(Controleur.etatAjouter2);
	}
	
	public void clicDroit() {
		// action = annuler
		Controleur.setEtatCourant(Controleur.etatIni);
	}

}
