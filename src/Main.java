import vue.GraphManager;
import vue.InterfaceGraphique;
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
				
		GraphManager.getInstance().create();
		GraphManager.getInstance().addNode("A", 0, 0);
		GraphManager.getInstance().addNode("B", 0, 3);
		GraphManager.getInstance().addNode("C", 3, 0);
		GraphManager.getInstance().addEdge("AB", "A", "B");
		GraphManager.getInstance().addEdge("AC", "A", "C");
		GraphManager.getInstance().addEdge("BC", "B", "C");
		InterfaceGraphique window = new InterfaceGraphique();
		window.linkView(GraphManager.getInstance().getView());
		window.display();
		p.chargerPlan();

        System.out.println("Chemin de id=3 à id=1 (" + p.calculerChemin(p.getAdresse(3), p.getAdresse(1)) + ")");
        //System.out.println(p.calculerChemin(p.getAdresse(0), p.getAdresse(p.getAdresses().size()-1)));
		//System.out.println("Verification : tps de 0 â€¡ 1 = " + p.getAdresses().get(0).getTroncons().get0).getDuree());
		Tournee t = p.chargerLivraison();
		t.calculerTournee();
		print_tournee(t);
	}

}
