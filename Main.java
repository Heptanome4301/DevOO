import modele.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Plan p = new Plan();
		p.chargerPlan();
		System.out.println(p.calculerChemin(p.getAdresses().get(0), p.getAdresses().get(p.getAdresses().size()-1)));
		//System.out.println("Vérification : tps de 0 à 1 = " + p.getAdresses().get(0).getTroncons().get(0).getDuree());
	}

}
