package controleur;

public class Controleur {
	
	private ListeDeCmd historique;
	private Etat etatCourant;

	public Controleur() {
		historique = new ListeDeCmd();
		etatCourant = new EtatDefaut();
	}
	
	public void undo() {
		etatCourant.undo(historique);
	}
	
	public void redo() {
		etatCourant.redo(historique);
	}
	
	public void chargerPlan() {
		//TODO
	}
	
	public void chargerLivraisons() {
		//TODO
	}
	
	public void clicNoeud() {
		//TODO
	}
	
	public void clicDroit() {
		//TODO
	}
	
	public void ajouter() {
		etatCourant = new EtatAjouter1();
	}
	
	public void supprimer() {
		etatCourant = new EtatSupprimer();
	}
	
	public void genererFeuilleDeRoute() {
		etatCourant.genererFeuilleDeRoute();
	}

}
