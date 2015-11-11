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
import tsp.TSP2;

/**
 * Cette classe reprèsente un ensemble de livraison et le chemin qui les relie
 */
public class Tournee extends Observable{
        /**
         * Le plan de la ville nécessaire au calcul des chemins entre les différentes livraisons.
         */
	private Plan plan;
        /**
         * La liste des chemins qui relient les différentes livraisons.
         */
	private ArrayList<Chemin> chemins;
        /**
         * La liste des livraisons qui composent la tournée.
         */
	private Collection<Livraison> livraisons;
        /**
         * L'adresse de l'entrepot d'où part et où doit revenir la tournée.
         */
	private Adresse entrepot;
        /**
         * La durée nécessaire à effectuer la tournée.
         */
	private int Duree;
        /**
         * La liste des fenêtres horaire pour chaque livraison.
         */
	private ArrayList<FenetreLivraison> fenetresLivraison;

	/**
         * Le constructeur de la tournée. La durée et les chemins seront calculés par la suite.
         * @param plan Le plan de la ville.
         * @param livraisons La liste des livraisons à traiter.
         * @param fenetresLivraison La liste des fenêtres associées à chaque livraison.
         * @param entrepot l'adresse de l'entrepot.
         */
	public Tournee(Plan plan, Collection<Livraison> livraisons,
			ArrayList<FenetreLivraison> fenetresLivraison, Adresse entrepot) {
		this.plan = plan;
		this.livraisons = livraisons;
		this.entrepot = entrepot;
		this.chemins = new ArrayList<>();
		this.Duree = 0;
		this.fenetresLivraison = sortFenetreLivraison(fenetresLivraison);

	}

	
	/**
         * Trie la liste des fenêtres de livraison de manière croissante.
         * @param fenetresLivraison la liste des fenêtres à trier.
         * @return la liste triée.
         */
	private ArrayList<FenetreLivraison> sortFenetreLivraison(ArrayList<FenetreLivraison> collection) {
		int size = collection.size();
		for (int i = 0; i < size; i++) {
			int min_fenetre = i;
			for (int j = i + 1; j < size; j++) {
				if (collection.get(j).getHeureDebut()
						.before(collection.get(min_fenetre).getHeureDebut())) {
					min_fenetre = j;
				}
			}
			if (min_fenetre != i) {
				FenetreLivraison min = collection.get(min_fenetre);
				collection.set(min_fenetre, collection.get(i));
				collection.set(i, min);
			}

		}
		return collection;
	}

	/**
	 * Accesseur de l'attribut chemins
	 * @return la liste des chemins qui composent la tournée, null si le calcul n'a pas encore été réalisé.
	 */
	public Collection<Chemin> getItineraire() {
		return chemins;
	} 
	
	
        /**
         * Génère les instructions de circulation à fournir au livreur pour qu'il effectue la tournée en empruntant
         * les plus courts chemins.
         * @param fichier le fichier dans lequel écrire ces instructions.
         * @throws Exception Si un problème survient à l'ouverture ou à l'écriture du fichier.
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
                    
                    
                    while(!stack.isEmpty()){
                        int deb = Integer.parseInt(stack.pop());
                        int arr = Integer.parseInt(stack.pop());
                        
                        pw.print(" De : ");
                        pw.print(deb);
                        pw.print(" A : ");
                        pw.print(arr);
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
         * Construit le graphe des plus courts chemins entre chaque adresse de livraison. 
         * @param cheminsDuGraphe une matrice des chemins à ajouter au graphe.
         * @return le graphe construit.
         */
	private Graphe construireGraphe(Chemin[][] cheminsDuGraphe){
		int nb_sommet = cheminsDuGraphe.length;
		// double [][] res = new double[nb_sommet][nb_sommet];
		int[][] res = new int[nb_sommet][nb_sommet];
		for (int i = 0; i < nb_sommet; i++) {
			for (int j = 0; j < nb_sommet; j++) {
				if (cheminsDuGraphe[i][j] != null)
					res[i][j] = new Double(cheminsDuGraphe[i][j].getDuree())
							.intValue();
				else
					res[i][j] = -1;
			}
		}

		return new GrapheComplet(res);
	}
	
