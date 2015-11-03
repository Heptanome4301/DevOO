package vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controleur.Controleur;

public class EcouterDeBoutons implements ActionListener {
	private Controleur controleur;
	
	public EcouterDeBoutons(Controleur c)
	{
		this.controleur = c;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
		
		case "Charger un plan":
			controleur.chargerPlan();
			break;
		case "Charger des livraisons":
			controleur.chargerLivraisons();
			break;
		case "Calculer une tournée":
			controleur.getTournee();
			break;
		case "Ajouter une livraison":
			controleur.ajouter();
			break;
		case "Supprimer une livraison":
			controleur.supprimer();
			break;
		case "Inverser deux livraisons":
			controleur.ajouter();
			break;
		case "Sauvegarder le feuille de route":
			// controleur.genererFeuilleDeRoute(fichier);
			break;
		}
		
	}

}
