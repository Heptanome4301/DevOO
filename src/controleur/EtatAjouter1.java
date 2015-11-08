package controleur;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;
import util.Constants;
import vue.Fenetre;

public class EtatAjouter1 extends EtatTournee {

	public EtatAjouter1() {
		// TODO Auto-generated constructor stub
		//TODO Mettre à jour les consignes dans la vue
	}

	@Override
	public void clicNoeud(Fenetre fenetre, Adresse adresse, Plan plan, Tournee tournee, ListeDeCmd listeCmd) {
		Controleur.etatAjouter2.setAdresse(adresse);
		//TODO si l'adresse est déjà une livraison, on la refoule
		Controleur.setEtatCourant(Controleur.etatAjouter2);
		fenetre.ecrireLog(Constants.LOGS_AJOUTER2);
	}

	@Override
	public void clicDroit(Fenetre fenetre) {
		// action = annuler
		Controleur.setEtatCourant(Controleur.etatTournee);
		fenetre.ecrireLog(Constants.LOGS_DEFAULT);
	}

}