        /**
         * Recupère un chemin dans la liste dont l'adresse d'arrivée est celle passée en paramètre de cette méthode.
         * @param arrivee l'adresse d'arrivée du chemin recherché.
         * @return le chemin s'il esxiste, null sinon.
         */
	private Chemin getCheminFromArrivee(Adresse arrivee) {
		if (arrivee != null) {
			for (Chemin chemin : chemins) {
				if (chemin.getArrivee().equals(arrivee)) {
					return chemin;
				}
			}
		}
		return null;
	}
	
        /**
         * Recupère un chemin dans la listedont l'adresse de départ est celle passée en paramètre de cette méthode
         * @param depart l'adresse de départ du chemin recherché.
         * @return le chein s'il existe, null sinon.
         */
	private Chemin getCheminFromDepart(Adresse depart) {
		if (depart != null) {
			for (Chemin chemin : chemins) {
				if (chemin.getDepart().equals(depart)) {
					return chemin;
				}
			}
		}
		return null;
	}
        
        /**
         * Récupère les livraisons associées à une fenêtre de livraison passée en paramètre.
         * @param fenetreLivraison la fenêtre de livraison.
         * @return les livraisons associées à cette fenêtre.
         */
	private Set<Livraison> getLivraison(FenetreLivraison fenetreLivraison){
		Set<Livraison> livraisonF = new HashSet<Livraison>();
		for (Livraison l : this.livraisons) {
			if ((l.getFenetreLivraison()).equals(fenetreLivraison)) {
				livraisonF.add(l);
			}
		}
		return livraisonF;
	}
	
        /**
         * Construit une matrice de Chemins (les plus courts chemins) entre chaque adresse associées à la fenêtre
         * de livraison passée en paramètre.
         * @param fenetreLivraison la fenêtre de livraison que l'on souhaite traiter.
         * @param adrssDepart une adresse supplémentaire (soit l'entrepot pour la première fenêtre soit la dernière
         * livraison effectuée lors de la fenêtre précédente.
         * @return mat la matrice des plus courts chemins. mat[i][j] est le plus court chemin reliant la i-ème adresse
         * de livraison à la j-éme.
         */
	private Chemin[][] construireAllChemin(FenetreLivraison fenetreLivraison,Adresse adrssDepart){
		//Livraison premiere =getLivraison(adrssDepart);
		Livraison premiere = adrssDepart.getLivraison();
		if(premiere == null){
			//System.err.println(adrssDepart.equals(entrepot));
			//System.err.println("Livraison associer a l'adresse "+adrssDepart.getId()+" de la fenetre"+fenetreLivraison.getHeureDebut()+" est null");
			premiere =new Livraison(-1, adrssDepart, fenetreLivraison);
		}
		Set<Livraison> livraisons = getLivraison(fenetreLivraison);
		if (livraisons.contains(premiere))
			livraisons.remove(premiere);

		int nb_sommet = livraisons.size();
		nb_sommet++;
		Chemin[][] chemins = new Chemin[nb_sommet][nb_sommet];

		int i = 1, j = 1;
		for (Livraison l : livraisons) {
			j = 1;
			for (Livraison ll : livraisons) {
				if (l == ll) {
					chemins[i][j] = null;
				} else {
					chemins[i][j] = plan.calculerChemin(l.getAdresse(),
							ll.getAdresse());
				}
				j++;
			}
			i++;
		}
		chemins[0][0] = null;
		j = 1;
		for (Livraison ll : livraisons) {
			chemins[0][j] = plan.calculerChemin(premiere.getAdresse(),
					ll.getAdresse());
			chemins[j][0] = plan.calculerChemin(ll.getAdresse(),
					premiere.getAdresse());
			j++;
		}
		if (premiere.getId() < 0) {
			premiere.getAdresse().setLivraison(null);
			livraisons.remove(premiere);
		}
		return chemins;
	}
	
