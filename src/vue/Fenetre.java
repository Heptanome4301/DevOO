package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.*;

import controleur.Controleur;
import modele.Adresse;
import modele.Livraison;
import modele.Plan;
import net.miginfocom.swing.MigLayout;
import util.Constants;

public class Fenetre {

	public JFrame frame;
	private JTextArea infoPoint;
	private JButton chargerPlan;
	private JButton chargerLivraisons;
	private JButton calculerTournee;
	private JButton ajouterLivraison;
	private JButton supprimerLivraison;
	private JButton echangerLivraisons;
        private JButton deplacerLivraison;
	private JButton sauvegardeFeuilleRoute;
	private JLabel etiquetteLivraisons;
	private JLabel etiquetteTournee;
	private VueGraphique vue;
	private VueTextuelle vueTextuelle;
	private JScrollPane scrollPanel;
	private JTextField log;
	private JLabel etiquetteListePoints;

	private EcouteurDeBoutons ecouteurBoutons;
	private EcouteurDeSouris ecouteurSouris;
	private EcouteurDeListe ecouteurListe;

	private final String TITLE = "Livraison Simulator 2015";
	private final Dimension MINIMUM_SIZE = new Dimension(800, 600);
	private final Color ERROR_COLOR = Color.RED;
	private final Color INFO_COLOR = Color.BLACK;
	private JSlider zoom;
	private JLabel lblZoom;

	/**
	 * Create the application.
	 */
	public Fenetre(Controleur c, Plan p) {
		initialize();
		ajouterView(p);
		ajouterViewTextuelle(c);
		initializeListeners(c);

	}

	private void ajouterViewTextuelle(Controleur c) {
		JList<Livraison> listAdressesLivraisons = new JList<Livraison>();
		vueTextuelle = new VueTextuelle(c, listAdressesLivraisons);
		// listAdressesLivraisons.addListSelectionListener(ecouteurListe);
		frame.getContentPane()
				.add(listAdressesLivraisons, "cell 0 1 1 10,grow");
	}

	private void initializeListeners(Controleur c) {
		this.ecouteurBoutons = new EcouteurDeBoutons(c);
		this.ecouteurSouris = new EcouteurDeSouris(c, vue);
		this.ecouteurListe = new EcouteurDeListe(vueTextuelle);

		chargerPlan.addActionListener(ecouteurBoutons);
		chargerLivraisons.addActionListener(ecouteurBoutons);
		calculerTournee.addActionListener(ecouteurBoutons);
		ajouterLivraison.addActionListener(ecouteurBoutons);
		supprimerLivraison.addActionListener(ecouteurBoutons);
		echangerLivraisons.addActionListener(ecouteurBoutons);
                deplacerLivraison.addActionListener(ecouteurBoutons);
		sauvegardeFeuilleRoute.addActionListener(ecouteurBoutons);

		vue.addMouseListener(ecouteurSouris);

		// zoom.addChangeListener(new ZoomListener(view));
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

		etiquetteListePoints = new JLabel("Liste des points de livraison");
		frame.getContentPane().add(etiquetteListePoints,
				"cell 0 0,alignx center,aligny center");

		// lblZoom = new JLabel("Zoom");
		// frame.getContentPane().add(lblZoom, "flowx,cell 1 0,alignx right");
		//
		// zoom = new JSlider();
		// zoom.setValue(100);
		// zoom.setMinimum(10);
		// zoom.setMaximum(200);
		// zoom.setPaintLabels(true);
		//
		// frame.getContentPane().add(zoom, "cell 1 0,alignx right");

		// listAdressesLivraisons = new JList<Livraison>();
		// frame.getContentPane()
		// .add(listAdressesLivraisons, "cell 0 1 1 10,grow");

		etiquetteTournee = new JLabel("Cr�er une tourn�e");
		etiquetteTournee.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(etiquetteTournee, "cell 2 0 2 1,growx");

		chargerPlan = new JButton(Constants.CHARGER_PLAN);
		frame.getContentPane().add(chargerPlan, "cell 2 1 2 1,growx");

		chargerLivraisons = new JButton(Constants.CHARGER_LIVRAISONS);
		frame.getContentPane().add(chargerLivraisons, "cell 2 2 2 1,growx");

		calculerTournee = new JButton(Constants.CALCULER_TOURNEE);
		frame.getContentPane().add(calculerTournee, "cell 2 3 2 1,growx");

		etiquetteLivraisons = new JLabel("Modifier une tourn�e");
		etiquetteLivraisons.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(etiquetteLivraisons, "cell 2 5 2 1,growx");

		ajouterLivraison = new JButton(Constants.AJOUTER_LIVRAISONS);
		frame.getContentPane().add(ajouterLivraison, "cell 2 6 2 1,growx");

		supprimerLivraison = new JButton(Constants.SUPPRIMER_LIVRAISON);
		frame.getContentPane().add(supprimerLivraison, "cell 2 7 2 1,growx");

		echangerLivraisons = new JButton(Constants.INVERSER_LIVRAISONS);
		frame.getContentPane().add(echangerLivraisons, "cell 2 8 2 1,growx");
                
                deplacerLivraison = new JButton(Constants.DEPLACER_LIVRAISON);
                frame.getContentPane().add(deplacerLivraison, "cell 2 9 2 1,growx");

		infoPoint = new JTextArea();
		infoPoint.setMargin(new Insets(Constants.MARGIN_TEXTE_PANEL,
				Constants.MARGIN_TEXTE_PANEL, Constants.MARGIN_TEXTE_PANEL,
				Constants.MARGIN_TEXTE_PANEL));
		frame.getContentPane().add(infoPoint, "cell 0 11,grow");

		sauvegardeFeuilleRoute = new JButton(
				Constants.SAUVEGARDER_FEUILLE_DE_ROUTE);
		frame.getContentPane().add(sauvegardeFeuilleRoute,
				"cell 2 11 2 1,growx");

		log = new JTextField();
		log.setEditable(false);
		frame.getContentPane().add(log, "cell 0 12 4 1,growx");
		log.setColumns(10);

		desactiverBuotonsModification();
		frame.setVisible(true);
	}

