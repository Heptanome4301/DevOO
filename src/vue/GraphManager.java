package vue;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;


public class GraphManager {
	
	/**
	 * Attributes
	 */
	private final static GraphManager INSTANCE = new GraphManager();
	private Graph graph;
	private Viewer viewer;
	private ViewPanel view;
	
	/**
	 * Constructor (private -> Singleton design pattern)
	 */
	private GraphManager(){}
	
	/**
	 * Method used to get an instance instead of constructor
	 * @return An instance of this class
	 */
	public static GraphManager getInstance()
	{	
		return INSTANCE;
	}
	
	/**
	 * Method used to create the graph
	 */
	public void create()
	{
		initialize();
	}
	
	/**
	 * Method used to add a node to the graph
	 * @param id Node's name
	 */
	public void addNode(String id)
	{
		graph.addNode(id);
	}
	
	/**
	 * Method used to add a node to the graph
	 * @param id Node's name
	 * @param coordX Abscissa position
	 * @param coordY Ordinate position
	 */
	public void addNode(String id, int coordX, int coordY)
	{
		Node node = graph.addNode(id);
		node.setAttribute("xy", coordX, coordY);
	}
	
	/**
	 * Method used to remove a node from the graph
	 * @param id Node's name
	 */
	public void removeNode(String id)
	{
		graph.removeNode(id);
	}
	
	/**
	 * Method used to add an edge to the graph
	 * @param id Edge's name
	 * @param idNode1 First node's name
	 * @param idNode2 Second node's name
	 */
	public void addEdge(String id, String idNode1, String idNode2)
	{
		graph.addEdge(id, idNode1, idNode2);
	}
	
	/**
	 * Method used to remove an edge from the graph
	 * @param id Edge's name
	 */
	public void removeEdge(String id)
	{
		graph.removeEdge(id);
	}
	
	/**
	 * Method used to get the view
	 * @return view
	 */
	public ViewPanel getView()
	{
		return view;
	}
	
	private void initialize() 
	{
		graph = new DefaultGraph("default");
		
		this.viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
		
		this.viewer.enableAutoLayout();

		this.view = viewer.addDefaultView(false);
		
        this.view.getCamera().setAutoFitView(true);
	}
}
