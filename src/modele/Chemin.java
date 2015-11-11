package modele;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Cette classe repr�sente le chemin � emprunter pour se rendre de l'Adresse depart � l'adresse Arrivee. Les chemins
 * sont construits � l'aide d'une impl�mentation de l'algorithme de Dijkstra et sont donc les plus courts chemins 
 * reliant les deux Adresses cit�s pr�c�demment.
 */
public class Chemin {
	
        /**
         * La liste des tron�ons � emprunter.
         */
	private Collection<Troncon> troncons;
        /**
         * La dur�e n�cessaire pour se rendre du d�part � l'arriv�e.
         */
	private int duree;
        /**
         * Les adresses de d�part et d'arriv�e du chemin.
         */
	private Adresse depart,arrivee;
	
        /**
         * Constructeur de la classe chemin. La liste de tron�ons sera calcul�e plus tard.
         * @param depart l'Adresse de d�part.
         * @param arrivee l'Adresse d'arriv�e.
         */
	public Chemin(Adresse depart, Adresse arrivee){
		troncons = new ArrayList<Troncon>();
		this.depart = depart;
		this.arrivee = arrivee;
	}
	
	/**
	 * Ajouter un tron�on � la liste.
	 * @param t le tron�on � ajouter.
	 */
	public void ajouterTroncon(Troncon t){
		troncons.add(t);
		duree = duree+t.getDuree();
	}
	
        /**
         * Surcharge de la m�thode d'affichage.
         * @return la cha�ne de carat�re d�crivant le chemin.
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
         * @return la dur�e n�cessaire � emprunter le chemin.
         */
	public int getDuree(){
		return duree;
	}
	
        /**
         * Accesseur de l'attribut tron�ons (la liste de tron�ons composant le chemin).
         * @return la liste de tron�ons.
         */
	public Collection<Troncon> getTroncons() {
		return troncons;
	}

        /**
         * Accesseur de l'attribut d�part.
         * @return l'Adresse e d�part du chemin.
         */
	public Adresse getDepart() {
		return depart;
	}

        /**
         * L'accesseur de l'attribut arriv�e.
         * @return l'Adresse d'arriv�e du chemin.
         */
	public Adresse getArrivee() {
		return arrivee;
	}
        
        /**
         * M�thoe permettant de d�terminer si le chemin contient un tron�on pass� en param�tre.
         * @param t le tron�on en question.
         * @return true s'il est contenu, false sinon.
         */
	public boolean contient(Troncon t) {
		return troncons.contains(t);
	}

        /**
         * M�thode n�cessaire � la mise en place du design pattern Visiteur.
         * @param visiteur le visiteur.
         */
	public void accept(VisiteurPlan visiteur) {
		for(Troncon t:troncons){
			visiteur.visite(t, true);
		}
		
	}
}
