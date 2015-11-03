import modele.*;

public class Main {

	
	public static void print_tournee(Tournee t){
		String res = "";
		for(Chemin chemin : t.getItineraire())
		{
			res += "("+chemin+")";
	
		}
		System.out.println(res);
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Plan p = new Plan();
		p.chargerPlan();

		System.out.println("Chemin de id=3 à id=1 (" + p.calculerChemin(p.getAdresse(3), p.getAdresse(1)) + ")");
		//System.out.println(p.calculerChemin(p.getAdresse(0), p.getAdresse(p.getAdresses().size()-1)));
		//System.out.println("Vérification : tps de 0 à 1 = " + p.getAdresses().get(0).getTroncons().get0).getDuree());
		Tournee t = p.chargerLivraison();
		t.calculerTournee();
		print_tournee(t);
	}

}
