package controleur;

import tsp.Graphe;
import modele.Livraison;
import modele.Plan;
import modele.Adresse;
import modele.Tournee;

public interface Etat {
	void undo(ListeDeCmd listeCmd) throws Exception;
	void redo(ListeDeCmd listeCmd) throws Exception;
	void clicNoeud(Adresse adresse,Livraison livraison,Tournee tournee, ListeDeCmd listeCmd);
	Graphe chargerPlan() throws Exception;
	Graphe chargerLivraisons(Plan plan) throws Exception;
	Graphe calculerTournee(Tournee tournee); 
	void genererFeuilleDeRoute(String fichier,Tournee tournee) throws Exception;
	void clicDroit();
}
