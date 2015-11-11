package vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controleur.Controleur;
import util.Constants;

public class EcouteurDeBoutons implements ActionListener {
	private Controleur controleur;

	public EcouteurDeBoutons(Controleur c) {
		this.controleur = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {

		case Constants.CHARGER_PLAN:
			controleur.chargerPlan();
			break;
		case Constants.CHARGER_LIVRAISONS:
			controleur.chargerLivraisons();
			break;
		case Constants.CALCULER_TOURNEE:
			controleur.calculerTournee();
			break;
		case Constants.AJOUTER_LIVRAISONS:
			controleur.ajouter();
			break;
		case Constants.SUPPRIMER_LIVRAISON:
			controleur.supprimer();
			break;
		case Constants.INVERSER_LIVRAISONS:
			controleur.echanger();
			break;
		case Constants.DEPLACER_LIVRAISON:
			controleur.deplacer();
			break;
		case Constants.SAUVEGARDER_FEUILLE_DE_ROUTE:
			controleur.genererFeuilleDeRoute();
			break;
		}

	}

}
