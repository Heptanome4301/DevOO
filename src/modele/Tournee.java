package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import tsp.Graphe;
import tsp.TSP;
import tsp.TSP1;
import tsp.GrapheComplet;

public class Tournee extends Observable{
	private Plan plan;
	private Collection<Chemin> chemins;
	private Collection<Livraison> livraisons;
	private Adresse entrepot;
	private int Duree;
	private Collection<FenetreLivraison> fenetresLivraison;
	
	/**
	 * Une tourn�e contient la liste des livraisons.
	 * Elle sotck �galement l'itin�raire de la tourn�e (une liste de chemin)
	 * L'instance de tourn�e est cr�e par la classe plan, au chargement du
	 * fichier des livraison.
	 * La tourn�e est accessible par le contr�leur  
	 */
	public Tournee(Plan plan,Collection<Livraison> livraisons,Collection<FenetreLivraison> fenetresLivraison,Adresse entrepot){
		this.plan = plan;
		this.livraisons = livraisons;
		this.entrepot = entrepot;
		this.chemins = null;
		this.Duree = 0;
		this.fenetresLivraison = fenetresLivraison;
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
	private Graphe construireGraphe(Chemin[][] cheminsDuGraphe){
		int nb_sommet = cheminsDuGraphe.length;
		//double [][] res = new double[nb_sommet][nb_sommet];
		int [][] res = new int[nb_sommet][nb_sommet];
		for(int i=0;i<nb_sommet;i++){
			for(int j=0;j<nb_sommet;j++){
				if(cheminsDuGraphe[i][j]!=null)
					res[i][j] = new Double(cheminsDuGraphe[i][j].getDuree()).intValue();
				else 
					res[i][j] = -1;
			}
		}
		
		return new GrapheComplet(res);
	}
	
	private Set<Livraison> getLivraison(FenetreLivraison fenetreLivraison){
		Set<Livraison> livraisonF = new HashSet<Livraison>();
		for(Livraison l: this.livraisons)
		{
			if((l.getFenetreLivraison()).equals(fenetreLivraison)){
				livraisonF.add(l);
			}
		}
		return livraisonF;
	}
	
	private Chemin[][] construireAllChemin(FenetreLivraison fenetreLivraison){
		Set<Livraison> livraisons = getLivraison(fenetreLivraison);
		int nb_sommet = livraisons.size();
		Chemin[][] chemins = new Chemin[nb_sommet][nb_sommet];
		int i=0,j=0;
		for(Livraison l : livraisons)
		{	
			j = 0;
			for(Livraison ll : livraisons)
			{
				if (l==ll){
					chemins[i][j] = null;
				}else{
					chemins[i][j] = plan.calculerChemin(ll.getAdresse(),l.getAdresse() );
				}
				j++;	
			}
			i++;
		}
		return chemins;
	}
	
	public void calculerTournee(){
		
		this.chemins = new ArrayList<Chemin>();
		
		for(FenetreLivraison fl : fenetresLivraison){
			ArrayList<Chemin> tmp = calculerTourneeFenetre(fl);
			//if(chemins.size()>0){
				//chemins.add(plan.calculerChemin(tmp.get(0).getDepart(),((ArrayList<Chemin>)chemins).get(0).getArrivee()));
			//}
			for(Chemin ch : tmp){
				chemins.add(ch);
			}
		}
		
		//chemins.add(plan.calculerChemin(((ArrayList<Chemin>)chemins).get(0).getArrivee(),this.entrepot));
		//chemins.add(plan.calculerChemin(this.entrepot,((ArrayList<Chemin>)chemins).get(chemins.size()-1).getArrivee()));
		
	}
	
	/** pour une fentre donne�
	 * Calcule la tourn�e qui passera par toutes les livraisons
	 * Cette methode fait appel entre autre � construireGraphe()
	 * puis TSP.chercherSolution
	 * Le resultat est obtenue en faisant TSP.getSolution() 
	 * ce resultat est "convertie" en chemin: faire appel �
	 * plan.calculerChemin()
	 * Enfin il est stock� dans l'attribut chemins, et accessible
	 * avec getIteneraire()
	 * @return 
	 */
	
	public ArrayList<Chemin> calculerTourneeFenetre(FenetreLivraison fenetreLivraison){ 
		int tpsLimite = 3000;
		
		Chemin[][] AllCheminsGraphe = construireAllChemin(fenetreLivraison) ;
		Graphe graphe = construireGraphe(AllCheminsGraphe);
		
		TSP tsp = new TSP1();
		tsp.chercheSolution(tpsLimite, graphe);
		
		this.Duree +=tsp.getCoutSolution();
		ArrayList<Chemin> res = new ArrayList<Chemin>();
		
		int i,j,I,J;
		for(j=1;j<graphe.getNbSommets();j++)
		{
			i = j-1;
			I = tsp.getSolution(i);
			J = tsp.getSolution(j);
			//this.chemins.add(AllCheminsGraphe[I][J]);
			res.add(AllCheminsGraphe[I][J]);
		}
		return res;
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
		this.notifyObservers(this);
	}
	
	/**
	 * Modification de la tourn�e en lui retirant une Livraison
	 * @throws Exception si la livraison n'existe pas 
	 */
    //  ?? est ce que on doit recalculer automatiquement la tourn�e ??
	public void supprimerLivraison(Livraison L) throws Exception {
		this.notifyObservers(this);
	}
	
	
	/**
	 * Modification de la tourn�e ...
	 * @throws Exception l1 == l2 ou pas contenu dans la liste des livraison 	
	 */
	public void echangerLivraison(Livraison l1,Livraison l2)throws Exception {
		this.notifyObservers(this);
	}
	
	
	public int getDuree() {
		return Duree;
	}

	public Collection<FenetreLivraison> getFenetresLivraison() {
		return fenetresLivraison;
	}

	public FenetreLivraison getFenetreLivraison(int id) {
		for (FenetreLivraison fl : fenetresLivraison) {
			if (fl.getId() == id)
				return fl;
		}
		return null;
	}
	

	
}
