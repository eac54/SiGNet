// Places the 'About SiGNet' menu item on the Tools.SiGnet menu and displays the 'About SiGNet' frame
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.cytoscape.app.CyAppAdapter;
import org.cytoscape.application.swing.AbstractCyAction;


@SuppressWarnings("serial")
public class MenuAction2 extends AbstractCyAction {
    @SuppressWarnings("unused")
	private final CyAppAdapter adapter;

    public MenuAction2(CyAppAdapter adapter) {
        super("About SiGNet",
            adapter.getCyApplicationManager(),
            "network",
            adapter.getCyNetworkViewManager());
        this.adapter = adapter;
        setPreferredMenu("Tools.SiGNet");
    }
 
    public void actionPerformed(ActionEvent e) {
    	JFrame frame = new JFrame("About SiGNet");
    	JPanel panel = new JPanel(); 
    	BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(new File("/Users/ecoker/Desktop/Signet pic.png"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			panel.add(picLabel);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	JLabel label2 = new JLabel("Please cite SiGNet as follows:");
    	panel.add(label2);
    	JLabel label3 = new JLabel("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
    	panel.add(label3);
    	panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    	frame.add(panel);
    	frame.pack();
    	frame.setVisible(true);
    	frame.setLocationRelativeTo(null);
    	panel.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
    }
       
}

