package modele;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Cette classe represente un croisement de rue dans une ville, et une
 * potentielle adresse de livraison
 */
public class Adresse {

	/**
	 * L'identifiant de l'adresse.
	 */
	private int id;
	/**
	 * Sa coordonnee suivant l'axe Est/Ouest. Utile pour la representation
	 * graphique de cette adresse dans un plan.
	 */
	private int x;
	/**
	 * Sa coordonnee suivant l'axe Nord/Sud. Utile pour la representation
	 * graphique de cette adresse dans un plan.
	 */
	private int y;
	/**
	 * La livraison associee a cette adresse, null si aucune livraison n'est
	 * prevue.
	 */
	private Livraison livraison;
	/**
	 * La liste des rues empruntables depuis cette adresse (representees par des
	 * instances de la classe Troncon).
	 */
	private Collection<Troncon> tronconsSortants;

	/**
	 * Constructeur de la classe Adresse.
	 * 
	 * @param id L'identifiant associé à cette adresse
	 * @param x La coordonée en abscisse de cette adresse
	 * @param y La coordonnée en ordonnée de cette adresse
	 */
	public Adresse(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		tronconsSortants = new ArrayList<>();
		livraison = null;
	}

	/**
	 * Surcharge de la methode d'afficahge.
	 * 
	 * @return une chaine de caractere decrivant l'adresse.
	 */
	@Override
	public String toString() {
		String res = "Adresse " + id + "\r\n" + "x=" + x + ", y=" + y;
		if (estAssocierAvecLivraison()) {
			res += "\n" + livraison.infos();
		}
		return res;
	}

	/**
	 * Surcharge du calcul de l'index de hachage afin de pouvoir utiliser un
	 * HashSet.
	 * 
	 * @return l'index de hachage.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/**
	 * Surcharge de la methode d'egalite.
	 * 
	 * @param obj
	 *            la deuxieme adresse a laquelle comparer la premiere.
	 * @return true si les id sont identiques, false sinon.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adresse other = (Adresse) obj;
		return id == other.id;
	}

	/**
	 * Ajouter un troncon a la liste des troncons.
	 * 
	 * @param t
	 *            le troncon a ajouter.
	 */
	public void ajouterTroncon(Troncon t) {
		tronconsSortants.add(t);
	}

	/**
	 * Accesseur de l'attribut id.
	 * 
	 * @return l'id de l'adresse.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Accesseur de l'attribut x.
	 * 
	 * @return x.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Accesseur de l'attribut y.
	 * 
	 * @return y.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Accesseur de l'attribut troncons.
	 * 
	 * @return la liste de troncons sortants de l'adresse.
	 */
	public Collection<Troncon> getTroncons() {
		return tronconsSortants;
	}

	/**
	 * Mutateur de l'attribut livraison.
	 * 
	 * @param l
	 *            la livraison a cette adresse.
	 */
	public void setLivraison(Livraison l) {
		this.livraison = l;
	}

	/**
	 * Accesseur de l'attribut livraison.
	 * 
	 * @return la livraison prevue a cette adresse, null sinon.
	 */
	public Livraison getLivraison() {
		return livraison;
	}

	/**
	 * Renvoie un booleen traduisant le fait qu'une livraison soit prevue a
	 * cette adresse.
	 * 
	 * @return true si une livraison est prevue, false sinon.
	 */
	public boolean estAssocierAvecLivraison() {
		return livraison != null;
	}
}
