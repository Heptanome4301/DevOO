package controleur;

import modele.Adresse;
import modele.Plan;

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
	public void genererFeuilleDeRoute(String fichier) {
		// TODO Auto-generated method stub
	}

	@Override
	public void clicDroit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clicNoeud(Adresse adresse, Plan plan, ListeDeCmd listeCmd) {
		// TODO Auto-generated method stub
		
	}

}
