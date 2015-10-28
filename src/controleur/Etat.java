package controleur;

import modele.Livraison;
import modele.Plan;
import modele.Adresse;

public interface Etat {
	void undo(ListeDeCmd listeCmd) throws Exception;
	void redo(ListeDeCmd listeCmd) throws Exception;
	void clicNoeud(Adresse adresse,Plan plan, ListeDeCmd listeCmd);
/*	Graph chargerPlan(File plan);
	Graph chargerLivraisons(File livraisons);
	Graph calculerTournee(); */
	void genererFeuilleDeRoute(String fichier);
	void clicDroit();
}