        /**
         * Calcule la tournée, c'est à dire le moyen le plus rapide d'effectuer toutes les livraisons en respectant 
         * au maximum les contraintes horaires des fenêtres de livraison. Cette méthode stock le résultat (la liste
         * des chemins) dans l'attribut chemin, et met à jour l'attribut durée en conséquence.
         */
	public void calculerTournee(){ //todo prÃ©voir que les profs peuvent Ãªtre des trolls : cul de sac
		System.out.println("Cacul de la tournï¿½e...");
		this.chemins = new ArrayList<Chemin>();

		for (FenetreLivraison fl : fenetresLivraison) {
			Adresse debutTournee = entrepot;
			if (!chemins.isEmpty()) {// donc c'est pas la premiere fenetre
				debutTournee = ((ArrayList<Chemin>) chemins).get(
						chemins.size() - 1).getArrivee();
			}
			ArrayList<Chemin> tmp = calculerTourneeFenetre(debutTournee, fl);

			chemins.addAll(tmp);

		}

		chemins.add(plan.calculerChemin(
				((ArrayList<Chemin>) chemins).get(chemins.size() - 1)
						.getArrivee(), this.entrepot));

		calculerLesDurees(0);
		this.setChanged();
		this.notifyObservers();

		this.livraisons = sortLivraison((ArrayList<Livraison>) this.livraisons);
		System.out.println("Tournee calculÃ©e : DurÃ©e totale " + getDuree());
	}

	// indice dans chemins du premier chemin Ã  partir dulequel
	// il faut calculer/recalculer les durees
        /**
         * Cette méthode met à jour les heures de livraisons prévues pour toutes les livraisons suivant la i-ème 
         * à partir du temps de parcours calculé.
         * @param indiceDepart l'indice de la livraison à partir de laquelle on veut mettre à jour les horaires
         * de livraison.
         */
	private void calculerLesDurees(int indiceDepart){
		Date horaire = null ;
		Chemin PremierChemin = chemins.get(indiceDepart);
		if (PremierChemin.getDepart().equals(entrepot)) {
			horaire = fenetresLivraison.get(0).getHeureDebut();
		} else {
			// horaire = getLivraison(PremierChemin.getDepart()).getHoraire();
			horaire = PremierChemin.getDepart().getLivraison().getHoraire();
		}

		for (int i = indiceDepart; i < chemins.size() - 1; i++) {
			Chemin chemin = chemins.get(i);
			// Livraison l = getLivraison(chemin.getArrivee());
			Livraison l = chemin.getArrivee().getLivraison();
			horaire = addSecondsHoraire(horaire, chemin.getDuree());

			if (horaire.before(l.getFenetreLivraison().getHeureDebut())) {
				horaire = l.getFenetreLivraison().getHeureDebut();
			}

			l.setHoraire(horaire);

			l.positionnerRetard(); // positionner le retard s'il existe
		}

		this.Duree = (int) (addSecondsHoraire(horaire,
				chemins.get(chemins.size() - 1).getDuree()).getTime() / 1000 - fenetresLivraison
				.get(0).getHeureDebut().getTime() / 1000);
		System.out.println("verif "
				+ addSecondsHoraire(fenetresLivraison.get(0).getHeureDebut(),
						Duree));
		System.out.println("duree retour Ã  la base (min) "
				+ chemins.get(chemins.size() - 1).getDuree() / 60);

	}
	
	/**
         * Cette méthode calcule une nouvelle date à partir de celle passée en paramètre à laquelle elle ajoute un 
         * nombre de secondes égal au deuxième paramètre.
         * @param horaire la date originale.
         * @param seconds le nombre de secondes à ajouter.
         * @return la nouvelle date.
         */
	private Date addSecondsHoraire(Date horaire,int seconds ){
		Calendar cal = Calendar.getInstance();
		cal.setTime(horaire);
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}
	
	/** 
         * Cette méthode calcule un morceau de la tournée associé à une fenêtre de livraison. 
	 * @return la liste des chemins qui composent ce morceau de la tournée.
	 */

	public ArrayList<Chemin> calculerTourneeFenetre(Adresse addssDepart,
			FenetreLivraison fenetreLivraison) {
		int tpsLimite = 3000;

		Chemin[][] AllCheminsGraphe = construireAllChemin(fenetreLivraison,
				addssDepart);
		Graphe graphe = construireGraphe(AllCheminsGraphe);

		TSP tsp = new TSP2();
		tsp.chercheSolution(tpsLimite, graphe);

		// this.Duree +=tsp.getCoutSolution();
		ArrayList<Chemin> res = new ArrayList<Chemin>();

		int i, j, I, J;
		for (j = 1; j < graphe.getNbSommets(); j++) {
			i = j - 1;
			I = tsp.getSolution(i);
			J = tsp.getSolution(j);
			// this.chemins.add(AllCheminsGraphe[I][J]);
			res.add(AllCheminsGraphe[I][J]);
		}
		return res;
	}
	
	/**
         * Accesseur de l'attribut livraisons.
         * @return la liste des livraisons.
         */
	public Collection<Livraison> getLivraisons() {
		return livraisons;
	}
        
