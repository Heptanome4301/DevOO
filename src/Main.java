
import java.io.File;
import xml.OuvreurDeFichiersXML;
import vue.Fenetre;
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
				
		Fenetre window = new Fenetre();
		window.display();
		// p.chargerPlan();
		window.linkView(p);

		File xml = OuvreurDeFichiersXML.getInstance().ouvre();
		p.chargerPlan(xml);

		System.out.println("Chemin de id=3 à id=1 (" + p.calculerChemin(p.getAdresse(3), p.getAdresse(1)) + ")");
		//System.out.println("Chemin de id=73 à id=13 (" + p.calculerChemin(p.getAdresse(73), p.getAdresse(13)) + ")");
		//System.out.println(p.calculerChemin(p.getAdresse(0), p.getAdresse(p.getAdresses().size()-1)));
		//System.out.println("Vérification : tps de 0 à 1 = " + p.getAdresses().get(0).getTroncons().get0).getDuree());
		xml = OuvreurDeFichiersXML.getInstance().ouvre();
		Tournee t = p.chargerLivraison(xml);

		t.calculerTournee();
		print_tournee(t);
	}

}
