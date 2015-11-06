package modele;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import java.util.Stack;

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
	private ArrayList<FenetreLivraison> fenetresLivraison;
	
	/**
	 * Une tournée contient la liste des livraisons.
	 * Elle sotck également l'itinéraire de la tournée (une liste de chemin)
	 * L'instance de tournée est crée par la classe plan, au chargement du
	 * fichier des livraison.
	 * La tournée est accessible par le contrôleur  
	 */
	public Tournee(Plan plan,Collection<Livraison> livraisons,ArrayList<FenetreLivraison> fenetresLivraison,Adresse entrepot){
		this.plan = plan;
		this.livraisons = livraisons;
		this.entrepot = entrepot;
		this.chemins = null;
		this.Duree = 0;
		this.fenetresLivraison = /* sort */fenetresLivraison; // <-------
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
    public void feuilleDeRoute(String fichier) throws Exception{
        try
        {
            Stack<String> stack = new Stack<String>();
            
            PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (fichier)));
         
            Collection<Chemin> itineraire = this.getItineraire();
            int i = 1;
            pw.println("Votre tournee est la suivante : ");
            for(Chemin chemin : itineraire){
                pw.print("Livraison " + i++);
                pw.print(" Depart : ");
                pw.print(chemin.getDepart().getId());
                pw.print(" Arrivee : ");
                pw.print(chemin.getArrivee().getId());
                pw.println(" Itineraire : ");
                
                Collection<Troncon> troncons = chemin.getTroncons();
                for(Troncon t : troncons){
                    stack.push(t.getNomRue());
                    stack.push(Integer.toString(t.getArrivee().getId()));
                    stack.push(Integer.toString(t.getDepart().getId()));
                }
                
                while(stack.size() >= 3){
                    pw.print(" De : ");
                    pw.print(stack.pop());
                    pw.print(" A : ");
                    pw.print(stack.pop());
                    pw.print(" Par : ");
                    pw.println(stack.pop());
                }
                pw.println();
            }
            
         
            pw.close();
        }
        catch (IOException exception)
        {
            System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
        }
    }
	
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
		System.out.println("Cacul de la tourn�e...");
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
                System.out.println("Tournee calcul�e");
		
		calculerLesDurees( 0 ); 
	}
	
	// indice dans chemins du premier chemin à partir dulequel
	// il faut calculer/recalculer les durees
	private void calculerLesDurees(int indiceDepart){
		Date horaire = null ;
		Chemin PremierChemin = chemins.get(indiceDepart);
		if(PremierChemin.getDepart() .equals(entrepot)){
			horaire = fenetresLivraison.get(0).getHeureDebut();
		} else {
			horaire = getLivraison(PremierChemin.getDepart()).getHoraire();
		}
		
		
		for(int i=indiceDepart; i < chemins.size()-1 ; i++ ){
			Chemin chemin = chemins.get(i);
			Livraison l = getLivraison(chemin.getArrivee());

			horaire =  addSecondsHoraire(horaire,chemin.getDuree());
 
	        if (horaire.before(l.getFenetreLivraison().getHeureDebut()))  {
	        	horaire = l.getFenetreLivraison().getHeureDebut();
	        }
	        	
			l.setHoraire(horaire); 
			
			l.positionnerRetard() ; // positionner le retard s'il existe
		}
		
		this.Duree =  (int) (addSecondsHoraire(horaire , chemins.get(chemins.size()-1).getDuree()).getTime()/ 1000 - fenetresLivraison.get(0).getHeureDebut().getTime()/1000);
		System.out.println("verif "+ addSecondsHoraire(fenetresLivraison.get(0).getHeureDebut() , Duree)) ;
		System.out.println("duree retour à la base (min) "+ chemins.get(chemins.size()-1).getDuree()/60);
		
	}
	
	
	private Date addSecondsHoraire(Date horaire,int seconds ){
		Calendar cal = Calendar.getInstance();
        cal.setTime(horaire);
        cal.add(Calendar.SECOND, seconds);
        return cal.getTime();
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
		
		//this.Duree +=tsp.getCoutSolution();
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
	 * Modification de la tournée en lui ajoutant une
	 * nouvelle livraison.
	 * @param l la nouvelle livraison à ajouter
	 * @throws Exception si la livraison est déja existante
	 * 
	 * 
	 */
	//  ?? est ce que on doit recalculer automatiquement la tournée ??
	public void ajouterLivraison(Livraison lAdd,Livraison lFollow) throws Exception{
		//this.notifyObservers(this);
		//Adresse 
		
	}
	
	/**
	 * Modification de la tournée en lui retirant une Livraison
	 * @throws Exception si la livraison n'existe pas 
	 */
    //  ?? est ce que on doit recalculer automatiquement la tournée ??
	public void supprimerLivraison(Livraison L) throws Exception {
		this.notifyObservers(this);
	}
	
	
	/**
	 * Modification de la tournée ...
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
