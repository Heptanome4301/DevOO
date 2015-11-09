package controleur;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;
import tsp.Graphe;
import vue.Fenetre;

public interface Etat {
	void undo(Fenetre fenetre, ListeDeCmd listeCmd);
	void redo(Fenetre fenetre, ListeDeCmd listeCmd);
	void clicNoeud(Fenetre fenetre, Adresse adresse,Plan plan,Tournee tournee, ListeDeCmd listeCmd);
	void chargerPlan(Fenetre fenetre, Plan plan);
	void chargerLivraisons(Fenetre fenetre, Plan plan);
	void calculerTournee(Fenetre fenetre, Tournee tournee);
	void genererFeuilleDeRoute(Fenetre fenetre, String fichier,Tournee tournee);
	void clicDroit(Fenetre fenetre);
	void ajouter(Fenetre fenetre);
	void supprimer(Fenetre fenetre);
	void echanger(Fenetre fenetre);
}
