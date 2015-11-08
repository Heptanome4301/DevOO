package vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controleur.Controleur;

public class EcouteurDeBoutons implements ActionListener {
	private Controleur controleur;
	
	private final String CHARGER_PLAN = "Charger un plan";
	private final String CHARGER_LIVRAISONS = "Charger des livraisons";
	private final String CALCULER_TOURNEE = "Calculer une tournée";
	private final String AJOUTER_LIVRAISONS = "Ajouter une livraison";
	private final String SUPPRIMER_LIVRAISON = "Supprimer une livraison";
	private final String INVERSER_LIVRAISONS = "Inverser deux livraisons";
	private final String SAUVEGARDER_FEUILLE_DE_ROUTE = "Sauvegarder le feuille de route";
	
	public EcouteurDeBoutons(Controleur c)
	{
		this.controleur = c;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
				
		switch (e.getActionCommand()) {
		
		case CHARGER_PLAN:
			controleur.chargerPlan();
			break;
		case CHARGER_LIVRAISONS:
			controleur.chargerLivraisons();
			break;
		case CALCULER_TOURNEE:
			controleur.calculerTournee();
			break;
		case AJOUTER_LIVRAISONS:
			controleur.ajouter();
			break;
		case SUPPRIMER_LIVRAISON:
			controleur.supprimer();
			break;
		case INVERSER_LIVRAISONS:
			controleur.ajouter();
			break;
		case SAUVEGARDER_FEUILLE_DE_ROUTE:
			controleur.genererFeuilleDeRoute();
			break;
		}
		
	}

}
