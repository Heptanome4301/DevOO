package controleur;

import tsp.Graphe;
import modele.Plan;

import java.io.File;

import modele.Adresse;
import modele.Tournee;

public interface Etat {
	void undo(ListeDeCmd listeCmd) throws Exception;
	void redo(ListeDeCmd listeCmd) throws Exception;
	void clicNoeud(Adresse adresse,Plan plan,Tournee tournee, ListeDeCmd listeCmd) throws Exception;
	Graphe chargerPlan(Plan plan,File file) throws Exception;
	Tournee chargerLivraisons(Plan plan, File file) throws Exception;
	Graphe calculerTournee(Tournee tournee); 
	void genererFeuilleDeRoute(String fichier,Tournee tournee) throws Exception;
	void clicDroit();
}
