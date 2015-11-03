package vue;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;

import org.graphstream.ui.swingViewer.ViewPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfaceGraphique {

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
	private ViewPanel view;
	private JTextField log;
	private JLabel lblListeDesPoints;

	private final String TITLE = "Livraison Simulator 2015";
	private final Dimension MINIMUM_SIZE = new Dimension(800, 600);

	// private int nbr;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GraphManager.getInstance().create();
					GraphManager.getInstance().addNode("A", 0, 0);
					GraphManager.getInstance().addNode("B", 0, 3);
					GraphManager.getInstance().addNode("C", 3, 0);
					GraphManager.getInstance().addEdge("AB", "A", "B");
					GraphManager.getInstance().addEdge("AC", "A", "C");
					GraphManager.getInstance().addEdge("BC", "B", "C");
					InterfaceGraphique window = new InterfaceGraphique();
					window.linkView(GraphManager.getInstance().getView());
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InterfaceGraphique() {
		initialize();
		initializeButton();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// nbr = 0;
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
	
	private void initializeButton() {
		loadPlanning.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadPlanning();
			}
		});
		loadDeliveries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadDeliveries();
			}
		});
		computeTour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				computeTour();
			}
		});
		addDelivery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDelivery();
				// GraphManager.getInstance().addNode("" + nbr++);
			}
		});
		removeDelivery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removeDelivery();
			}
		});
		saveRoadMap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRoadMap();
			}
		});
	}

	public void linkView(ViewPanel view) {
		this.view = view;
		frame.getContentPane().add(view, "cell 1 1 1 11,grow");
	}
	
	private void loadPlanning() {
		// TODO Auto-generated method stub
		
	}
	
	private void loadDeliveries() {
		// TODO Auto-generated method stub
		
	}

	private void computeTour() {
		// TODO Auto-generated method stub
		
	}
	
	private void addDelivery() {
		// TODO Auto-generated method stub
		
	}
	
	private void removeDelivery() {
		// TODO Auto-generated method stub
		
	}
	
	private void saveRoadMap() {
		// TODO Auto-generated method stub
		
	}

}
