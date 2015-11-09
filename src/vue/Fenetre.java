package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.Observer;

import javax.swing.*;

import controleur.Controleur;
import modele.Plan;
import modele.Tournee;
import net.miginfocom.swing.MigLayout;

import util.Constants;

public class Fenetre {

	public JFrame frame;
	private JTextArea infoPoint;
	private JButton loadPlan;
	private JButton loadDeliveries;
	private JButton computeTour;
	private JButton addDelivery;
	private JButton removeDelivery;
	private JButton swapDelivery;
	private JButton saveRoadMap;
	private JLabel labelDelivery;
	private JLabel labelTour;
	private JList<?> labelPointList;
	private VueGraphique view;
	private JScrollPane scrollPanel;
	private JTextField log;
	private JLabel lblListeDesPoints;
	
	private EcouteurDeBoutons ecouteurBoutons;
	private EcouteurDeSouris ecouteurSouris;

	private final String TITLE = "Livraison Simulator 2015";
	private final Dimension MINIMUM_SIZE = new Dimension(800, 600);
	private final Color ERROR_COLOR = Color.RED;
	private JSlider zoom;
	private JLabel lblZoom;

	/**
	 * Create the application.
	 */
	public Fenetre(Controleur c, Plan p) {
		initialize();
		ajouterView(p);
		initializeListeners(c);
	}

	
	private void initializeListeners(Controleur c) {
		this.ecouteurBoutons = new EcouteurDeBoutons(c);
		this.ecouteurSouris = new EcouteurDeSouris(c, view, this);
				
		loadPlan.addActionListener(ecouteurBoutons);
		loadDeliveries.addActionListener(ecouteurBoutons);
		computeTour.addActionListener(ecouteurBoutons);
		addDelivery.addActionListener(ecouteurBoutons);
		removeDelivery.addActionListener(ecouteurBoutons);
		swapDelivery.addActionListener(ecouteurBoutons);
		saveRoadMap.addActionListener(ecouteurBoutons);

		view.addMouseListener(ecouteurSouris);
		
//		zoom.addChangeListener(new ZoomListener(view));
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
		
		lblZoom = new JLabel("Zoom");
		frame.getContentPane().add(lblZoom, "flowx,cell 1 0,alignx right");
		
		zoom = new JSlider();
		zoom.setValue(100);
		zoom.setMinimum(10);
		zoom.setMaximum(200);
		zoom.setPaintLabels(true);
		
		frame.getContentPane().add(zoom, "cell 1 0,alignx right");

		labelPointList = new JList<String>();
		frame.getContentPane().add(labelPointList, "cell 0 1 1 10,grow");

		labelTour = new JLabel("Cr�er une tourn�e");
		labelTour.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(labelTour, "cell 2 0 2 1,growx");

		loadPlan = new JButton("Charger un plan");
		frame.getContentPane().add(loadPlan, "cell 2 1 2 1,growx");

		loadDeliveries = new JButton("Charger des livraisons");
		frame.getContentPane().add(loadDeliveries, "cell 2 2 2 1,growx");

		computeTour = new JButton("Calculer une tourn�e");
		frame.getContentPane().add(computeTour, "cell 2 3 2 1,growx");

		labelDelivery = new JLabel("Modifier une tourn�e");
		labelDelivery.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(labelDelivery, "cell 2 5 2 1,growx");

		addDelivery = new JButton("Ajouter une livraison");
		frame.getContentPane().add(addDelivery, "cell 2 6 2 1,growx");

		removeDelivery = new JButton("Supprimer une livraison");
		frame.getContentPane().add(removeDelivery, "cell 2 7 2 1,growx");

		swapDelivery = new JButton("Inverser deux livraisons");
		frame.getContentPane().add(swapDelivery, "cell 2 8 2 1,growx");

		infoPoint = new JTextArea();
		infoPoint.setMargin(new Insets(Constants.MARGIN_TEXTE_PANEL, Constants.MARGIN_TEXTE_PANEL, Constants.MARGIN_TEXTE_PANEL, Constants.MARGIN_TEXTE_PANEL));
		frame.getContentPane().add(infoPoint, "cell 0 11,grow");

		saveRoadMap = new JButton("Sauvegarder la feuille de route");
		frame.getContentPane().add(saveRoadMap, "cell 2 11 2 1,growx");

		log = new JTextField();
		log.setEditable(false);
		frame.getContentPane().add(log, "cell 0 12 4 1,growx");
		log.setColumns(10);
		
		frame.setVisible(true);
	}

	private void ajouterView(Plan plan) {
		this.view = new VueGraphique(plan, this);
		this.view.setBackground(Color.white);
		
//		view.setPreferredSize(new Dimension(1000,1000));
		
		scrollPanel = new JScrollPane(view); 

//		scrollPanel.setViewportView(view);
		
		frame.getContentPane().add(scrollPanel, "cell 1 1 1 11,grow");
	}
	
	public Dimension getSizeView() {
		return this.view.getSize();
	}

	public void setEchelle(double echelle) {
		this.view.setEchelle(echelle);	
	}
	
	public void ecrireLog(String texte) {
		log.setText(texte);
	}
	
	public void ecrireLog(String texte, Color color) {
		log.setForeground(color);
		log.setText(texte);
	}
	
	public void ecrireInfos(String texte) {
		infoPoint.setText(texte);
	}


	public void signalerErreur(String texteErreur){
		this.ecrireLog(texteErreur, ERROR_COLOR);
		JOptionPane.showMessageDialog(null,
				texteErreur,
				"Erreur",
				JOptionPane.ERROR_MESSAGE);
	}

	public boolean confirmerSuppression(){
		int result = JOptionPane.showConfirmDialog(null,
				"Etes vous sur de vouloir supprimer cette livraison",
				"Supprimer?",
				JOptionPane.CANCEL_OPTION);
		return result == 0;
	}

}
