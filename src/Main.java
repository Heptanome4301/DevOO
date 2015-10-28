import modele.*;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Plan p = new Plan();
		p.chargerPlan();
		System.out.println(p.calculerChemin(p.getAdresse(0), p.getAdresse(p.getAdresses().size()-1)));
		//System.out.println("V�rification : tps de 0 � 1 = " + p.getAdresses().get(0).getTroncons().get(0).getDuree());
	}

}
