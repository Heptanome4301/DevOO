package controleur;

import tsp.Graphe;
import modele.Plan;

import java.io.File;

import modele.Adresse;
import modele.Tournee;
import vue.Fenetre;

public interface Etat {
	void undo(Fenetre fenetre, ListeDeCmd listeCmd);
	void redo(Fenetre fenetre, ListeDeCmd listeCmd);
	void clicNoeud(Fenetre fenetre, Adresse adresse,Plan plan,Tournee tournee, ListeDeCmd listeCmd);
	Graphe chargerPlan(Fenetre fenetre, Plan plan,File file);
	Tournee chargerLivraisons(Fenetre fenetre, Plan plan, File file);
	Graphe calculerTournee(Fenetre fenetre, Tournee tournee);
	void genererFeuilleDeRoute(Fenetre fenetre, String fichier,Tournee tournee);
	void clicDroit();
}
