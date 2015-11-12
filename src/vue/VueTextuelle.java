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

public class VueTextuelle implements Observer {


	private JList<Livraison> listLivraisons;
	private Controleur controleur;
	
	private Livraison selectedValue;
	
	public VueTextuelle(Controleur c, JList<Livraison> listAdressesLivraisons){
		this.listLivraisons = listAdressesLivraisons;
		this.controleur = c ;
		this.controleur.getPlan().addObserver(this);
		this.selectedValue = null;  
		
	}

	public void changed() {
		Livraison livraison = listLivraisons.getSelectedValue();
		if(livraison != null && ! livraison.equals(selectedValue) ){
			selectedValue = livraison;
			controleur.clicListLivraisons(livraison);
			// System.err.println("List changed");
		}				
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Plan) {
			if (arg != null && arg instanceof Tournee) {
				Tournee t = (Tournee) arg;
				// t.addObserver(this);
			}
		}

		this.actualiser();
		
	}

	
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

	private void setList(Livraison[] listData) {
		listLivraisons.setListData(listData);
	}
	
	protected void selectionnerList(Livraison livraison) {
		if(!livraison.equals(selectedValue)){
			selectedValue = livraison;
			listLivraisons.setSelectedValue(livraison, true);
		}
	}

	protected void deSelectionList() {
		listLivraisons.clearSelection();
	}


	protected void addListListener(EcouteurDeListe ecouteurDeListe) {
		listLivraisons.addListSelectionListener(ecouteurDeListe);	
	}

	
	
}
