package vue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JList;

import controleur.Controleur;

import modele.Livraison;
import modele.Plan;
import modele.Tournee;

public class VueTextuelle implements Observer {


	private JList<Livraison> listLivraisons;
	private Controleur controleur;
	
	public VueTextuelle(Controleur c, JList<Livraison> listAdressesLivraisons){
		this.listLivraisons = listAdressesLivraisons;
		this.controleur = c ;
		this.controleur.getPlan().addObserver(this);
		
	}

	public void changed() {
		Livraison livraison = listLivraisons.getSelectedValue();
		if(livraison != null){
			controleur.clicListLivraisons(livraison);
		}				
	}

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

	private void actualiser() {
		if(controleur.getTournee()!= null){
			Collection<Livraison> livraisons =  controleur.getTournee().getLivraisons();
			int i=0,size = livraisons.size();
			Livraison[] listData = new Livraison[size];
			for(Livraison l : livraisons) listData[i++] = l;
			//listData = (Livraison[]) controleur.getTournee().getLivraisons().toArray();
			setList( listData);
		}
	}

	private void setList(Livraison[] listData) {
		listLivraisons.setListData(listData);
	}
	
	protected void selectionnerList(Livraison livraison) {
		listLivraisons.setSelectedValue(livraison, true);
		
	}

	protected void deSelectionList() {
		listLivraisons.clearSelection();
	}


	protected void addListListener(EcouteurDeListe ecouteurDeListe) {
		listLivraisons.addListSelectionListener(ecouteurDeListe);
		
	}

	
	
}
