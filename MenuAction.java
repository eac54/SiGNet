
// Places the 'Generate SiGNet data from current network' menu items on the Tools.SiGnet menu and runs the algorithm according to user-specified paramteres

import java.awt.Color;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.xml.soap.Node;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.swing.AbstractCyAction;
import org.cytoscape.model.CyEdge;
import org.cytoscape.model.CyIdentifiable;
import org.cytoscape.model.CyNetwork;
import org.cytoscape.model.CyNode;
import org.cytoscape.model.CyTable;
import org.cytoscape.view.layout.CyLayoutAlgorithm;
import org.cytoscape.view.layout.CyLayoutAlgorithmManager;
import org.cytoscape.view.model.CyNetworkView;
import org.cytoscape.view.presentation.property.ArrowShapeVisualProperty;
import org.cytoscape.view.presentation.property.BasicVisualLexicon;
import org.cytoscape.view.vizmap.VisualMappingFunctionFactory;
import org.cytoscape.view.vizmap.VisualMappingManager;
import org.cytoscape.view.vizmap.VisualPropertyDependency;
import org.cytoscape.view.vizmap.VisualStyle;
import org.cytoscape.view.vizmap.VisualStyleFactory;
import org.cytoscape.view.vizmap.mappings.BoundaryRangeValues;
import org.cytoscape.view.vizmap.mappings.ContinuousMapping;
import org.cytoscape.view.vizmap.mappings.DiscreteMapping;
import org.cytoscape.view.vizmap.mappings.PassthroughMapping;
import org.cytoscape.work.TaskIterator;

