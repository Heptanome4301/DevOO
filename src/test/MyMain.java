package test;

import controleur.Controleur;
import modele.Chemin;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;

public class MyMain {
	
	
	private static void afficherLatournerCalculer(Tournee t){
		for(Chemin ch : t.getItineraire()){
			System.out.println("Chemin["+ch+"]  "+ch.getDuree()/60+" mn ");
		}
		for(Chemin ch : t.getItineraire()){
			String res = "";
			Livraison l= t.getLivraison(ch.getArrivee());
			if(l==null) res += "Entrepot";
			else res += l;
			System.out.println(res);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Controleur c = new Controleur(new Plan());
		c.chargerPlan();
		c.chargerLivraisons();
		c.calculerTournee();
		Tournee t = c.getTournee();
		afficherLatournerCalculer(t);
	}

}
