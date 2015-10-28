package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;

public class Tournee extends Observable{
	private Plan plan;
	private Collection<Chemin> chemins;
	private Collection<Livraison> livraisons;
	private Adresse entrepot;
	
	/**
	 * Une tourn�e contient la liste des livraisons.
	 * Elle sotck �galement l'itin�raire de la tourn�e (une liste de chemin)
	 * L'instance de tourn�e est cr�e par la classe plan, au chargement du
	 * fichier des livraison.
	 * La tourn�e est accessible par le contr�leur  
	 */
	public Tournee(Plan plan,Collection<Livraison> livraisons,Adresse entrepot){
		this.plan = plan;
		this.livraisons = livraisons;
		this.entrepot = entrepot;
		this.chemins = null;
	}
	
	/**
	 * Acces � l'itin�raire calcul�
	 * 
	 * @return null si un appel � calculerTournee n'est pas fait auparavant
	 */
	public Collection<Chemin> getItineraire(){
		return chemins;
	} 
	
	/**
	 *  G�n�re les suites des instructions
	 *  que le livreur doit suivre pour effectuer sa tourn�e 
	 * @throws Exception si �chec de cr�ation du fichier de sortie
	 * (soit le path n'est pas valide, ou que le calcul de la tourn�e n'est pas fait)
	 * @param FichierDeSortie 
	 * si un appel � calculerTournee n'est pas fait auparavant
	 * le fichier ne sera pas cr�� et une exception est lev�e
	 */
	public void feuilleDeRoute(String FichierDeSortie) throws Exception{}
	
	/**
	 * Appl�e par this.calculerTournee
	 *  Construction du graphe qui represente le cout des chemins 
	 *  entre chaque couple de livraison � effectuer
	 *  Pour se faire des appels � plan.calculerChemin sont effectu�s
	 *  Ce graphe sera pass� comme parametre � la classe TSP
	 *  i,j : le cout du chemin entre l'adresse de id i et l'adresse  de l'id j
	 * @return un tableau de 2 dimensions representant les adresse
	 * de livraison 
	 */
	private int[][] construireGraphe(){
		return null;
	}
	
	/**
	 * Calcule la tourn�e qui passera par toutes les livraisons
	 * Cette methode fait appel entre autre � construireGraphe()
	 * puis TSP.chercherSolution
	 * Le resultat est obtenue en faisant TSP.getSolution() 
	 * ce resultat est "convertie" en chemin: faire appel �
	 * plan.calculerChemin()
	 * Enfin il est stock� dans l'attribut chemins, et accessible
	 * avec getIteneraire()
	 */
	public void calculerTournee(){ // TO DO
		//TSP.chercherSolution(
				construireGraphe() ;
		//);
		//TSP.getSolution() ;
		chemins = new ArrayList<Chemin>();
		chemins.add( plan.calculerChemin(null,null) ) ;
		
	}
	
	/**
	 *  Acces � la liste des livraisons � effecteur
	 *  (pour les afficher dans vue par exemple)
	 * @return Collection de Livraison
	 */
	public Collection<Livraison> getLivraison(){
		return livraisons;
	}
	
	public Collection<Livraison> getLivraisons() {
		return livraisons;
	}

	public void setLivraisons(Collection<Livraison> livraisons) {
		this.livraisons = livraisons;
	}

	public Plan getPlan() {
		return plan;
	}

	public Adresse getEntrepot() {
		return entrepot;
	}

	/**
	 * Modification de la tourn�e en lui ajoutant une
	 * nouvelle livraison.
	 * @param l la nouvelle livraison � ajouter
	 * @throws Exception si la livraison est d�ja existante
	 * 
	 * 
	 */
	//  ?? est ce que on doit recalculer automatiquement la tourn�e ??
	public void ajouterLivraison(Livraison l) throws Exception{
		
	}
	
	/**
	 * Modification de la tourn�e en lui retirant une Livraison
	 * @throws Exception si la livraison n'existe pas 
	 */
    //  ?? est ce que on doit recalculer automatiquement la tourn�e ??
	public void supprimerLivraison(Livraison L) throws Exception {}
	
	
	/**
	 * Modification de la tourn�e ...
	 * @throws Exception l1 == l2 ou pas contenu dans la liste des livraison 	
	 */
	public void echangerLivraison(Livraison l1,Livraison l2)throws Exception {}
	
	
	
	
}