        /**
         * Mutateur de l'attribut livraisons.
         * @param livraisons la nouvelle liste de livraisons à traiter.
         */
	public void setLivraisons(Collection<Livraison> livraisons) {
		this.livraisons = livraisons;
	}
        
        /**
         * Accesseur de l'attribut plan.
         * @return le plan.
         */
	public Plan getPlan() {
		return plan;
	}
        
        /**
         * Accesseur de l'attribut entrepot
         * @return l'adresse de l'entrepot associé à cette tournée.
         */
	public Adresse getEntrepot() {
		return entrepot;
	}

        /**
         * Ajoute la livraison lAdd à la tournéee, juste avant la livraison lFollow. Si lFollow n'est pas dans la liste,
         * lAdd est ajouté à la fin de la tournée. La liste des chemins est mis à jour.
         * @param lAdd la livraison à ajouter.
         * @param lFollow la livraison avant laquelle ajouter lAdd.
         */
	public void ajouterLivraison(Livraison lAdd,Livraison lFollow) {
		if(!lAdd.equals(lFollow)) {
			Adresse adresseAdd = lAdd.getAdresse();
			Adresse adresseFollow = lFollow.getAdresse();
			Chemin cheminToRemove = getCheminFromArrivee(adresseFollow);
			Chemin chemin1 = plan.calculerChemin(cheminToRemove.getDepart(),
					adresseAdd);
			Chemin chemin2 = plan.calculerChemin(adresseAdd, adresseFollow);
			int i;
			for (i = 0; i < chemins.size(); i++) {
				if ((chemins.get(i)).equals(cheminToRemove)) {
					chemins.add(i, chemin1);
					chemins.add(i + 1, chemin2);
					break;
				}
			}
			chemins.remove(cheminToRemove);
			livraisons.add(lAdd);
			calculerLesDurees(i); // recalcule les durÃ©es Ã  partir de ce chmin
		} else {

			Adresse entrepot = (chemins.get(chemins.size() - 1)).getArrivee();
			Adresse lastAdresse = (chemins.get(chemins.size() - 2))
					.getArrivee();
			Chemin chemin = plan.calculerChemin(lastAdresse, lAdd.getAdresse());
			Chemin cheminVersEntrepot = plan.calculerChemin(lAdd.getAdresse(),
					entrepot);
			chemins.remove(chemins.size() - 1);
			chemins.add(chemins.size(), chemin);
			chemins.add(chemins.size(), cheminVersEntrepot);
			livraisons.add(lAdd);
			calculerLesDurees(chemins.size() - 2);

		}
		this.setChanged();
		this.notifyObservers();

	}

	/**
	 * Supprime la livraison de la tournee et recalcule les heures de passage
	 * @param L
	 */
	public void supprimerLivraison(Livraison L) {
		if (livraisons.contains(L)) {
			Chemin chemin1 = getCheminFromArrivee(L.getAdresse());
			Chemin chemin2 = getCheminFromDepart(L.getAdresse());
			if (chemin1 != null && chemin2 != null) {
				Chemin newChemin = plan.calculerChemin(chemin1.getDepart(),
						chemin2.getArrivee());
				int i;
				for (i = 0; i < chemins.size(); i++) {
					if ((chemins.get(i)).equals(chemin2)) {
						chemins.add(i, newChemin);
						break;
					}
				}
				chemins.remove(chemin1);
				chemins.remove(chemin2);
				livraisons.remove(L);
				L.getAdresse().setLivraison(null);
				calculerLesDurees(i - 1); // recalcule les durÃ©es Ã  partir de ce
											// chmin
				this.setChanged();
				this.notifyObservers();
			}

		}
	}	

