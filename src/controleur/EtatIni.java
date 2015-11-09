package controleur;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;
import tsp.Graphe;
import util.Constants;
import vue.Fenetre;
import xml.OuvreurDeFichiersXML;

import java.io.File;

public class EtatIni implements Etat {

	public EtatIni() {
	}

	@Override
	public void undo(Fenetre fenetre, ListeDeCmd listeCmd){
		// Does nothing (or return exception?)
	}

	@Override
	public void redo(Fenetre fenetre, ListeDeCmd listeCmd){
		// Does nothing (or return exception?)
	}

	@Override
	public void genererFeuilleDeRoute(Fenetre fenetre, String fichier,Tournee tournee){
		throw new UnsupportedOperationException();
	}

	@Override
	public void clicDroit(Fenetre fenetre) {
		// Does nothing	
	}

	@Override
	public void clicNoeud(Fenetre fenetre, Adresse adresse,Plan plan, Tournee tournee, ListeDeCmd listeCmd){
		throw new UnsupportedOperationException();  //todo pourquoi tous ces arguments?
	}

	@Override
	public void chargerPlan(Fenetre fenetre, Plan plan){
		File xml;
		try {
			xml = OuvreurDeFichiersXML.getInstance().ouvre();
			plan.chargerPlan(xml);
			Controleur.setEtatCourant(Controleur.etatPlan);
                        fenetre.ecrireLog(Constants.LOGS_PLAN);
		} catch (Exception e){
			fenetre.signalerErreur(e.getMessage());
			Controleur.setEtatCourant(Controleur.etatIni);
		}
	}

	@Override
	public void chargerLivraisons(Fenetre fenetre, Plan plan){
		fenetre.signalerErreur("Il faut charger un plan avant de pouvoir charger des livraisons.");
	}

	@Override
	public void calculerTournee(Fenetre fenetre, Tournee tournee) {
		fenetre.signalerErreur("Il faut d'abord charger un plan et des livraisons avant de pouvoir calculer la tournée");
	}

	@Override
	public void ajouter(Fenetre fenetre) {
		//Does nothing
	}

	@Override
	public void supprimer(Fenetre fenetre) {
		//Does nothing
	}

	@Override
	public void echanger(Fenetre fenetre) {
		//Does nothing
	}
}
