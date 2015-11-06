package controleur;

import modele.Plan;
import modele.Tournee;
import tsp.Graphe;

public class EtatTournee extends EtatIni {

	public EtatTournee() {
		// TODO Auto-generated constructor stub
	}

	public Graphe chargerLivraisons(Plan plan) throws Exception {
		// TODO Auto-generated method stub
//		plan.chargerLivraison();
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
