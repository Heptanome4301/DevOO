package controleur;

import java.io.File;

import modele.Adresse;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;
import util.Constants;
import vue.Fenetre;

import xml.ExceptionXML;
import xml.OuvreurDeFichiersXML;

public class EtatIni implements Etat {

	public EtatIni() {
	}

	@Override
	public void undo(Fenetre fenetre, ListeDeCmd listeCmd) {
		// Does nothing (or return exception?)
	}

	@Override
	public void redo(Fenetre fenetre, ListeDeCmd listeCmd) {
		// Does nothing (or return exception?)
	}

	@Override
	public void genererFeuilleDeRoute(Fenetre fenetre, Tournee tournee) {
		fenetre.signalerErreur(Constants.ERR_GENERE_FEUILLE);
	}

	@Override
	public void clicDroit(Fenetre fenetre) {
		// Does nothing
	}

	@Override
	public void clicNoeud(Fenetre fenetre, Adresse adresse, Plan plan,
			ListeDeCmd listeCmd) {
		// throw new UnsupportedOperationException(); //todo pourquoi tous ces
		// arguments?
	}

	@Override
	public void chargerPlan(Fenetre fenetre, Plan plan, ListeDeCmd listeCmd,File xml) {
		//File xml;
		try {
			//xml = OuvreurDeFichiersXML.getInstance().ouvre();
			fenetre.desactiverBuotonsModification();
			fenetre.getVue().deselection();
			fenetre.clear();
			plan.chargerPlan(xml);
			listeCmd.clear();
			Controleur.setEtatCourant(Controleur.etatPlan);
			fenetre.ecrireLog(Constants.LOGS_PLAN);
		} catch (Exception e) {
			//plan.clear();
			fenetre.signalerErreur(e.getMessage());
			Controleur.setEtatCourant(Controleur.etatIni);
		}
	}

	@Override
	public void chargerLivraisons(Fenetre fenetre, Plan plan,
			ListeDeCmd listeCmd,Controleur c) {
		fenetre.signalerErreur(Constants.ERR_CHARGEMENT_LIVRAISON);
	}

	@Override
	public void calculerTournee(Fenetre fenetre, Tournee tournee) {
		fenetre.signalerErreur(Constants.ERR_CALCUL_TOURNEE);
	}

	@Override
	public void ajouter(Fenetre fenetre) {
		// Does nothing
	}

	@Override
	public void supprimer(Fenetre fenetre) {
		// Does nothing
	}

	@Override
	public void echanger(Fenetre fenetre) {
		// Does nothing
	}

	@Override
	public void clicListLivraisons(Fenetre fenetre, Livraison livraison) {
		// throw new UnsupportedOperationException();
	}

	@Override
	public void deplacer(Fenetre fenetre) {
		// Does nothing
	}
}
