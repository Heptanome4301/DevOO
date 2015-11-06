package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import modele.Adresse;
import modele.Plan;
import modele.Troncon;

public class VueGraphique extends JPanel implements Observer {
	/**
	 * 
	 */
	private final int RAYON_NOEUD = 10;
	private double echelle = 1;
	
	private int maxLargeur;
	private int maxHauteur;
	
	private Plan plan;
	
	public VueGraphique(Plan plan, Fenetre fenetre) {
		super();
		this.plan = plan;
		plan.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {

		if ( o instanceof Plan ) {
//			this.echelle = getEchelle();
			this.repaint();
		}
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		super.paintComponent(g);
				
		for (Adresse a : this.plan.getAdresses()) {
			g.setColor(Color.gray);
			for (Troncon t : a.getTroncons()) {
	        	g.drawLine( (int) (t.getDepart().getX() * echelle), (int) (t.getDepart().getY() * echelle), (int) (t.getArrivee().getX() * echelle), (int) (t.getArrivee().getY() * echelle));
			}
			
//			if (a.getX() > this.maxLargeur)
//			{
//				this.maxLargeur = a.getX();
//			}
//			if (a.getY() > this.maxHauteur)
//			{
//				this.maxHauteur = a.getY();
//			}
			
			
		    g.fillOval( (int) ((a.getX() - RAYON_NOEUD/2) * echelle), (int) ((a.getY() - RAYON_NOEUD/2) *  echelle), (int) (RAYON_NOEUD * echelle), (int) (RAYON_NOEUD * echelle));
		    g.setColor(Color.black);
		    g.drawOval( (int) ((a.getX() - RAYON_NOEUD/2) * echelle), (int) ((a.getY() - RAYON_NOEUD/2) * echelle), (int) (RAYON_NOEUD * echelle), (int) (RAYON_NOEUD * echelle));
		    
		}
		
//		this.getParent().setPreferredSize(new Dimension(this.maxLargeur, this.maxHauteur));
		
    }

	public void setZoom(double value) {
		this.echelle = value;
	}
}
