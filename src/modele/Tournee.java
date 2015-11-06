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
	private ArrayList<Chemin> chemins;
	private Collection<Livraison> livraisons;
	private Adresse entrepot;
	private int Duree;
	private Collection<FenetreLivraison> fenetresLivraison;
	
	/**
	 * Une tournée contient la liste des livraisons non ordonnée.
	 * Elle stocke également l'itinéraire de la tournée (une liste de chemins)
	 * L'instance de tournée est créée par la classe Plan, au chargement du
	 * fichier des livraisons.
	 * La tournée est accessible par le contrôleur  
	 */
	public Tournee(Plan plan,Collection<Livraison> livraisons,Collection<FenetreLivraison> fenetresLivraison,Adresse entrepot){
		this.plan = plan;
		this.livraisons = livraisons;
		this.entrepot = entrepot;
		this.chemins = null;
		this.Duree = 0;
		this.fenetresLivraison = /* sort */fenetresLivraison;
	}
	
	/**
	 * Acces à l'itinéraire calculé
	 * 
	 * @return null si un appel à calculerTournee n'est pas fait auparavant
	 */
	public Collection<Chemin> getItineraire(){
		return chemins;
	} 
	
	/**
	 *  Génère les suites des instructions
	 *  que le livreur doit suivre pour effectuer sa tournée 
	 * @throws Exception si échec de création du fichier de sortie
	 * (soit le path n'est pas valide, ou que le calcul de la tournée n'est pas fait)
	 * @param FichierDeSortie 
	 * si un appel à calculerTournee n'est pas fait auparavant
	 * le fichier ne sera pas créé et une exception est levée
	 */
	public void feuilleDeRoute(String FichierDeSortie) throws Exception{}
	
	/**
	 * Applée par this.calculerTournee
	 *  Construction du graphe qui represente le cout des chemins 
	 *  entre chaque couple de livraison à effectuer
	 *  Pour se faire des appels à plan.calculerChemin sont effectués
	 *  Ce graphe sera passé comme parametre à la classe TSP
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
	
	private Chemin getCheminFromArrivee(Adresse arrivee) {
		if(arrivee!=null){
			for(Chemin chemin : chemins) {
				if(chemin.getArrivee().equals(arrivee)) {
					return chemin;
				}
			}
		}
		return null;
	}
	
	private Chemin getCheminFromDepart(Adresse depart) {
		if(depart!=null){
			for(Chemin chemin : chemins) {
				if(chemin.getDepart().equals(depart)) {
					return chemin;
				}
			}
		}
		return null;
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
	
	private Chemin[][] construireAllChemin(FenetreLivraison fenetreLivraison,Adresse adrssDepart){
		Livraison premiere =getLivraison(adrssDepart);
		if(premiere == null){
			System.err.println(adrssDepart.equals(entrepot));
			System.err.println("Livraison associer a l'adresse "+adrssDepart.getId()+" de la fenetre"+fenetreLivraison.getHeureDebut()+" est null");
			premiere =new Livraison(-1, adrssDepart, fenetreLivraison);
		}
		Set<Livraison> livraisons = getLivraison(fenetreLivraison);
		if(livraisons.contains(premiere)) livraisons.remove(premiere);
		
		int nb_sommet = livraisons.size();nb_sommet++; 
		Chemin[][] chemins = new Chemin[nb_sommet][nb_sommet];
		
		int i=1,j=1;
		for(Livraison l : livraisons)
		{	
			j = 1;
			for(Livraison ll : livraisons)
			{
				if (l==ll){
					chemins[i][j] = null;
				}else{
					chemins[i][j] = plan.calculerChemin(l.getAdresse(),ll.getAdresse() );
				}
				j++;	
			}
			i++;
		}
		chemins[0][0] = null;
		j = 1;
		for(Livraison ll:livraisons){
			chemins[0][j] = plan.calculerChemin(premiere.getAdresse(),ll.getAdresse() );
			chemins[j][0] = plan.calculerChemin(ll.getAdresse(),premiere.getAdresse() );
			j++;
		}
		return chemins;
	}
	
	public void calculerTournee(){
		
		this.chemins = new ArrayList<Chemin>();
		
		for(FenetreLivraison fl : fenetresLivraison){
			Adresse debutTournee = entrepot;
			if( ! chemins.isEmpty()){//donc c'est pas la premiere fenetre
				debutTournee = ((ArrayList<Chemin>)chemins).get(chemins.size()-1).getArrivee();
			}
			ArrayList<Chemin> tmp = calculerTourneeFenetre(debutTournee,fl);
			
			chemins.addAll(tmp);

		}
		
		chemins.add(plan.calculerChemin(((ArrayList<Chemin>)chemins).get(chemins.size()-1).getArrivee(),this.entrepot));
		
	}
	
	/** pour une fentre donneé
	 * Calcule la tournée qui passera par toutes les livraisons
	 * Cette methode fait appel entre autre à construireGraphe()
	 * puis TSP.chercherSolution
	 * Le resultat est obtenue en faisant TSP.getSolution() 
	 * ce resultat est "convertie" en chemin: faire appel à
	 * plan.calculerChemin()
	 * Enfin il est stocké dans l'attribut chemins, et accessible
	 * avec getIteneraire()
	 * @return 
	 */
	
	public ArrayList<Chemin> calculerTourneeFenetre(Adresse addssDepart,FenetreLivraison fenetreLivraison){ 
		int tpsLimite = 3000;
		
		Chemin[][] AllCheminsGraphe = construireAllChemin(fenetreLivraison,addssDepart) ;
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
	 *  Acces à la liste des livraisons à effecteur
	 *  (pour les afficher dans vue par exemple)
	 * @return Collection de Livraison
	 */

	
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
 * Modification de la tournée en lui ajoutant une nouvelle livraison.
 * Si les deux livraisons sont égales, ajoute la livraison à ajouter à la fin de la tournée 
 * @param lAdd
 * @param lFollow
 */
	public void ajouterLivraison(Livraison lAdd,Livraison lFollow) {
		if(!lAdd.equals(lFollow)) {
			Adresse adresseAdd = lAdd.getAdresse();
			Adresse adresseFollow = lFollow.getAdresse();
			Chemin cheminToRemove = getCheminFromArrivee(adresseFollow);
			Chemin chemin1 = plan.calculerChemin(cheminToRemove.getDepart(),adresseAdd);
			Chemin chemin2 = plan.calculerChemin(adresseAdd, adresseFollow);
			for(int i=0;i<chemins.size();i++) {
				if((chemins.get(i)).equals(cheminToRemove)) {
					chemins.add(i, chemin1);
					chemins.add(i+1, chemin2);
				}
			}
			chemins.remove(cheminToRemove);
			livraisons.add(lAdd);
			//TODO calculer nouvelles dates
		}
		else {
			Adresse lastAdresse = chemins.get(chemins.size()).getArrivee();
			Chemin chemin = plan.calculerChemin(lastAdresse, lAdd.getAdresse());
			chemins.add(chemins.size(),chemin);
			livraisons.add(lAdd);
			//TODO nouvelles dates
		}
				
	}
	
	/**
	 * Modification de la tournée en lui retirant une Livraison
	 *  
	 */
	public void supprimerLivraison(Livraison L)  {
		this.notifyObservers(this);
		if(livraisons.contains(L)) {
			Chemin chemin1 = getCheminFromArrivee(L.getAdresse());
			Chemin chemin2 = getCheminFromDepart(L.getAdresse());
			if(chemin1 != null && chemin2 != null) {
				Chemin newChemin = plan.calculerChemin(chemin1.getDepart(), chemin2.getArrivee());
				for(int i=0;i<chemins.size();i++) {
					if((chemins.get(i)).equals(chemin2)) {
						chemins.add(i, newChemin);
					}
				}
				chemins.remove(chemin1);
				chemins.remove(chemin2);
				livraisons.remove(L);
				//TODO nouvelles dates
			}
			
		}
	}
	
	
	

/**
 * Echange l'ordre de passage de deux livraisons et racalcule les heures de passages
 * Ne fait rien si les deux livraisons sont les mêmes
 * @param l1
 * @param l2
 */
	public void echangerLivraison(Livraison l1,Livraison l2) {
		this.notifyObservers(this);
		if(livraisons.contains(l1) && livraisons.contains(l2) && !l1.equals(l2)) {
			Chemin cheminRm1A = getCheminFromArrivee(l1.getAdresse());
			Chemin cheminRm1D = getCheminFromDepart(l1.getAdresse());
			Chemin cheminRm2A = getCheminFromArrivee(l2.getAdresse());
			Chemin cheminRm2D = getCheminFromDepart(l2.getAdresse());
			
			Chemin cheminAdd1A = plan.calculerChemin(cheminRm1A.getDepart(),l2.getAdresse());
			Chemin cheminAdd1D = plan.calculerChemin(l2.getAdresse(),cheminRm1D.getDepart());
			Chemin cheminAdd2A = plan.calculerChemin(cheminRm2A.getDepart(),l1.getAdresse());
			Chemin cheminAdd2D = plan.calculerChemin(l1.getAdresse(),cheminRm2D.getDepart());
			
			for(int i=0;i<chemins.size();i++) {
				if(chemins.get(i).equals(cheminRm1A)) {
					chemins.add(i, cheminAdd1A);
					chemins.add(i+1, cheminAdd1D);
				}
				if(chemins.get(i).equals(cheminRm2A)) {
					chemins.add(i, cheminAdd2A);
					chemins.add(i+1, cheminAdd2D);
				}
			}
			chemins.remove(cheminRm1A);
			chemins.remove(cheminRm1D);
			chemins.remove(cheminRm2A);
			chemins.remove(cheminRm2D);
			//TODO nouvelles dates
		}
		
		
		
	}
	
	
	public int getDuree() {
		return Duree;
	}

	public Collection<FenetreLivraison> getFenetresLivraison() {
		return fenetresLivraison;
	}


	
	public FenetreLivraison getFenetreLivraisonIndx(int index) {
		return ((ArrayList<FenetreLivraison>)fenetresLivraison).get(index);

	}
	
	public Livraison getLivraison(Adresse a){
		for(Livraison l:livraisons){
			if(l.getAdresse().getId() == a.getId() ){
				return l;
			}
		}
		return null;
	}
	
	

	
}
