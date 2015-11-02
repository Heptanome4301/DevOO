package controleur;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;

public class EtatIni implements Etat {

	public EtatIni() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void undo(ListeDeCmd listeCmd) throws Exception {
		listeCmd.undo();
	}

	@Override
	public void redo(ListeDeCmd listeCmd) throws Exception {
		listeCmd.redo();
	}

	@Override
	public void genererFeuilleDeRoute(String fichier,Tournee tournee) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void clicDroit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clicNoeud(Adresse adresse,Livraison livraison, Tournee tournee, ListeDeCmd listeCmd) {
		// TODO Auto-generated method stub
		
	}

}