        /**
         * Echange l'ordre de passage de deux livraisons et recalcule les heures de passage
         * Ne fait rien si les deux livraisons sont les mêmes
         * @param l1 la première livraison.
         * @param l2 la seconde livraison.
         */
	public void echangerLivraison(Livraison l1,Livraison l2) {
		if(livraisons.contains(l1) && livraisons.contains(l2) && !l1.equals(l2)) {
			Chemin cheminRm1A = getCheminFromArrivee(l1.getAdresse());
			Chemin cheminRm1D = getCheminFromDepart(l1.getAdresse());
			Chemin cheminRm2A = getCheminFromArrivee(l2.getAdresse());
			Chemin cheminRm2D = getCheminFromDepart(l2.getAdresse());

			Chemin cheminAdd1A = plan.calculerChemin(cheminRm1A.getDepart(),
					l2.getAdresse());
			Chemin cheminAdd1D = plan.calculerChemin(l2.getAdresse(),
					cheminRm1D.getArrivee());
			Chemin cheminAdd2A = plan.calculerChemin(cheminRm2A.getDepart(),
					l1.getAdresse());
			Chemin cheminAdd2D = plan.calculerChemin(l1.getAdresse(),
					cheminRm2D.getArrivee());
			int indiceModif = 0;
			for (int i = 0; i < chemins.size(); i++) {
				if (chemins.get(i).equals(cheminRm2A)) {
					chemins.add(i, cheminAdd2A);
					chemins.add(i + 1, cheminAdd2D);
					if (indiceModif == 0)
						indiceModif = i;
					break;
				}
			}
			for (int i = 0; i < chemins.size(); i++) {
				if (chemins.get(i).equals(cheminRm1A)) {
					chemins.add(i, cheminAdd1A);
					chemins.add(i + 1, cheminAdd1D);
					if (i < indiceModif)
						indiceModif = i;
					break;
				}
			}
			chemins.remove(cheminRm1A);
			chemins.remove(cheminRm1D);
			chemins.remove(cheminRm2A);
			chemins.remove(cheminRm2D);
			l1.getAdresse().setLivraison(l2);
			l2.getAdresse().setLivraison(l1);
			calculerLesDurees(indiceModif);
			this.setChanged();
			this.notifyObservers();
		}

	}
        
        /**
         * Cette méthode récupère la livraison suivant celle passée en paramètre dans la liste des livraisons
         * qui composent la tournée.
         * @param livraison
         * @return la livraison suivante.
         */
	public Livraison getFollowingLivraison(Livraison livraison) {
		Chemin chemin = getCheminFromDepart(livraison.getAdresse());
		Adresse adresseF = chemin.getArrivee();
		return adresseF.getLivraison();
	}
	
	/**
         * Accesseur de l'attribut duree.
         * @return la duree totale du trajet de cette tournee.
         */
	public int getDuree() {
		return Duree;
	}
        
        /**
         * Accesseur de l'attribut fenetreLivraison.
         * @return la liste des fenêtres de livraison prévues pour cette tournée.
         */
	public Collection<FenetreLivraison> getFenetresLivraison() {
		return fenetresLivraison;
	}

	/**
         * Cette méthode permet de récupérer la i-ème fenêtre de livraison de l'attribut fenetreLivraison.
         * @param index
         * @return la i-ème fenêre de livraison.
         */
	public FenetreLivraison getFenetreLivraisonIndx(int index) {
		return ((ArrayList<FenetreLivraison>) fenetresLivraison).get(index);

	}
	
	/**
         * Cette léthode vérifie si le tronçon t passé en paramètre est emprunté lors de la tournée.
         * @param t
         * @return vrai s'il est emprunté, false sinon.
         */
	public boolean isDansTrournee(Troncon t) {
		if (chemins != null) {
			for (Chemin ch : chemins) {
				if (ch.contient(t))
					return true;
			}
		}
		return false;
	}
        
        /**
         * Trie les livraisons en fonction de leur fenêtre respectives.
         * @param collection la liste de livraisons à trier.
         * @return la liste de livraisons triées.
         */
	private ArrayList<Livraison> sortLivraison(ArrayList<Livraison> collection) {
		int size = collection.size();
		for (int i = 0; i < size; i++) {
			int max_fenetre = i;
			for (int j = i + 1; j < size; j++) {
				if (collection.get(j).getHoraire()
						.after(collection.get(max_fenetre).getHoraire())) {
					max_fenetre = j;
				}
			}
			if (max_fenetre != i) {
				Livraison max = collection.get(max_fenetre);
				collection.set(max_fenetre, collection.get(i));
				collection.set(i, max);
			}

		}
		return collection;
	}
        
        /**
         * Accesseur de l'attribut livraisons qui les trie avant de les renvoyer.
         * @return l'attribut livraisons trié.
         */
	public Collection<Livraison> getSortedLivraisons() {
		this.livraisons = sortLivraison((ArrayList<Livraison>) this.livraisons);
		return this.livraisons;
	}
}
