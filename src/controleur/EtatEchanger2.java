package controleur;

import modele.Adresse;
import modele.Plan;
import modele.Tournee;
import util.Constants;
import vue.Fenetre;

public class EtatEchanger2 extends EtatTournee {

	/**
         * L'adresse récupérée par l'EtatEchanger1, c'est à dire la première adresse à échanger.
         */
	private Adresse adresse1;

	public EtatEchanger2() {
		adresse1 = null;
	}
        
        /**
         * Mutateur de l'attribut adresse1
         * @param adresse
         */
	public void setAdresse(Adresse adresse) {
		this.adresse1 = adresse;
	}

	@Override
	public void clicNoeud(Fenetre fenetre, Adresse adresse2, Plan plan,
			ListeDeCmd listeCmd) {
		if ( adresse2.estAssocierAvecLivraison()) {
			CmdEchanger cmd = new CmdEchanger(adresse1.getLivraison(),
					adresse2.getLivraison(), plan.getTournee());
			listeCmd.ajoute(cmd);
			cmd.doCmd();
			Controleur.setEtatCourant(Controleur.etatTournee);
			fenetre.ecrireLog(Constants.LOGS_DEFAULT);
		} else {
			fenetre.signalerErreur(Constants.ERR_PAS_ADRESSE_LIVRAISON);
			fenetre.ecrireLog(Constants.LOGS_ECHANGER2);
		}
	}
        
        @Override
	public void clicDroit(Fenetre fenetre) {
		adresse1 = null;
		Controleur.setEtatCourant(Controleur.etatTournee);
		fenetre.ecrireLog(Constants.LOGS_DEFAULT);
	}

}
