import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class GUI extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JPanel drawarea;
	static SideMenu sidemenu;
	public JLabel message;
	public GUI() throws IOException{
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		sidemenu=new SideMenu();
		drawarea=new DrawArea();
		add(drawarea);
		add(sidemenu);
		message=new JLabel("X = 0"+"Y = 0");
		
		
		
	}
}
