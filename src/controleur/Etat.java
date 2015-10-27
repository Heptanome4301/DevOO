package controleur;

import controleur.ListeDeCmd;

public interface Etat {
	void undo(ListeDeCmd listeCmd);
	void redo(ListeDeCmd listeCmd);
/*	void clicNoeud(Noeud noeud);
	Graph chargerPlan(File plan);
	Graph chargerLivraisons(File livraisons);
	Graph calculerTournee(); */
	void genererFeuilleDeRoute();
}
