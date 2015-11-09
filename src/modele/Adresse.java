package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

/**
 * Cette classe représente un croisement de rue dans une ville, et une potentielle adresse de livraison
 */
public class Adresse extends Observable {
        
        /**
         * L'identifiant de l'adresse.
         */
	private int id;
        /**
         * Sa coordonnée suivant l'axe x. Utile pour la représentation graphique de cette adresse dans un plan.
         */
	private int x;
        /**
         * Sa coordonnée suivant l'axe y. Utile pour la représentation graphique de cette adresse dans un plan.
         */
	private int y;
        /**
         * La livraison associée à cette adresse, null si aucune livraison n'est prévue.
         */
	private Livraison livraison;
        /**
         * La liste des rues empruntables depuis cette adresse (représentées par des instances de la classe Troncon).
         */
	private Collection<Troncon> tronconsSortants;
	
        
        /**
         * Constructeur de la classe Adresse.
         * @param id
         * @param x
         * @param y
         */
	public Adresse(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		tronconsSortants = new ArrayList<Troncon>();
		livraison = null;
	}
	
	/**
         * Surcharge de la méthode d'afficahge.
         * @return une chaîne de caractère décrivant l'adresse.
         */
	@Override
	public String toString() {
		String res = "Adresse " + id + "\r\n"
				+ "x=" + x + ", y=" + y;
		if(estAssocierAvecLivraison() ){
			res += "\n"+livraison;
		}
		return res;
	}
        
        /**
         * Surcharge du calcul de l'index de hachage afin de pouvoir utiliser un HashSet.
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
         * Surcharge de la méthode d'égalité.
         * @param obj la deuxième adresse à laquelle comparer la première.
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
		if (id != other.id)
			return false;
		return true;
	}
        
        /**
         * Ajouter un tronçpn à la liste des tronçons.
         * @param t le tronçon à ajouter.
         */
	public void ajouterTroncon(Troncon t) {
		tronconsSortants.add(t);
		notifyObservers(t);
	}

        /**
         * Retirer un tronçon de la liste des tronçons.
         * @param t le tronçon à retirer.
         */
	protected void retirerTroncon(Troncon t) {
		tronconsSortants.remove(t);
		notifyObservers(t);
	}
        
        /**
         * Accesseur de l'attribut id.
         * @return l'id de l'adresse.
         */
	public int getId() {
		return id;
	}

        /**
         * Accesseur de l'attribut x.
         * @return x.
         */
	public int getX() {
		return x;
	}
        
        /**
         * Accesseur de l'attribut y.
         * @return y.
         */
	public int getY() {
		return y;
	}

        /**
         * Accesseur de l'attribut troncons.
         * @return la liste de tronçons sortants de l'adresse.
         */
	public Collection<Troncon> getTroncons() {
		return tronconsSortants;
	}

        /**
         * Méthode nécessaire à la mise en place du pattern visiteur.
         * @param visiteur le visiteur.
         * @param estEntrepot
         */
	public void accept(Visiteur visiteur,boolean estEntrepot) {
		for (Troncon t : getTroncons()) {
			t.accept(visiteur);
		}
		visiteur.visite(this, estEntrepot);
		
	}
        
        /**
         * Mutateur de l'attribut livraison.
         * @param l la livraison à cette adresse.
         */
        protected void setLivraison(Livraison l){
                this.livraison = l;
        }
        
        /**
         * Accesseur de l'attribut livraison.
         * @return la livraison prévue à cette adresse, null sinon.
         */
        public Livraison getLivraison(){
                return livraison;
        }
        
        /**
         * Renvoie un booléen traduisant le fait qu'une livraison soit prévue à cette adresse.
         * @return true si une livraison est prévue, false sinon.
         */
        public boolean estAssocierAvecLivraison(){
                return livraison != null;
        }
    
}
