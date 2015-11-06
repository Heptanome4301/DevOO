package controleur;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;

public class EtatEchanger1 extends EtatIni {
	
	
	
	public EtatEchanger1() {
		// TODO Auto-generated constructor stub
	}
	
	public void clicNoeud(Adresse adresse,Tournee tournee, ListeDeCmd listeCmd) {
		Controleur.etatEchanger2.setAdresse(adresse);
		Controleur.setEtatCourant(Controleur.etatEchanger2);
	}
	
	public void clicDroit() {
		// action = annuler
		Controleur.setEtatCourant(Controleur.etatTournee);
	}
}
