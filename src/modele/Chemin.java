package modele;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Cette classe représente le chemin à emprunter pour se rendre de l'Adresse depart à l'adresse Arrivee. Les chemins
 * sont construits à l'aide d'une implémentation de l'algorithme de Dijkstra et sont donc les plus courts chemins 
 * reliant les deux Adresses cités précédemment.
 */
public class Chemin {
	
        /**
         * La liste des tronçons à emprunter.
         */
	private Collection<Troncon> troncons;
        /**
         * La durée nécessaire pour se rendre du départ à l'arrivée.
         */
	private int duree;
        /**
         * Les adresses de départ et d'arrivée du chemin.
         */
	private Adresse depart,arrivee;
	
        /**
         * Constructeur de la classe chemin. La liste de tronçons sera calculée plus tard.
         * @param depart l'Adresse de départ.
         * @param arrivee l'Adresse d'arrivée.
         */
	public Chemin(Adresse depart, Adresse arrivee){
		troncons = new ArrayList<Troncon>();
		this.depart = depart;
		this.arrivee = arrivee;
	}
	
	/**
	 * Ajouter un tronçon à la liste.
	 * @param t le tronçon à ajouter.
	 */
	public void ajouterTroncon(Troncon t){
		troncons.add(t);
		duree = duree+t.getDuree();
	}
	
        /**
         * Surcharge de la méthode d'affichage.
         * @return la chaîne de caratère décrivant le chemin.
         */
	public String toString(){
		String res = new String();
		
		for(Troncon t : troncons){
			//res+=t.getNomRue() + " ";
			res = t.getNomRue() + " " + res;
		}
		
		return res;
	}
	
        /**
         * L'accesseur de l'attribut duree.
         * @return la durée nécessaire à emprunter le chemin.
         */
	public int getDuree(){
		return duree;
	}
	
        /**
         * Accesseur de l'attribut tronçons (la liste de tronçons composant le chemin).
         * @return la liste de tronçons.
         */
	public Collection<Troncon> getTroncons() {
		return troncons;
	}

        /**
         * Accesseur de l'attribut départ.
         * @return l'Adresse e départ du chemin.
         */
	public Adresse getDepart() {
		return depart;
	}

        /**
         * L'accesseur de l'attribut arrivée.
         * @return l'Adresse d'arrivée du chemin.
         */
	public Adresse getArrivee() {
		return arrivee;
	}
        
        /**
         * Méthoe permettant de déterminer si le chemin contient un tronçon passé en paramètre.
         * @param t le tronçon en question.
         * @return true s'il est contenu, false sinon.
         */
	public boolean contient(Troncon t) {
		return troncons.contains(t);
	}

        /**
         * Méthode nécessaire à la mise en place du design pattern Visiteur.
         * @param visiteur le visiteur.
         */
	public void accept(Visiteur visiteur) {
		for(Troncon t:troncons){
			visiteur.visite(t, true);
		}
		
	}
}
