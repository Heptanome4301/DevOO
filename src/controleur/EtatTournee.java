package controleur;

import java.io.File;

import modele.Plan;
import modele.Tournee;

public class EtatTournee extends EtatIni {

	public EtatTournee() {
		// TODO Auto-generated constructor stub
	}

	public Tournee chargerLivraisons(Plan plan,File file) throws Exception {
		// TODO Auto-generated method stub
//		plan.chargerLivraison();
		// plan.chargerLivraison(file);
		Controleur.setEtatCourant(Controleur.etatLivraison);
		return null;
	}
	
	public void genererFeuilleDeRoute(String fichier,Tournee tournee) throws Exception {
		tournee.feuilleDeRoute(fichier);
	}
	
	public void undo(ListeDeCmd listeCmd) throws Exception {
		listeCmd.undo();
	}

	public void redo(ListeDeCmd listeCmd) throws Exception {
		listeCmd.redo();
	}
	
}
