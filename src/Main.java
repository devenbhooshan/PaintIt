import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;



public class Main extends JFrame {
	
	private static final long serialVersionUID = -3718217240256565435L;

	public Main() throws IOException{
		Menu();
		canvasarea();
		init();
	}
	
	
	public void canvasarea() throws IOException{
		GUI gui=new GUI();
		add(gui);
		
	}
	
	public void init(){
		setTitle("GuruDev Paint");
		setSize(1200, 640);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(100, 0);
		setResizable(false);
		setVisible(true);	
	}
	public void Menu(){
		JMenuBar menubar=new JMenuBar();
		JMenu file=new JMenu("File");
		JMenu tools=new JMenu("Tools");
		tools.setMnemonic(KeyEvent.VK_T);		
		file.setMnemonic(KeyEvent.VK_F);
		JMenuItem emenuitem=new JMenuItem("Exit");
		emenuitem.setMnemonic(KeyEvent.VK_F);
		emenuitem.setToolTipText("Exit ");
		emenuitem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		JMenuItem newitem=new JMenuItem("New");
		newitem.setMnemonic(KeyEvent.VK_N);
		newitem.setToolTipText("New");
		newitem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//DrawArea.cache=new BufferedImage(DrawArea.cache.getWidth(),DrawArea.cache.getHeight() , BufferedImage.TYPE_3BYTE_BGR);
			}
		});
		//file.add(newitem);
		file.add(emenuitem);
		menubar.add(file);
	//	menubar.add(tools);
		setJMenuBar(menubar);
	}
	
	public static void main(String args[]) throws IOException{
		new Main();
	}
	
	
}