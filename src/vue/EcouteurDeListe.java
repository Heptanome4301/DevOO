package vue;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * L'ecouteur de liste.
 */
public class EcouteurDeListe implements ListSelectionListener {
	/**
	 * La vue textuelle qui gère l'affichage de la liste ectoutee.
	 */
	private VueTextuelle vueTextuelle;
	
	/**
	 * Le constructeur de l'ecouteur.
	 * @param vueText la vue textuelle qui affiche la liste ecoutee.
	 */
	public EcouteurDeListe(VueTextuelle vueText) {
		this.vueTextuelle = vueText;
		vueTextuelle.addListListener(this);
	}
	
	/**
	 * Surcharge de la methode valueChanged, appelee lors de la modification de l'objet selectionne dans la liste
	 * @param e l'evenement decleche lors de la selection dans la vue.
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		vueTextuelle.changed();
	}
}
