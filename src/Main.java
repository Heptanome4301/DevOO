import vue.GraphManager;
import vue.InterfaceGraphique;
import modele.*;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Plan p = new Plan();
		// p.chargerPlan();
		// System.out.println(p.calculerChemin(p.getAdresse(0), p.getAdresse(p.getAdresses().size()-1)));
		//System.out.println("V�rification : tps de 0 � 1 = " + p.getAdresses().get(0).getTroncons().get(0).getDuree());
		
		GraphManager.getInstance().create();
		GraphManager.getInstance().addNode("A", 0, 0);
		GraphManager.getInstance().addNode("B", 0, 3);
		GraphManager.getInstance().addNode("C", 3, 0);
		GraphManager.getInstance().addEdge("AB", "A", "B");
		GraphManager.getInstance().addEdge("AC", "A", "C");
		GraphManager.getInstance().addEdge("BC", "B", "C");
		InterfaceGraphique window = new InterfaceGraphique();
		window.linkView(GraphManager.getInstance().getView());
		window.frame.setVisible(true);
	}

}
