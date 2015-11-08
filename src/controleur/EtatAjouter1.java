package controleur;

import modele.Adresse;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;
import vue.Fenetre;

public class EtatAjouter1 extends EtatIni {

	public EtatAjouter1() {
		// TODO Auto-generated constructor stub
	}
	
	public void clicNoeud(Fenetre fenetre, Adresse adresse, Tournee tournee, ListeDeCmd listeCmd) {
		Controleur.etatAjouter2.setAdresse(adresse);
		Controleur.setEtatCourant(Controleur.etatAjouter2);
	}
	
	public void clicDroit() {
		// action = annuler
		Controleur.setEtatCourant(Controleur.etatTournee);
	}

}
