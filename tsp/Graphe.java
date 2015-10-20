package tsp;
public interface Graphe {

	/**
	 * @return le nombre de sommets de <code>this</code>
	 */
	public abstract int getNbSommets();

	/**
	 * @param i 
	 * @param j 
	 * @return le cout de l'arc (i,j) si (i,j) est un arc ; -1 sinon
	 */
	public abstract int getCout(int i, int j);

	/**
	 * @param i 
	 * @param j 
	 * @return true si <code>(i,j)</code> est un arc de <code>this</code>
	 */
	public abstract boolean estArc(int i, int j);


}