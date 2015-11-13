package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

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
	private JButton annuler;
	private JButton refaire;
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

	/**
	 * Constructeur de Fenetre.
	 */
	public Fenetre(Controleur c, Plan p) {
		initialize();
		ajouterView(p);
		ajouterViewTextuelle(c);
		initializeListeners(c);

	}

	/**
	 * Ajoute la vue textuelle à la fenetre
	 * @param c le controleur qui fourni la vue textuelle
     */
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
		annuler.addActionListener(ecouteurBoutons);
		refaire.addActionListener(ecouteurBoutons);

		vue.addMouseListener(ecouteurSouris);

		// zoom.addChangeListener(new ZoomListener(view));
	}

	/**
	 * initialise le contenu de la fenêtre.
	 */
	private void initialize() {

		frame = new JFrame(this.TITLE);
		frame.setMinimumSize(this.MINIMUM_SIZE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new MigLayout("", "[20%,grow][60%,grow][20%][10%]",
						"[22.00][][][][][][][][][][grow][15%][]"));

		scrollPanel = new JScrollPane(vue);
		scrollPanel.setBorder(new EmptyBorder(Constants.MARGIN_VUE_GRAPHE,
				Constants.MARGIN_VUE_GRAPHE, Constants.MARGIN_VUE_GRAPHE,
				Constants.MARGIN_VUE_GRAPHE));
		frame.getContentPane().add(scrollPanel, "cell 1 1 1 11,grow");

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

		etiquetteTournee = new JLabel("Créer une tournée");
		etiquetteTournee.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(etiquetteTournee, "cell 2 0 2 1,growx");

		chargerPlan = new JButton(Constants.CHARGER_PLAN);
		frame.getContentPane().add(chargerPlan, "cell 2 1 2 1,growx");

		chargerLivraisons = new JButton(Constants.CHARGER_LIVRAISONS);
		frame.getContentPane().add(chargerLivraisons, "cell 2 2 2 1,growx");

		calculerTournee = new JButton(Constants.CALCULER_TOURNEE);
		frame.getContentPane().add(calculerTournee, "cell 2 3 2 1,growx");

		etiquetteLivraisons = new JLabel("Modifier une tournée");
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

		annuler = new JButton(Constants.ANNULER);
		frame.getContentPane().add(annuler, "cell 2 10 2 1,growx");

		refaire = new JButton(Constants.REFAIRE);
		frame.getContentPane().add(refaire, "cell 2 10 2 1,growx");

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

	/**
	 * Ajoute le plan à la fenetre
	 * @param plan le plan à visualiser
     */
	private void ajouterView(Plan plan) {
		this.vue = new VueGraphique(plan, this);

		// view.setPreferredSize(new Dimension(1000,1000));

		scrollPanel.setViewportView(vue);

		// scrollPanel.setViewportView(view);
	}

	/**
	 * Getter de la vue Graphique
	 * @return la vue graphique
     */
	public VueGraphique getVue() {
		return vue;
	}

	/**
	 * Setter du parametre de l'echelle du plan
	 * @param echelle l'echelle appliquée au plan
     */
	public void setEchelle(double echelle) {
		this.vue.setEchelle(echelle);
	}

	/**
	 * Ecrit le texte dans la barre de log en bas de la fenêtre
	 * @param texte le message à afficher
     */
	public void ecrireLog(String texte) {
		log.setForeground(INFO_COLOR);
		log.setText(texte);
	}

	/**
	 * Ecrit le texte dans la barre de log en bas de la fenêtre
	 * @param texte le message à afficher
	 * @param color la couleur du texte
     */
	private void ecrireLog(String texte, Color color) {
		log.setForeground(color);
		log.setText(texte);
	}

	/**
	 * Ecrit les infos concernant un noeud dans la zone dédiée
	 * @param texte Le texte à afficher
     */
	public void ecrireInfos(String texte) {
		infoPoint.setText(texte);
	}

	/**
	 * Signale une erreur dans la vue
	 * @param texteErreur le message d'erreur
     */
	public void signalerErreur(String texteErreur) {
		this.ecrireLog(texteErreur, ERROR_COLOR);
		JOptionPane.showMessageDialog(null, texteErreur, "Erreur",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Demande à l'utilisateur de confirmer la suppression d'un noeud
	 * @return le choix de l'utilisateur
     */
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
		vue.repaint();
	}

	private void updateSelectionTextuelle(Adresse adresse) {
		if (adresse != null && adresse.estAssocierAvecLivraison()) {
			vueTextuelle.selectionnerList(adresse.getLivraison());
		} else {
			vueTextuelle.deSelectionList();
		}
		vue.repaint();
	}

	/**
	 * Met à jour l'adresse sélectionnée sur la carte
	 * @param adresse L'adresse selectionnée
	 * @param updateList précise si la selection dans la liste doit être mise à jour
     */
	public void updateSelection(Adresse adresse, boolean updateList) {
		if (updateList)
			updateSelectionTextuelle(adresse);
		updateSelectionGraphique(adresse);
		vue.repaint();
	}

	private void setActiverBuotonsModification(boolean enable) {
		supprimerLivraison.setEnabled(enable);
		echangerLivraisons.setEnabled(false); //fixme La fonction echanger ne fonctionne pas
		ajouterLivraison.setEnabled(enable);
		deplacerLivraison.setEnabled(enable);
		sauvegardeFeuilleRoute.setEnabled(enable);
		annuler.setEnabled(enable);
		refaire.setEnabled(enable);
	}


	/**
	 * Active les boutons de modification de la tournée
	 */
	public void activerBuotonsModification() {
		setActiverBuotonsModification(true);
	}

	/**
	 * Desactive les boutons de modification de la tournée
	 */
	public void desactiverBuotonsModification() {
		setActiverBuotonsModification(false);
	}

	/**
	 * met à jour la vue
	 */
	public void update() {
		// System.out.println(vue.echelle);
		// System.out.println("Vue x : " + vue.getSize().width + " et y : " +
		// vue.getSize().height);
		// System.out.println("ScrollPanel x : " + scrollPanel.getSize().width +
		// " et y : " + scrollPanel.getSize().height);
		vue.setPreferredSize(new Dimension(
				(int) (vue.getSize().width * vue.echelle),
				(int) (vue.getSize().height * vue.echelle)));
		vue.repaint();
		// vue.setVisible(true);
		scrollPanel.revalidate();
	}

	public void moveEcran(int dx, int dy) {
		vue.moveEcran(dx, dy);
		update();
	}

}
