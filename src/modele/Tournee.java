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
 * Cette classe repr�sente un ensemble de livraison et le chemin qui les relie
 */
public class Tournee extends Observable{
        /**
         * Le plan de la ville n�cessaire au calcul des chemins entre les diff�rentes livraisons.
         */
	private Plan plan;
        /**
         * La liste des chemins qui relient les diff�rentes livraisons.
         */
	private ArrayList<Chemin> chemins;
        /**
         * La liste des livraisons qui composent la tourn�e.
         */
	private Collection<Livraison> livraisons;
        /**
         * L'adresse de l'entrepot d'o� part et o� doit revenir la tourn�e.
         */
	private Adresse entrepot;
        /**
         * La dur�e n�cessaire � effectuer la tourn�e.
         */
	private int Duree;
        /**
         * La liste des fen�tres horaire pour chaque livraison.
         */
	private ArrayList<FenetreLivraison> fenetresLivraison;

	/**
         * Le constructeur de la tourn�e. La dur�e et les chemins seront calcul�s par la suite.
         * @param plan Le plan de la ville.
         * @param livraisons La liste des livraisons � traiter.
         * @param fenetresLivraison La liste des fen�tres associ�es � chaque livraison.
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
         * Trie la liste des fen�tres de livraison de mani�re croissante.
         * @param fenetresLivraison la liste des fen�tres � trier.
         * @return la liste tri�e.
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
	 * @return la liste des chemins qui composent la tourn�e, null si le calcul n'a pas encore �t� r�alis�.
	 */
	public Collection<Chemin> getItineraire() {
		return chemins;
	} 
	
	
        /**
         * G�n�re les instructions de circulation � fournir au livreur pour qu'il effectue la tourn�e en empruntant
         * les plus courts chemins.
         * @param fichier le fichier dans lequel �crire ces instructions.
         * @throws Exception Si un probl�me survient � l'ouverture ou � l'�criture du fichier.
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
         * @param cheminsDuGraphe une matrice des chemins � ajouter au graphe.
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
         * Recup�re un chemin dans la liste dont l'adresse d'arriv�e est celle pass�e en param�tre de cette m�thode.
         * @param arrivee l'adresse d'arriv�e du chemin recherch�.
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
         * Recup�re un chemin dans la listedont l'adresse de d�part est celle pass�e en param�tre de cette m�thode
         * @param depart l'adresse de d�part du chemin recherch�.
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
         * R�cup�re les livraisons associ�es � une fen�tre de livraison pass�e en param�tre.
         * @param fenetreLivraison la fen�tre de livraison.
         * @return les livraisons associ�es � cette fen�tre.
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
         * Construit une matrice de Chemins (les plus courts chemins) entre chaque adresse associ�es � la fen�tre
         * de livraison pass�e en param�tre.
         * @param fenetreLivraison la fen�tre de livraison que l'on souhaite traiter.
         * @param adrssDepart une adresse suppl�mentaire (soit l'entrepot pour la premi�re fen�tre soit la derni�re
         * livraison effectu�e lors de la fen�tre pr�c�dente.
         * @return mat la matrice des plus courts chemins. mat[i][j] est le plus court chemin reliant la i-�me adresse
         * de livraison � la j-�me.
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
         * Calcule la tourn�e, c'est � dire le moyen le plus rapide d'effectuer toutes les livraisons en respectant 
         * au maximum les contraintes horaires des fen�tres de livraison. Cette m�thode stock le r�sultat (la liste
         * des chemins) dans l'attribut chemin, et met � jour l'attribut dur�e en cons�quence.
         */
	public void calculerTournee(){ //todo prévoir que les profs peuvent être des trolls : cul de sac
		System.out.println("Cacul de la tourn�e...");
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
		System.out.println("Tournee calculée : Durée totale " + getDuree());
	}

	// indice dans chemins du premier chemin à partir dulequel
	// il faut calculer/recalculer les durees
        /**
         * Cette m�thode met � jour les heures de livraisons pr�vues pour toutes les livraisons suivant la i-�me 
         * � partir du temps de parcours calcul�.
         * @param indiceDepart l'indice de la livraison � partir de laquelle on veut mettre � jour les horaires
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
		System.out.println("duree retour à la base (min) "
				+ chemins.get(chemins.size() - 1).getDuree() / 60);

	}
	
	/**
         * Cette m�thode calcule une nouvelle date � partir de celle pass�e en param�tre � laquelle elle ajoute un 
         * nombre de secondes �gal au deuxi�me param�tre.
         * @param horaire la date originale.
         * @param seconds le nombre de secondes � ajouter.
         * @return la nouvelle date.
         */
	private Date addSecondsHoraire(Date horaire,int seconds ){
		Calendar cal = Calendar.getInstance();
		cal.setTime(horaire);
		cal.add(Calendar.SECOND, seconds);
		return cal.getTime();
	}
	
	/** 
         * Cette m�thode calcule un morceau de la tourn�e associ� � une fen�tre de livraison. 
	 * @return la liste des chemins qui composent ce morceau de la tourn�e.
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
         * @param livraisons la nouvelle liste de livraisons � traiter.
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
         * @return l'adresse de l'entrepot associ� � cette tourn�e.
         */
	public Adresse getEntrepot() {
		return entrepot;
	}

        /**
         * Ajoute la livraison lAdd � la tourn�ee, juste avant la livraison lFollow. Si lFollow n'est pas dans la liste,
         * lAdd est ajout� � la fin de la tourn�e. La liste des chemins est mis � jour.
         * @param lAdd la livraison � ajouter.
         * @param lFollow la livraison avant laquelle ajouter lAdd.
         */
	public void ajouterLivraison(Livraison lAdd,Livraison lFollow) {
                lAdd.getAdresse().setLivraison(lAdd);
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
			calculerLesDurees(i); // recalcule les durées à partir de ce chmin
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
				calculerLesDurees(i - 1); // recalcule les durées à partir de ce
											// chmin
				this.setChanged();
				this.notifyObservers();
			}

		}
	}	

        /**
         * Echange l'ordre de passage de deux livraisons et recalcule les heures de passage
         * Ne fait rien si les deux livraisons sont les m�mes
         * @param l1 la premi�re livraison.
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
         * Cette m�thode r�cup�re la livraison suivant celle pass�e en param�tre dans la liste des livraisons
         * qui composent la tourn�e.
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
         * @return la liste des fen�tres de livraison pr�vues pour cette tourn�e.
         */
	public Collection<FenetreLivraison> getFenetresLivraison() {
		return fenetresLivraison;
	}

	/**
         * Cette m�thode permet de r�cup�rer la i-�me fen�tre de livraison de l'attribut fenetreLivraison.
         * @param index
         * @return la i-�me fen�re de livraison.
         */
	public FenetreLivraison getFenetreLivraisonIndx(int index) {
		return ((ArrayList<FenetreLivraison>) fenetresLivraison).get(index);

	}
	
	/**
         * Cette l�thode v�rifie si le tron�on t pass� en param�tre est emprunt� lors de la tourn�e.
         * @param t
         * @return vrai s'il est emprunt�, false sinon.
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
         * Trie les livraisons en fonction de leur fen�tre respectives.
         * @param collection la liste de livraisons � trier.
         * @return la liste de livraisons tri�es.
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
         * @return l'attribut livraisons tri�.
         */
	public Collection<Livraison> getSortedLivraisons() {
		this.livraisons = sortLivraison((ArrayList<Livraison>) this.livraisons);
		return this.livraisons;
	}
}
