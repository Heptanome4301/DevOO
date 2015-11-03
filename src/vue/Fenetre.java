package vue;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;

import modele.Plan;
import net.miginfocom.swing.MigLayout;

import org.graphstream.ui.swingViewer.ViewPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Fenetre {

	public JFrame frame;
	private JTextArea infoPoint;
	private JButton loadPlanning;
	private JButton loadDeliveries;
	private JButton computeTour;
	private JButton addDelivery;
	private JButton removeDelivery;
	private JButton swapDelivery;
	private JButton saveRoadMap;
	private JLabel labelDelivery;
	private JLabel labelTour;
	private JList<?> labelPointList;
	private JPanel view;
	private JTextField log;
	private JLabel lblListeDesPoints;

	private final String TITLE = "Livraison Simulator 2015";
	private final Dimension MINIMUM_SIZE = new Dimension(800, 600);

	/**
	 * Create the application.
	 */
	public Fenetre() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frame = new JFrame(this.TITLE);
		frame.setMinimumSize(this.MINIMUM_SIZE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new MigLayout("", "[20%,grow][60%,grow][20%][10%]",
						"[22.00][][][][][][][][][][grow][15%][]"));
		
		lblListeDesPoints = new JLabel("Liste des points de livraison");
		frame.getContentPane().add(lblListeDesPoints,
				"cell 0 0,alignx center,aligny center");

		labelPointList = new JList<String>();
		frame.getContentPane().add(labelPointList, "cell 0 1 1 10,grow");

		labelTour = new JLabel("Créer une tournée");
		labelTour.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(labelTour, "cell 2 0 2 1,growx");

		loadPlanning = new JButton("Charger un plan");
		frame.getContentPane().add(loadPlanning, "cell 2 1 2 1,growx");

		loadDeliveries = new JButton("Charger des livraisons");
		frame.getContentPane().add(loadDeliveries, "cell 2 2 2 1,growx");

		computeTour = new JButton("Calculer une tournée");
		frame.getContentPane().add(computeTour, "cell 2 3 2 1,growx");

		labelDelivery = new JLabel("Modifier une tournée");
		labelDelivery.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(labelDelivery, "cell 2 5 2 1,growx");

		addDelivery = new JButton("Ajouter une livraison");
		frame.getContentPane().add(addDelivery, "cell 2 6 2 1,growx");

		removeDelivery = new JButton("Supprimer une livraison");
		frame.getContentPane().add(removeDelivery, "cell 2 7 2 1,growx");

		swapDelivery = new JButton("Inverser deux livraisons");
		frame.getContentPane().add(swapDelivery, "cell 2 8 2 1,growx");

		infoPoint = new JTextArea();
		frame.getContentPane().add(infoPoint, "cell 0 11,grow");

		saveRoadMap = new JButton("Sauvegarder le feuille de route");
		frame.getContentPane().add(saveRoadMap, "cell 2 11 2 1,growx");

		log = new JTextField();
		log.setEditable(false);
		frame.getContentPane().add(log, "cell 0 12 4 1,growx");
		log.setColumns(10);
	}

	public void display() {
		frame.setVisible(true);
	}
	
	public void linkView(Plan plan) {
		this.view = new VueGraphique(plan, this);
		frame.getContentPane().add(view, "cell 1 1 1 11,grow");
	}

}