import java.text.DecimalFormat;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class MenuAction extends AbstractCyAction implements ActionListener {
	private final CyAppAdapter adapter;
	// Places the menu action 'Generate SiGNet data from current network' on SigNet submenu, under Tools menu
	public MenuAction(CyAppAdapter adapter) {
		super("Generate SiGNet data from current network",
				adapter.getCyApplicationManager(),
				"network",
				adapter.getCyNetworkViewManager());
		this.adapter = adapter;
		setPreferredMenu("Tools.SiGNet");
	}

	//Describes what will happen when 'Generate SiGNet data from current network is selected'     
	public void actionPerformed(ActionEvent e) {
		
		// Create JFrame for building frame for pop up window
		final JFrame frame = new JFrame("Generate SiGNet Data from Current Network");
		JPanel panel = new JPanel();

		// User specifies number of 'experimental replicates' they want in a spinner. Default is 3, maximum is 1000, minimum is 1
		JLabel labela = new JLabel("Number of replicates:");
		SpinnerModel model1 = new SpinnerNumberModel(3, 1, 1000, 1);
		final JSpinner spinner1 = new JSpinner(model1);

		// User specifies how noisy they want the system to be using a second spinner- default is 0, max = 100, minimum = 0
		JLabel labelc = new JLabel("Noise percentage:");
		SpinnerModel model2 = new SpinnerNumberModel(0, 0, 100, 1);
		final JSpinner spinner2 = new JSpinner(model2);
		
		// User specifies how noisy they want the system to be using a second spinner- default is 0, max = 100, minimum = 0
		JLabel labele = new JLabel("Number of timepoints:");
		SpinnerModel model3 = new SpinnerNumberModel(1, 1, 10000, 1);
		final JSpinner spinner3 = new JSpinner(model3);

		//Adds 'Generate SiGNet Data' button to frame
		JButton buttonx = new JButton("Generate SiGNet Data");
		
		//Adds "Nodes for inhibition label and list
		JLabel labeld = new JLabel("Nodes for inhibition:");
				
		// Adds "Inhibition timepoint" label
		JLabel labeli = new JLabel("Timepoint for inhibition:");
		SpinnerModel model4 = new SpinnerNumberModel(1, 1, 10000, 1);
		final JSpinner spinner4 = new JSpinner(model4);
		
		//Adds "Permanent inhibition?" label and button
		final JCheckBox buttona = new JCheckBox("Permanent inhibition?", false);
		
		//Adds "Nodes for activation label and list
		JLabel labelg = new JLabel("Nodes for activation:");
				
		// Adds “Activation timepoint" label
		JLabel labelh = new JLabel("Timepoint for activation:");
		SpinnerModel model5 = new SpinnerNumberModel(1, 1, 10000, 1);
		final JSpinner spinner5 = new JSpinner(model5);
		
		//Adds "Permanent activation?" label and button
		final JCheckBox buttonb = new JCheckBox("Permanent activation?", false);
		
		//Adds "Include decay function?" label and button
		final JCheckBox buttonc = new JCheckBox("Include decay function?", false);
		
		// Adds "SiGNet exponent" label and spinner
		JLabel labelj = new JLabel("SiGNet exponent:");
		SpinnerModel model6 = new SpinnerNumberModel(0.15, 0.01, 1.00, 0.01);
		final JSpinner spinner6 = new JSpinner(model6);
		
				
		// Get properties of the current network displayed
		final CyNetwork network = adapter.getCyApplicationManager().getCurrentNetwork();
				final CyNetworkView myNetworkView = adapter.getCyApplicationManager().getCurrentNetworkView();
				DefaultListModel listModel=new DefaultListModel();
				final List<CyNode> nodeList3 = network.getNodeList();
				for (CyNode node : nodeList3){
					String myNodeName = network.getRow(node).get(CyNetwork.NAME, String.class);
					//System.out.println(myNodeName);
					listModel.addElement(myNodeName);
				}	
				final JList list = new JList (listModel);
				list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				list.setVisibleRowCount(10);
				
				DefaultListModel listModel2=new DefaultListModel();
				final List<CyNode> nodeList4 = network.getNodeList();
				for (CyNode node : nodeList4){
					String myNodeName = network.getRow(node).get(CyNetwork.NAME, String.class);
					//System.out.println(myNodeName);
					listModel2.addElement(myNodeName);
				}	
				final JList list2 = new JList (listModel2);
				list2.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				list2.setVisibleRowCount(10);
				JScrollPane listScrollPane = new JScrollPane(list);
				JScrollPane listScrollPane2 = new JScrollPane(list2);
				
					
		
			
				// Determines the layout of the panel
				GroupLayout layout = new GroupLayout(panel);
				panel.setLayout(layout);
				layout.setAutoCreateGaps(true);
				layout.setAutoCreateContainerGaps(true);
				layout.setHorizontalGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(labela)
								.addComponent(labelc)
								.addComponent(labele)
								.addComponent(labeld)
								.addComponent(labeli)
								.addComponent(labelg)
								.addComponent(labelh)
								.addComponent(labelj))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(spinner1)
										.addComponent(spinner2)
										.addComponent(spinner3)
										.addComponent(listScrollPane)
										.addComponent(spinner4)
										.addComponent(buttona)
										.addComponent(listScrollPane2)
										.addComponent(spinner5)
										.addComponent(buttonb)
										.addComponent(buttonc)
										.addComponent(spinner6)
										.addComponent(buttonx))
						);
				layout.setVerticalGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(labela)
								.addComponent(spinner1))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(labelc)
										.addComponent(spinner2))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(labele)
										.addComponent(spinner3))		
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(labeld)
												.addComponent(listScrollPane))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(labeli)
												.addComponent(spinner4))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(buttona))	
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(labelg)
												.addComponent(listScrollPane2))			
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(labelh)
												.addComponent(spinner5))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(buttonb))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(buttonc))
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(labelj)
												.addComponent(spinner6))				
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addComponent(buttonx))
						);

		// Set title for the frame and add the panel objects
		frame.setTitle("Generate SiGNet Data");
		frame.setLocationRelativeTo(null);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		
		// When frame is closed, make it disappear
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Create a listener to be added to buttonx
		ActionListener listener3 = new ActionListener() {

			private Object vmfFactoryC;

			//When button x is pressed, do the following:
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e)
			{

				//Actions when buttonx ("Generate SiGNet Data") is pressed
				frame.setVisible(true);
				frame.dispose();

				// Access the number of nodes present in the network, and return error message if less than two nodes present. Otherwise return number of nodes present     
				if (network.getNodeCount()<2){
					JFrame nullframe = new JFrame();
					JOptionPane nullPane = new JOptionPane();
					nullPane.showMessageDialog(nullframe, "Error - must be at least two nodes in network", "Error", nullPane.ERROR_MESSAGE);
					return;
				}
				
				// Access the number of edges present in the network, and return error message if less than one edge present. Otherwise return number of edges present     
				if (network.getEdgeCount() < 1) {
					JFrame edgeframe = new JFrame();
					JOptionPane edgePane = new JOptionPane();
					edgePane.showMessageDialog(edgeframe, "Error - no edges in network", "Error", edgePane.ERROR_MESSAGE); 
					return;
				}

				//If nodes are selected for inhibition and the timepoint for inhibition is larger than the number of time points specified, return an error message     
				int inhibitimepoint = (Integer) spinner4.getValue();
				int timepoint = (Integer) spinner3.getValue();
				List selected = list.getSelectedValuesList();
				int activetimepoint = (Integer) spinner5.getValue();
				List activeselected = list2.getSelectedValuesList();
				Object perminhib = buttona.getSelectedObjects();
				//System.out.println("Perm inhib " + perminhib);
				Object permactiv = buttonb.getSelectedObjects();
				//System.out.println("Perm activ " + permactiv);
				Object decay = buttonc.isSelected();
				//System.out.println("Decay function " + decay);
		
				if ((selected.isEmpty()==false) && (inhibitimepoint>timepoint)){
					JFrame edgeframe = new JFrame();
					JOptionPane edgePane = new JOptionPane();
					edgePane.showMessageDialog(edgeframe, "Error - nodes cannot be inhibited outside the range of timepoints selected", "Error", edgePane.ERROR_MESSAGE); 
					return;
				}

				if ((activeselected.isEmpty()==false) && (activetimepoint>timepoint)){
					JFrame edgeframe2 = new JFrame();
					JOptionPane edgePane2 = new JOptionPane();
					edgePane2.showMessageDialog(edgeframe2, "Error - nodes cannot be activated outside the range of timepoints selected", "Error", edgePane2.ERROR_MESSAGE); 
					return;
				}
				
				// error message for node both inhibited and activated at the same time point
				if ((activeselected.isEmpty()==false) && (selected.isEmpty()==false) && activetimepoint==inhibitimepoint){
					JFrame edgeframe3 = new JFrame();
					JOptionPane edgePane3 = new JOptionPane();
					edgePane3.showMessageDialog(edgeframe3, "Error - cannot have activation and inhibition at the same timepoint", "Error", edgePane3.ERROR_MESSAGE); 
					return;
				}


				
				// Access the interactions in the network and return an error message if interactions are not described using correct vocabulary 
				List<CyNode> nodeList2 = network.getNodeList();
				Map<String, Integer> exceptionsMap = new HashMap<String, Integer>();
				for (CyNode node2 : nodeList2){
					List <CyEdge> edgeList2 = network.getAdjacentEdgeList(node2, CyEdge.Type.ANY);
					for (CyEdge edge2: edgeList2){

						String interaction = network.getRow(edge2).get("interaction",String.class);
						//System.out.println(network.getRow(edge2).get("interaction",String.class));
						
						
						if (interaction == null) {
							interaction = network.getRow(edge2).get("name",String.class);
							//System.out.println("interaction/name = " + interaction);	
						}
						
						if (interaction.equals("binds")||interaction.equals("activates")||interaction.equals("strongly activates")||interaction.equals("weakly activates")||interaction.equals("strongly inhibits")||interaction.equals("weakly inhibits")||interaction.equals("inhibits")) {
						}
					
						else{
							network.getRow(edge2).set("interaction", "activates");
							if(exceptionsMap.containsKey(interaction))
							{
							exceptionsMap.put(interaction, exceptionsMap.get(interaction) + 1);
							}
							else
							{
							exceptionsMap.put(interaction, 1);
							}
							
						}

					}
				}
				
				if(! exceptionsMap.isEmpty())
				{
				StringBuffer exceptions = new StringBuffer();
				exceptions.append("Warning - network contains one or more interactions in the incorrect format.\n\n");
				Iterator<String> iter = exceptionsMap.keySet().iterator();
				while(iter.hasNext())
				{
				String exception = iter.next();
				exceptions.append("\t" + exception + "\tfound " + exceptionsMap.get(exception) + " time");
				if(exceptionsMap.get(exception) > 1)
				{
				exceptions.append("s");
				}
				exceptions.append("\n");
				}
				exceptions.append("\n" + "These have been replaced with 'activates'");
				JFrame edgeframe = new JFrame();
				JOptionPane edgePane = new JOptionPane();
				//edgePane.showMessageDialog(edgeframe, "Warning - network contains one or more interactions in the incorrect format." + "\r\n" + "These have been replaced with 'activates'", "Warning", edgePane.WARNING_MESSAGE); 
				edgePane.showMessageDialog(edgeframe, exceptions.toString(), "Warning", edgePane.WARNING_MESSAGE); 

				}
				//System.out.println("Time for the SiGNet algorithm!");
				//run SiGNet algorithm
				//for number of replicates specified by user
				//Number of replicates = r = value specified by spinner1
				
				// initiate number of replicates and noise levels using values from spinners
				int numberOfReplicates =  (Integer) spinner1.getValue();
				int noiseLevels = (Integer) spinner2.getValue();
				
				
				//Set value for resets
				double bottom = 0.0001;
				double top = 100-0.0001;
				
				//ensure all numbers are presented to four decimal places only
				DecimalFormat df = new DecimalFormat("#.####");
				
				// get the nodes selected by the user for inhibition
				//System.out.println(selected);
				//System.out.println(activeselected);
				
				for(int t=1; t<timepoint+1; t++){
					
					//Add a column to the node table named 'SiGNet value average'
					if (network.getDefaultNodeTable().getColumn("SiGNet value average timepoint " + t) == null) {
						network.getDefaultNodeTable().createColumn("SiGNet value average timepoint " + t, Double.class, false);
					}
						// for each replicate
						for(int i=1; i <numberOfReplicates+1; i++){
						
							// Add new column to the table of Node properties:
							if (network.getDefaultNodeTable().getColumn("SiGNet value repeat "+ i + " timepoint " + t) == null) {
								network.getDefaultNodeTable().createColumn("SiGNet value repeat "+ i + " timepoint " + t, Double.class, false);
							}
					
							// get a list of every node present in the network
							List<CyNode> nodeList = network.getNodeList();
							// for every node in the network
							for (CyNode node : nodeList){
								
								double nodevalue = 0;
						
								// get the name of the node
								String nodename = network.getRow(node).get("name", String.class);
								Double nodebaseline = network.getRow(node).get("Baseline value", Double.class);
								if (nodebaseline == null){
									nodebaseline = -100.0;
								}
								//System.out.println(nodename + " = " + nodebaseline);
								
								
								
								if (selected.contains(nodename) && perminhib!=null){
									nodevalue = Math.random() * 5;
								}
								else if (selected.contains(nodename) && perminhib==null){
									
									if (t<inhibitimepoint && t==1 && perminhib==null){
										//initialise values with 50
										nodevalue = 50 + Math.random();
										
									}
									
									else if (t<inhibitimepoint && t>=2 && perminhib==null){
										nodevalue=network.getRow(node).get("SiGNet value repeat " + i + " timepoint " + (t-1), Double.class);
										
									}
								
									else if (t==inhibitimepoint && perminhib==null){
										// if the node is in the selected list, initiate with a lower number
										//initialise values with random numbers between 0 and 5
										//System.out.println("Here");
										nodevalue = (Math.random() * 5);
										//System.out.println("There");
										
									}
									else if (t==inhibitimepoint+1 && perminhib==null){
										//initialise values with random numbers between 25 and 30
										nodevalue = 22.5+ (Math.random() * 5);
										//System.out.println("Plus 1");
										
									}
									else if (t==inhibitimepoint+2 && perminhib==null){
										//initialise values with random numbers between 25 and 30
										nodevalue =  50+ (Math.random() * 5);
										//System.out.println("Plus 2");
										
									}
									
									else{
										nodevalue=network.getRow(node).get("SiGNet value repeat " + i + " timepoint " + (t-1), Double.class);
									}
									
								}
								
								
								if (activeselected.contains(nodename) && permactiv!=null){
									nodevalue = 100- (Math.random() * 5);
								}
								else if (activeselected.contains(nodename) && permactiv==null){
								
									if (t<activetimepoint && t==1 && permactiv==null){
										//initialise values with 50
										nodevalue = 50 + Math.random();
										
									}
									
									else if (t<activetimepoint && t>=2 && permactiv==null){
										nodevalue=network.getRow(node).get("SiGNet value repeat " + i + " timepoint " + (t-1), Double.class);
										
									}
								
									else if (t==activetimepoint && permactiv==null){
										// if the node is in the selected list, initiate with a lower number
										//initialise values with random numbers between 95 and 100
										nodevalue = 100- (Math.random() * 5);
										
									}
									else if (t==activetimepoint+1 && permactiv==null){
										//initialise values with random numbers between 72.5 and 77.5
										nodevalue = 72.5+ (Math.random() * 5);
										
									}
									else if (t==activetimepoint+2 && permactiv==null){
										//initialise values with random numbers between 50 and 55
										nodevalue =  50 + (Math.random() * 5);
										
									}
									
									else{
										nodevalue=network.getRow(node).get("SiGNet value repeat " + i + " timepoint " + (t-1), Double.class);
									}
									
								}
								
								
								
								
								else {
									if (t==1){
										if (nodebaseline==-100){
											nodevalue = 50 + Math.random();
											System.out.println(node + "initial value = " + nodevalue);
										}
										//initialise values with 50
										else{
											nodevalue = nodebaseline;
											System.out.println(node + "initial value = " + nodevalue);
										}
										//System.out.println("initial value = " + network.getRow(node).get("SiGNet value repeat " + i + " timepoint " + (t), Double.class));
									}
									else {
										nodevalue=network.getRow(node).get("SiGNet value repeat " + i + " timepoint " + (t-1), Double.class);
									}
								}
								
								
								
						
							//get a list of edges for the node
							List <CyEdge> edgeList = network.getAdjacentEdgeList(node, CyEdge.Type.INCOMING);
						
							//System.out.println("node name = " + node);
							int numberOfInhibitoryEdges = 0;
							int numberOfActivatingEdges = 0;
							//for every edge in the input edge list
							for (CyEdge edge: edgeList){
								String interaction = network.getRow(edge).get("interaction",String.class);
								//System.out.println(network.getRow(edge).get("interaction",String.class));
								
								if (interaction == null) {
									interaction = network.getRow(edge).get("name",String.class);
									//System.out.println("interaction/name = " + interaction);	
								}
								
								
								CyNode sourcenodeID = edge.getSource();
								String sourcenodename =  network.getRow(sourcenodeID).get("name", String.class);
								//System.out.println("Source node ID = " + edge.getSource() + "Source node name =" + sourcenodename);
										
								Double sourcevalue=0d;
								if (t>1){
									sourcevalue = network.getRow(sourcenodeID).get("SiGNet value repeat " + i + " timepoint " + (t-1), Double.class);
								}
								if (t==1){
									if (selected.contains(sourcenodename)){
									sourcevalue = 5d;
									//System.out.println("Source node ID = " + edge.getSource() + "Source node name =" + sourcenodename);
									}
									else{
										sourcevalue = 50d;
									}
								}
								
								
								//System.out.println("Source value = " + network.getRow(sourcenodeID).get("SiGNet value repeat " + i + " timepoint " + (t-1), Double.class));
								
								//calculate activating inputs if the node value is larger than 50 
																
								if (interaction.matches("activates") && (sourcevalue>=50) && sourcevalue<90){
									numberOfActivatingEdges+=1.5;	
								}
								
								if (interaction.matches("strongly activates") &&(sourcevalue >=50) && sourcevalue<90){
									numberOfActivatingEdges+=2;
								}	
								
								if (interaction.matches("weakly activates") &&(sourcevalue >=50) && sourcevalue<90){
									numberOfActivatingEdges+=0.75;
								}
								
								if (interaction.matches("activates") && (sourcevalue>=90)){
									numberOfActivatingEdges+=3;	
								}
								
								if (interaction.matches("strongly activates") &&(sourcevalue >=90)){
									numberOfActivatingEdges+=4;
								}	
								
								if (interaction.matches("weakly activates") &&(sourcevalue >=90)){
									numberOfActivatingEdges+=1.5;
								}
								
								
								
								//System.out.println("node= " + node + "activating input= " + numberOfActivatingEdges);

								//calculate inhibitory inputs if the node value is larger than 50
								if (interaction.matches("inhibits") && (sourcevalue >=50) && sourcevalue<90){
									numberOfInhibitoryEdges+=1.5;
								}
								if (interaction.matches("strongly inhibits") && (sourcevalue >=50) && sourcevalue<90){
									numberOfInhibitoryEdges+=2;
								}
								if (interaction.matches("weakly inhibits") && (sourcevalue >=50) && sourcevalue<90){
									numberOfInhibitoryEdges+=0.75;
								}
								
								if (interaction.matches("inhibits") && (sourcevalue >=90)){
									numberOfInhibitoryEdges+=3;
								}
								if (interaction.matches("strongly inhibits") && (sourcevalue >=90)){
									numberOfInhibitoryEdges+=4;
								}
								if (interaction.matches("weakly inhibits") && (sourcevalue >=90)){
									numberOfInhibitoryEdges+=1.5;
								}
								
								
								//System.out.println("node= " + node + "inhibitory input= " + numberOfInhibitoryEdges);

								}
					
							
								//Define the signmoidal signal-response curve
								//Signal-response relationship is based on the sigmoid function
								//X = net input into node= signal. Can be negative
								Double exponent = (Double)spinner6.getValue();
								//System.out.println("exponent= " + exponent);
							
								//Plot of X and Y will be a sigmoidal curve with highest rate of change at (0,0)
								if ((((numberOfActivatingEdges>0) || (numberOfInhibitoryEdges>0))&&(decay.equals(false))) || (decay.equals(true)&&(t<0.5*timepoint)) ){
									double X = ( numberOfActivatingEdges - numberOfInhibitoryEdges );
									double Y = ((100/(1+Math.exp(-X*exponent)))-50);
									nodevalue = nodevalue + Y+ (Math.random());						
									//System.out.println("No decay here!");	
								}
								
								if ( (decay.equals(true)&&(t>=0.5*timepoint)) ){
									double X = Math.random() * 5;
									double Y = ((100/(1+Math.exp(X*exponent)))-50);
									nodevalue = nodevalue + Y;					
									//System.out.println("There's decay!");	
								}
								
								else {
									nodevalue = (nodevalue-5)+(Math.random() *10);
								}
								
								//System.out.println("node= " + node + "node value= " + nodevalue);	
								//System.out.println("iteration "+ i + "node= " + node + "node value= " + nodevalue);

							

								if (noiseLevels>0){
									//Noise multiplication
									//System.out.println("nodevalueprenoise"+nodevalue);
									
									//randomNoise is equal to a percentage
									double random = Math.random();
									//System.out.println("random= " + random);
									if (random >0.5){
										double q = ( 1 + (noiseLevels/100d));
										//System.out.println("noise/100 = " + noiseLevels/100d);
										nodevalue = nodevalue * q;
										//System.out.println("noise=" + noiseLevels + "q=" + q + "nodevalue=" + nodevalue);
										
									}
									else {
										double p = 1 - ((noiseLevels/100d));
										//System.out.println("noise/100 = " + noiseLevels/100d);
										nodevalue = nodevalue * p;
										//System.out.println("noise=" + noiseLevels + "p=" + p + "nodevalue=" + nodevalue);										
									}									
								}
								
								
							
								// if a node value becomes larger than 100, reset it to 100-0.0001
								if (nodevalue>100){
									nodevalue = top;
								}
								//if a node value becomes less than 0, reset it to 0.0001
								if (nodevalue<0){
									nodevalue = bottom;
								}
							
									
								//System.out.println("Node= " + node + "Repeat number= " + i + "Node value= " + nodevalue);
								nodevalue=Double.valueOf(df.format(nodevalue));
								network.getRow(node).set("SiGNet value repeat "+ i + " timepoint " + t, nodevalue);
							}
					}
					
						//get the list of nodes in the network
						List<CyNode> nodeList = network.getNodeList();
						// for every node in the network
						for (CyNode node : nodeList){
							//initiate a running total
							double runningtotal =0;
							// for each replicate
							for(int j=1; j <numberOfReplicates+1; j++){
								// add that node's value for that repeat to the running total
								runningtotal+=network.getRow(node).get("SiGNet value repeat " + j + " timepoint " + t, Double.class);
							}
							// calculate the average by dividing the running total by the number of replicates
							double nodeaverage=runningtotal/numberOfReplicates;
							//convert nodeaverage to a double
							nodeaverage=Double.valueOf(df.format(nodeaverage));
							//set the SiGNet value average with the node average value
							network.getRow(node).set("SiGNet value average timepoint " + t, nodeaverage);
						}
						
						 
				}
				
				
				

			
				// To get references to services in CyActivator class
				  VisualMappingManager vmmServiceRef = adapter.getVisualMappingManager();
				 
				  VisualStyleFactory visualStyleFactoryServiceRef = adapter.getVisualStyleFactory(); 
				  VisualMappingFunctionFactory vmfFactoryC = adapter.getVisualMappingFunctionContinuousFactory(); 
				  VisualMappingFunctionFactory vmfFactoryD = adapter.getVisualMappingFunctionDiscreteFactory(); 
				  VisualMappingFunctionFactory vmfFactoryP = adapter.getVisualMappingFunctionPassthroughFactory(); 
				  
				  
				  String signetVizmapName = "SiGNet visual style";
				  VisualStyle vs = null;
				  for(VisualStyle visualstyle : vmmServiceRef.getAllVisualStyles()) {
						if(signetVizmapName.equals(visualstyle.getTitle()))
							vs = visualstyle;
					}
				  

					if (vs == null) {
						vs = visualStyleFactoryServiceRef.createVisualStyle("SiGNet visual style"); 
					// Set node color map to attribute "SiGNet value average timepoint 1"
					ContinuousMapping<Double, Paint> mapping = (ContinuousMapping<Double, Paint>) vmfFactoryC.createVisualMappingFunction("SiGNet value average timepoint 1", Double.class, BasicVisualLexicon.NODE_FILL_COLOR);
					PassthroughMapping nodeLabel = 	(PassthroughMapping) vmfFactoryP.createVisualMappingFunction("name", String.class,BasicVisualLexicon.NODE_LABEL);
					DiscreteMapping edgeArrow = 	(DiscreteMapping) vmfFactoryD.createVisualMappingFunction("interaction",String.class, BasicVisualLexicon.EDGE_TARGET_ARROW_SHAPE);
					DiscreteMapping<String, Paint> edgeColour =  (DiscreteMapping) vmfFactoryD.createVisualMappingFunction("interaction", String.class, BasicVisualLexicon.EDGE_STROKE_UNSELECTED_PAINT); 
					  
					  // Define the points
					  Double val1 = 0d;
					  BoundaryRangeValues<Paint> brv1 = new BoundaryRangeValues<Paint>(Color.BLUE, Color.BLUE, Color.BLUE);
					  
					  Double val2 = 45d;
					  BoundaryRangeValues<Paint> brv2 = new BoundaryRangeValues<Paint>(Color.WHITE, Color.WHITE, Color.WHITE);
					  
					  Double val3 = 55d;
					  BoundaryRangeValues<Paint> brv3 = new BoundaryRangeValues<Paint>(Color.WHITE, Color.WHITE, Color.WHITE);
					  
					  Double val4 = 100d;
					  BoundaryRangeValues<Paint> brv4 = new BoundaryRangeValues<Paint>(Color.RED, Color.RED, Color.RED);
					  
					  
					  edgeArrow.putMapValue("pp", ArrowShapeVisualProperty.DIAMOND);
					  edgeArrow.putMapValue("activates", ArrowShapeVisualProperty.DELTA);
					  edgeArrow.putMapValue("strongly activates", ArrowShapeVisualProperty.DELTA);
					  edgeArrow.putMapValue("weakly activates", ArrowShapeVisualProperty.DELTA);
					  edgeArrow.putMapValue("weakly inhibits", ArrowShapeVisualProperty.T);
					  edgeArrow.putMapValue("strongly inhibits", ArrowShapeVisualProperty.T);
					  edgeArrow.putMapValue("inhibits", ArrowShapeVisualProperty.T);
					  edgeArrow.putMapValue("binds", ArrowShapeVisualProperty.CIRCLE);
					  
	
					  edgeColour.putMapValue("activates", Color.RED); 
					  edgeColour.putMapValue("strongly activates", Color.RED); 
					  edgeColour.putMapValue("weakly activates", Color.RED); 
					  edgeColour.putMapValue("weakly inhibits", Color.BLUE);
					  edgeColour.putMapValue("strongly inhibits", Color.BLUE); 
					  edgeColour.putMapValue("inhibits", Color.BLUE); 
					  edgeColour.putMapValue("binds", Color.BLACK);
					
					  
					  @SuppressWarnings("rawtypes")
					DiscreteMapping<String, Double> edgeThickness = 
					 (DiscreteMapping) vmfFactoryD.createVisualMappingFunction("interaction", String.class, BasicVisualLexicon.EDGE_WIDTH); 
					  Double defaultThickness = 2d;
					  edgeThickness.putMapValue("activates", defaultThickness); 
					  edgeThickness.putMapValue("strongly activates", defaultThickness * 2); 
					  edgeThickness.putMapValue("weakly activates", defaultThickness / 2); 
					  edgeThickness.putMapValue("weakly inhibits", defaultThickness/2); 
					  edgeThickness.putMapValue("strongly inhibits", defaultThickness * 2); 
					  edgeThickness.putMapValue("inhibits", defaultThickness); 
					  edgeThickness.putMapValue("binds", defaultThickness);
					  
							    
					  // Set the points
					  mapping.addPoint(val1, brv1);
					  mapping.addPoint(val2, brv2);
					  mapping.addPoint(val3, brv3);
					  mapping.addPoint(val4, brv4);
	
					  // add the mapping to visual style            
					  vs.addVisualMappingFunction(mapping); 
					  vs.addVisualMappingFunction(nodeLabel);
					  vs.addVisualMappingFunction(edgeArrow); 
					  vs.addVisualMappingFunction(edgeThickness); 
					  
					 
					  // Add the new style to the VisualMappingManager
					  vmmServiceRef.addVisualStyle(vs);
					  
	
					}
					  // Apply the visual style to a NetwokView
					  vs.apply(myNetworkView);
					  
					  CyLayoutAlgorithmManager alMan = adapter.getCyLayoutAlgorithmManager();
					 

					  CyLayoutAlgorithm algor = alMan.getLayout("kamada-kawai");

		               if (algor == null) {

		                               algor = alMan.getDefaultLayout();

		               }
		               
		              

		               TaskIterator itr = algor.createTaskIterator(myNetworkView,algor.createLayoutContext(),CyLayoutAlgorithm.ALL_NODE_VIEWS, null);

		               // NOTE: layout is performed in another thread and this may return before layout is complete:

		               adapter.getTaskManager().execute(itr);
					  
					  
					  myNetworkView.updateView();
					
				
				// Pop up box explaining to user how to access/export data	
				JFrame nullframe = new JFrame();
				JOptionPane result = new JOptionPane();
				JOptionPane.showMessageDialog(nullframe, "To access the data, click 'File', 'Export', then 'Table', and select the option for exporting the default node table." + "\r\n" + "You can then select the format and file path in which your data will be saved.", "SiGNet data generated", result.PLAIN_MESSAGE);	

			};
		
		};
		buttonx.addActionListener(listener3);        	      
	}}
	



