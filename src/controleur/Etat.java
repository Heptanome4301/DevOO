package controleur;

import modele.Livraison;
import modele.Plan;
import modele.Adresse;

public interface Etat {
	void undo(ListeDeCmd listeCmd) throws Exception;
	void redo(ListeDeCmd listeCmd) throws Exception;
	void clicNoeud(Adresse adresse,Livraison livraison,Tournee tournee, ListeDeCmd listeCmd);
	Graph chargerPlan() throws Exception;
	Graph chargerLivraisons(Plan plan) throws Exception;
	Graph calculerTournee(Tournee tournee); 
	void genererFeuilleDeRoute(String fichier,Tournee tournee) throws Exception;
	void clicDroit();
}
