package vue;

import java.awt.Graphics;
import java.util.Collection;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import modele.Adresse;
import modele.Plan;
import modele.Troncon;

public class VueGraphique extends JPanel implements Observer {
	private Plan plan;
	private Fenetre fenetre;
	private Object element;
	
	public VueGraphique(Plan plan, Fenetre fenetre) {
		this.plan = plan;
		this.fenetre = fenetre;
		plan.addObserver(this);
		display();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		if ( o instanceof Plan ) {
			this.display();
		}
	}
	
	public void display() {
		this.repaint();
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (element instanceof Adresse)
        {
        	
        	Adresse adresse = (Adresse) element;
        	g.fillOval(adresse.getX(), adresse.getY(), 10, 10);
        	
        } else if (element instanceof Troncon) {
        	
        	Troncon troncon = (Troncon) element;
        	g.drawLine(troncon.getDepart().getX(), troncon.getDepart().getY(), troncon.getArrivee().getX(), troncon.getArrivee().getY());
        	
        }
        // Draw a little square at where the mouse was clicked.
//        for (Adresse adresse : adresses) {
//        	g.fillOval(adresse.getX(), adresse.getY(), 10, 10);
//        	Collection<Troncon> troncons = adresse.getTroncons();
//        	for (Troncon troncon : troncons) {
//        		g.drawLine(adresse.getX(), adresse.getY(), troncon.getArrivee().getX(), troncon.getArrivee().getY());
//        	}
//        }
        	
    }
	
}
