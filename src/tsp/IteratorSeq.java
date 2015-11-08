package tsp;
import java.util.Collection;
import java.util.Iterator;

public class IteratorSeq implements Iterator<Integer> {

	protected Integer[] candidats;
	protected int nbCandidats;

	/**
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus qui sont successeurs de sommetCrt dans le graphe g,
	 * dans l'odre d'apparition dans <code>nonVus</code>
	 * @param nonVus
	 * @param sommetCrt
	 * @param g
	 */
	public IteratorSeq(Collection<Integer> nonVus, int sommetCrt, Graphe g){
		this.candidats = new Integer[nonVus.size()];
		Iterator<Integer> it = nonVus.iterator();
		while (it.hasNext()){
			Integer s = it.next();
			if (g.estArc(sommetCrt, s))
				candidats[nbCandidats++] = s;
		}
	}
	
	@Override
	public boolean hasNext() {
		return nbCandidats > 0;
	}

	@Override
	public Integer next() {
		nbCandidats--;
		return candidats[nbCandidats];
	}

	@Override
	public void remove() {}
}
