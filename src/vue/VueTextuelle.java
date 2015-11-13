package vue;

import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JList;

import controleur.Controleur;

import modele.FenetreLivraison;
import modele.Livraison;
import modele.Plan;
import modele.Tournee;

/**
 * La vue textuelle qui gere l'affichage textuelle de l'application.
 */
public class VueTextuelle implements Observer {

	/**
	 * La liste de livraison a afficher.
	 */
	private JList<Livraison> listLivraisons;
	/**
	 * Le controleur a notifier.
	 */
	private Controleur controleur;
	/**
	 * La livraison selectionnee dans la liste.
	 */
	private Livraison selectedValue;
	
	/**
	 * Le constructeur de la vue.
	 * @param c le controleur a notifier.
	 * @param listAdressesLivraisons la liste de livraison a afficher.
	 */
	public VueTextuelle(Controleur c, JList<Livraison> listAdressesLivraisons){
		this.listLivraisons = listAdressesLivraisons;
		this.controleur = c ;
		this.controleur.getPlan().addObserver(this);
		this.selectedValue = null;  
	}

	/**
	 * La metohde appelee par le listener lors de la modifiaction de la livraison selectionne dans la liste.
	 */
	public void changed() {
		Livraison livraison = listLivraisons.getSelectedValue();
		if(livraison != null && ! livraison.equals(selectedValue) ){
			selectedValue = livraison;
			controleur.clicListLivraisons(livraison);
			// System.err.println("List changed");
		}				
	}

	/**
	 * Surcharge de la methode necessaire au fonctionnement du design pattern observable
	 * @param o l'objet observe.
	 * @param arg l'objet a l'origine de la modification.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Plan) {
			if (arg != null && arg instanceof Tournee) {
				Tournee t = (Tournee) arg;
				t.addObserver(this);
			}
		}

		this.actualiser();
		
	}

	/**
	 * Met a jour l'afficahge de la liste de livraison.
	 */
	private void actualiser() {
		if(controleur.getTournee()!= null){
			Collection<Livraison> livraisons =  controleur.getTournee().getLivraisons();
			int size = livraisons.size();
			Livraison[] listData = new Livraison[size];
			for(Livraison l : livraisons) listData[--size] = l;  
			setList( listData);
		}else {
			Livraison[] listData = new Livraison[0];
			setList(listData);
		}
	}
	
	/**
	 * Mutateur de l'attribut listeLivraisons.
	 * @param listData un tableau contenant les livraisons a ajouter dans la liste.
	 */
	private void setList(Livraison[] listData) {
		listLivraisons.setListData(listData);
	}
	
	/**
	 * Change la livraison selectionnee dans la liste.
	 * @param livraison la livraison a selectionner.
	 */
	protected void selectionnerList(Livraison livraison) {
		if(!livraison.equals(selectedValue)){
			selectedValue = livraison;
			listLivraisons.setSelectedValue(livraison, true);
		}
	}

	/**
	 * Reinitialise la selection dans la liste.
	 */
	protected void deSelectionList() {
		listLivraisons.clearSelection();
	}

	protected void addListListener(EcouteurDeListe ecouteurDeListe) {
		listLivraisons.addListSelectionListener(ecouteurDeListe);	
	}

	
	
}
