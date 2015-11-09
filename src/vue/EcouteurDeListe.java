package vue;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import modele.Livraison;
import controleur.Controleur;

public class EcouteurDeListe implements ListSelectionListener {
	private Controleur controleur;
	private JList<Livraison> liste;
	
	public EcouteurDeListe(Controleur c, JList<Livraison> liste) {
		this.controleur = c;
		this.liste = liste;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		controleur.clicListLivraisons(liste.getSelectedValue());
	}

}
