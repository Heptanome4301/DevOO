package controleur;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;
import util.Constants;
import vue.Fenetre;
import xml.OuvreurDeFichiersXML;

import java.io.File;

public class EtatPlan extends EtatIni {

	public EtatPlan() {
		// TODO Auto-generated constructor stub
	}

	public void chargerLivraisons(Fenetre fenetre, Plan plan){
		File xml;
		try {
			xml = OuvreurDeFichiersXML.getInstance().ouvre();
			plan.chargerLivraison(xml);
			Controleur.setEtatCourant(Controleur.etatLivraison);
			//fenetre.ecrireList(plan.getTournee().getLivraisons().toArray(new Livraison[0]));
			fenetre.ecrireLog(Constants.LOGS_LIVRAISON);
		} catch (Exception e){
			fenetre.signalerErreur(e.getMessage());
			Controleur.setEtatCourant(Controleur.etatPlan);
		}
	}
	//TODO implémenter clicNoeuds : la mise à jour de l'affichage doit passer par le controlleur
	
	public void clicNoeud(Fenetre fenetre, Adresse adresse,Plan plan, Tournee tournee, ListeDeCmd listeCmd){
		/*VueGraphique vue = fenetre.getVue();
		if (adresse == null) {
			vue.deselection();
		} else {
			vue.selection(adresse.getId());
			fenetre.selectionList(adresse.getLivraison());
			fenetre.ecrireInfos(adresse.toString());
		}*/

	}
}
