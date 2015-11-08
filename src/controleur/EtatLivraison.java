package controleur;

import java.io.File;

import modele.Plan;
import modele.Tournee;
import tsp.Graphe;
import vue.Fenetre;

public class EtatLivraison extends EtatIni {

	public EtatLivraison() {
		// TODO Auto-generated constructor stub
	}
	
	public Graphe calculerTournee(Fenetre fenetre, Tournee tournee) {
		// TODO Auto-generated method stub
		tournee.calculerTournee();
		Controleur.setEtatCourant(Controleur.etatTournee);
		return null;
	}
	
	public Tournee chargerLivraisons(Fenetre fenetre, Plan plan,File file){
		// TODO Auto-generated method stub
		//plan.chargerLivraison();
		// plan.chargerLivraison(file);
		Controleur.setEtatCourant(Controleur.etatLivraison);
		return null;
	}
	
}
