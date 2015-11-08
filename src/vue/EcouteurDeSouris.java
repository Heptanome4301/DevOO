package vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controleur.Controleur;

public class EcouteurDeSouris extends MouseAdapter {
	private Controleur controleur;
	private VueGraphique view;
	private Fenetre fenetre;

	public EcouteurDeSouris(Controleur c, VueGraphique v, Fenetre f) {
		this.controleur = c;
		this.view = v;
		this.fenetre = f;
		System.out.println(v);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		
		switch (e.getButton()) {
		case MouseEvent.BUTTON1:
			int idAdresse = view.getAdresseByXY(e.getX(), e.getY());
			if(idAdresse>0)
				controleur.afficheInfos(idAdresse);
			break;

		case MouseEvent.BUTTON3:
			break;
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseReleased(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		super.mouseDragged(e);
	}
	
	
}
