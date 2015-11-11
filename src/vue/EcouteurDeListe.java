package vue;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.Livraison;
import controleur.Controleur;

public class EcouteurDeListe implements ListSelectionListener {
	private VueTextuelle vueTextuelle;
	
	public EcouteurDeListe(VueTextuelle vueText) {
		this.vueTextuelle = vueText;
		vueTextuelle.getListLivraisons().addListSelectionListener(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		vueTextuelle.changed();
	}

}
