package controleur;

import tsp.Graphe;
import modele.Adresse;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;

public class EtatIni implements Etat {

	public EtatIni() {
	}

	@Override
	public void undo(ListeDeCmd listeCmd) throws Exception {
		// Does nothing (or return exception?)
	}

	@Override
	public void redo(ListeDeCmd listeCmd) throws Exception {
		// Does nothing (or return exception?)
	}

	@Override
	public void genererFeuilleDeRoute(String fichier,Tournee tournee) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clicDroit() {
		// Does nothing	
	}

	@Override
	public void clicNoeud(Adresse adresse,Plan plan, Tournee tournee, ListeDeCmd listeCmd) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Graphe chargerPlan(Plan plan) throws Exception {
		plan.chargerPlan();
		Controleur.setEtatCourant(Controleur.etatPlan);
		return null;
		//TODO
	}

	@Override
	public Graphe chargerLivraisons(Plan plan) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public Graphe calculerTournee(Tournee tournee) {
		throw new UnsupportedOperationException();
	}

}
