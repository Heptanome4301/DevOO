package tsp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class TemplateTSP implements TSP {
	
	private Integer[] meilleureSolution;
	protected Graphe g;
	private int coutMeilleureSolution;
	private int tpsLimite;
	private long tpsDebut;
	
	public void chercheSolution(int tpsLimite, Graphe g){
		if (tpsLimite <= 0) return;
		tpsDebut = System.currentTimeMillis();	
		this.tpsLimite = tpsLimite;
		this.g = g;
		meilleureSolution = new Integer[g.getNbSommets()];
		Collection<Integer> nonVus = new ArrayList<Integer>(g.getNbSommets()-1);
		for (int i=1; i<g.getNbSommets(); i++) nonVus.add(i);
		Collection<Integer> vus = new ArrayList<Integer>(g.getNbSommets());
		vus.add(0); // le premier sommet visite est 0
		coutMeilleureSolution = Integer.MAX_VALUE;
		branchAndBound(0, nonVus, vus, 0);
	}
	
	public Integer getSolution(int i){
		if (g != null && i>=0 && i<g.getNbSommets())
			return meilleureSolution[i];
		return -1;
	}
	
	public int getCoutSolution(){
		if (g != null)
			return coutMeilleureSolution;
		return -1;
	}
	
	/**
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * @param sommetCourant
	 * @param nonVus
	 * @return une borne inferieure du cout des chemins de <code>g</code> partant de <code>sommetCourant</code>, visitant 
	 * tous les sommets de <code>nonVus</code> exactement une fois, puis retournant sur le sommet <code>0</code>.
	 */
	protected abstract int bound(Integer sommetCourant, Collection<Integer> nonVus);
	
	/**
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * @param sommetCrt
	 * @param nonVus
	 * @param g
	 * @return un iterateur permettant d'iterer sur tous les sommets de <code>nonVus</code> qui sont successeurs de <code>sommetCourant</code>
	 */
	protected abstract Iterator<Integer> iterator(Integer sommetCrt, Collection<Integer> nonVus, Graphe g);
	
	/**
	 * Methode definissant le patron (template) d'une resolution par separation et evaluation (branch and bound) du TSP pour le graphe <code>g</code>.
	 * @param sommetCrt le dernier sommet visite
	 * @param nonVus la liste des sommets qui n'ont pas encore ete visites
	 * @param vus la liste des sommets deja visites (y compris sommetCrt)
	 * @param coutVus la somme des couts des arcs du chemin passant par tous les sommets de vus dans l'ordre ou ils ont ete visites
	 */	
	private void branchAndBound(int sommetCrt, Collection<Integer> nonVus, Collection<Integer> vus, int coutVus){
		if (System.currentTimeMillis() - tpsDebut > tpsLimite) return;
	    if (nonVus.size() == 0){ // tous les sommets ont ete visites
	    	if (g.estArc(sommetCrt,0)){ // on peut retourner au sommet de depart (0)
	    		if (coutVus+g.getCout(sommetCrt,0) < coutMeilleureSolution){ // on a trouve une solution meilleure que meilleureSolution
	    			vus.toArray(meilleureSolution);
	    			coutMeilleureSolution = coutVus+g.getCout(sommetCrt,0);
	    		}
	    	}
	    } else if (coutVus+bound(sommetCrt,nonVus) < coutMeilleureSolution){
	        Iterator<Integer> it = iterator(sommetCrt, nonVus, g);
	        while (it.hasNext()){
	        	Integer prochainSommet = it.next();
	        	vus.add(prochainSommet);
	            nonVus.remove(prochainSommet);
	            branchAndBound(prochainSommet, nonVus, vus, coutVus+g.getCout(sommetCrt, prochainSommet));
	            vus.remove(prochainSommet);
	            nonVus.add(prochainSommet);
	        }	    
	    }
	}
}

