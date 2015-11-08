package controleur;

import java.io.File;

import modele.Plan;
import modele.Tournee;
import vue.Fenetre;

public class EtatTournee extends EtatIni {

	public EtatTournee() {
		// TODO Auto-generated constructor stub
	}

	public Tournee chargerLivraisons(Fenetre fenetre, Plan plan, File file){
		// TODO Auto-generated method stub
//		plan.chargerLivraison();
		// plan.chargerLivraison(file);
		Controleur.setEtatCourant(Controleur.etatLivraison);
		return null;
	}
	
	public void genererFeuilleDeRoute(Fenetre fenetre, String fichier,Tournee tournee){
		try {
			tournee.feuilleDeRoute(fichier);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void undo(Fenetre fenetre, ListeDeCmd listeCmd){
		try {
			listeCmd.undo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void redo(Fenetre fenetre, ListeDeCmd listeCmd){
		try {
			listeCmd.redo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
