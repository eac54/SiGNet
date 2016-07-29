
import org.cytoscape.app.swing.AbstractCySwingApp;
import org.cytoscape.app.swing.CySwingAppAdapter;


public class SiGNetApp extends AbstractCySwingApp 
{
	public SiGNetApp (CySwingAppAdapter adapter)
	{
		super(adapter);
		adapter.getCySwingApplication()
        .addAction(new MenuAction(adapter));
		adapter.getCySwingApplication()
        .addAction(new MenuAction2(adapter));	
	}
	
	public String describe() {
		StringBuffer sb = new StringBuffer();
		return sb.toString();
	}
	
}