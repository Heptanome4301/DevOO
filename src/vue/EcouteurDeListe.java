package vue;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class EcouteurDeListe implements ListSelectionListener {
	private VueTextuelle vueTextuelle;
	
	public EcouteurDeListe(VueTextuelle vueText) {
		this.vueTextuelle = vueText;
		vueTextuelle.addListListener(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		vueTextuelle.changed();
	}

}