	private void ajouterView(Plan plan) {
		this.vue = new VueGraphique(plan, this);

		// view.setPreferredSize(new Dimension(1000,1000));

		scrollPanel = new JScrollPane(vue);

		// scrollPanel.setViewportView(view);

		frame.getContentPane().add(scrollPanel, "cell 1 1 1 11,grow");
	}

	public VueGraphique getVue() {
		return vue;
	}

	public void setEchelle(double echelle) {
		this.vue.setEchelle(echelle);
	}

	public void ecrireLog(String texte) {
		log.setForeground(INFO_COLOR);
		log.setText(texte);
	}

	private void ecrireLog(String texte, Color color) {
		log.setForeground(color);
		log.setText(texte);
	}

	public void ecrireInfos(String texte) {
		infoPoint.setText(texte);
	}

	public void signalerErreur(String texteErreur) {
		this.ecrireLog(texteErreur, ERROR_COLOR);
		JOptionPane.showMessageDialog(null, texteErreur, "Erreur",
				JOptionPane.ERROR_MESSAGE);
	}

	public boolean confirmerSuppression() {
		int result = JOptionPane.showConfirmDialog(null,
				"Etes vous sur de vouloir supprimer cette livraison",
				"Supprimer?", JOptionPane.CANCEL_OPTION);
		return result == 0;
	}


	private void updateSelectionGraphique(Adresse adresse) {
		if (adresse == null) {
			vue.deselection();
			ecrireInfos("");
		} else {
			vue.selection(adresse.getId());
			ecrireInfos(adresse.toString());
		}
	}

	private void updateSelectionTextuelle(Adresse adresse) {
		if (adresse != null && adresse.estAssocierAvecLivraison()) {
			vueTextuelle.selectionnerList(adresse.getLivraison());
		} else {
			vueTextuelle.deSelectionList();
		}

	}

	public void updateSelection(Adresse adresse, boolean updateList) {
		if( updateList) updateSelectionTextuelle(adresse);
		updateSelectionGraphique(adresse);
	}

	private void setActiverBuotonsModification(boolean enable) {
		supprimerLivraison.setEnabled(enable);
		echangerLivraisons.setEnabled(enable);
		ajouterLivraison.setEnabled(enable);
                deplacerLivraison.setEnabled(enable);
		sauvegardeFeuilleRoute.setEnabled(enable);
	}

	public void activerBuotonsModification() {
		setActiverBuotonsModification(true);
	}

	public void desactiverBuotonsModification() {
		setActiverBuotonsModification(false);
	}

}
