package controleur;

import tsp.Graphe;
import modele.Livraison;
import modele.Plan;
import modele.Adresse;
import modele.Tournee;

public interface Etat {
	void undo(ListeDeCmd listeCmd) throws Exception;
	void redo(ListeDeCmd listeCmd) throws Exception;
	void clicNoeud(Adresse adresse,Plan plan,Tournee tournee, ListeDeCmd listeCmd);
	Graphe chargerPlan(Plan plan) throws Exception;
	Graphe chargerLivraisons(Plan plan) throws Exception;
	Graphe calculerTournee(Tournee tournee); 
	void genererFeuilleDeRoute(String fichier,Tournee tournee) throws Exception;
	void clicDroit();
}
