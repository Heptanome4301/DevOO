package tsp;
import java.util.Collection;
import java.util.Iterator;

public class TSP2 extends TemplateTSP {

	public TSP2(){
		
	}
	
	@Override
	protected int bound(Integer sommetCourant, Collection<Integer> nonVus) {
		return getMaxCoutArc(sommetCourant,nonVus) * nonVus.size();
	}

	private int getMaxCoutArc(Integer sommetCourant,	Collection<Integer> nonVus) {
		int cout,max = -1;
		for(Integer i : nonVus){
			if(! g.estArc(sommetCourant, i)) continue;
			cout = g.getCout(sommetCourant, i);
			if(cout>max) max = cout; 
		}
		return max;
	}

	@Override
	protected Iterator<Integer> iterator(Integer sommetCrt, Collection<Integer> nonVus, Graphe g) {
		return new IteratorCroissant(nonVus, sommetCrt, g);
	}

}
