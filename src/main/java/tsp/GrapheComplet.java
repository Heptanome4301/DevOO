package tsp;
public class GrapheComplet implements Graphe {
	
	private static final int COUT_MAX = 40;
	private static final int COUT_MIN = 10;
	int nbSommets;
	int[][] cout;
	
	public GrapheComplet(int[][]cout){
		this.nbSommets = cout.length;
		this.cout = cout;
	}
	
	/**
	 * Cree un graphe complet dont les aretes ont un cout compris entre COUT_MIN et COUT_MAX
	 * @param nbSommets
	 */
	public GrapheComplet(int nbSommets){
		this.nbSommets = nbSommets;
		int iseed = 1;
		cout = new int[nbSommets][nbSommets];
		for (int i=0; i<nbSommets; i++){
		    for (int j=0; j<nbSommets; j++){
		        if (i == j) cout[i][j] = -1;
		        else {
		            int it = 16807 * (iseed % 127773) - 2836 * (iseed / 127773);
		            if (it > 0)	iseed = it;
		            else iseed = 2147483647 + it;
		            cout[i][j] = COUT_MIN + iseed % (COUT_MAX-COUT_MIN+1);
		        }
		    }
		}
	}

	@Override
	public int getNbSommets() {
		return nbSommets;
	}

	@Override
	public int getCout(int i, int j) {
		if (i<0 || i>=nbSommets || j<0 || j>=nbSommets)
			return -1;
		return cout[i][j];
	}

	@Override
	public boolean estArc(int i, int j) {
		if (i<0 || i>=nbSommets || j<0 || j>=nbSommets)
			return false;
		return i != j;
	}

}
