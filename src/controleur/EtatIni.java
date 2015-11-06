package controleur;

import tsp.Graphe;

import java.io.File;

import modele.Adresse;
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
	public void clicNoeud(Adresse adresse,Plan plan, Tournee tournee, ListeDeCmd listeCmd) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public Graphe chargerPlan(Plan plan, File file) throws Exception {
		plan.chargerPlan(file);
		Controleur.setEtatCourant(Controleur.etatPlan);
		return null;
		//TODO
	}

	@Override
	public Tournee chargerLivraisons(Plan plan,File file) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public Graphe calculerTournee(Tournee tournee) {
		throw new UnsupportedOperationException();
	}

}
