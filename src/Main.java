import modele.*;

public class Main {

	
	public static void print_tournee(Tournee t){
		String res = "";
		for(Chemin chemin : t.getItineraire())
		{
			res = "("+chemin+") "+res;
	
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
		System.out.println(p.calculerChemin(p.getAdresse(0), p.getAdresse(p.getAdresses().size()-1)));
		//System.out.println("V�rification : tps de 0 � 1 = " + p.getAdresses().get(0).getTroncons().get(0).getDuree());
		Tournee t = p.chargerLivraison();
		t.calculerTournee();
		print_tournee(t);
	}

}
