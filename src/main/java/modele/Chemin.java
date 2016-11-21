package modele;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Cette classe represente le chemin a emprunter pour se rendre de l'Adresse depart a l'adresse Arrivee. Les chemins
 * sont construits a l'aide d'une implementation de l'algorithme de Dijkstra et sont donc les plus courts chemins
 * reliant les deux Adresses cites precedemment.
 */
public class Chemin {
	
        /**
         * La liste des troncons a emprunter.
         */
	private Collection<Troncon> troncons;
        /**
         * La duree necessaire pour se rendre du depart a l'arrivee.
         */
	private int duree;
        /**
         * Les adresses de depart et d'arrivee du chemin.
         */
	private Adresse depart,arrivee;
	
        /**
         * Constructeur de la classe chemin. La liste de troncons sera calculee plus tard.
         * @param depart l'Adresse de depart.
         * @param arrivee l'Adresse d'arrivee.
         */
	public Chemin(Adresse depart, Adresse arrivee){
		troncons = new ArrayList<>();
		this.depart = depart;
		this.arrivee = arrivee;
	}
	
	/**
	 * Ajouter un troncon a la liste.
	 * @param t le troncon a ajouter.
	 */
	public void ajouterTroncon(Troncon t){
		troncons.add(t);
		duree = duree+t.getDuree();
	}
	
        /**
         * Surcharge de la methode d'affichage.
         * @return la chaine de caratere decrivant le chemin.
         */
	public String toString(){
		String res = "";
		
		for(Troncon t : troncons){
			//res+=t.getNomRue() + " ";
			res = t.getNomRue() + " " + res;
		}
		
		return res;
	}
	
        /**
         * L'accesseur de l'attribut duree.
         * @return la duree necessaire a emprunter le chemin.
         */
	public int getDuree(){
		return duree;
	}
	
        /**
         * Accesseur de l'attribut troncons (la liste de troncons composant le chemin).
         * @return la liste de troncons.
         */
	public Collection<Troncon> getTroncons() {
		return troncons;
	}

        /**
         * Accesseur de l'attribut depart.
         * @return l'Adresse de depart du chemin.
         */
	public Adresse getDepart() {
		return depart;
	}

        /**
         * L'accesseur de l'attribut arrivee.
         * @return l'Adresse d'arrivee du chemin.
         */
	public Adresse getArrivee() {
		return arrivee;
	}
        
        /**
         * Methode permettant de determiner si le chemin contient un troncon passe en parametre.
         * @param t le troncon en question.
         * @return true s'il est contenu, false sinon.
         */
	public boolean contient(Troncon t) {
		return troncons.contains(t);
	}

}
