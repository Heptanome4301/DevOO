package vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controleur.Controleur;
import util.Constants;

/**
 * L'ecouteur de boutons qui definit les actions a effectuer lors d'un clic sur un bouton.
 */
public class EcouteurDeBoutons implements ActionListener {
	/**
	 * Le controleur qui devra effectuer les actions associees aux boutons.
	 */
	private Controleur controleur;
	
	/**
	 * Le constructeur de l'ecouteur.
	 * @param c le controleur a notifier.
	 */
	public EcouteurDeBoutons(Controleur c) {
		this.controleur = c;
	}

	/**
	 * Surcharge de la methode actionPerformed qui definit les actions a accomplir lors d'un clic sur un bouton.
	 * @param e l'evenement declenche par un clic sur un bouton.
	 */
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
                case Constants.ANNULER:
                        controleur.undo();
                        break;
                case Constants.REFAIRE:
                        controleur.redo();
                        break;
		}

	}

}
