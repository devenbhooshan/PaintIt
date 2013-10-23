import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class SideMenu extends JPanel implements MouseListener,ItemListener,ActionListener,ChangeListener{

	private static  int draw_tool=0 ; 
	private static String text;
	private static int pencil_size=2;
	private static Color colors[]={Color.BLACK,Color.BLUE,Color.CYAN,Color.DARK_GRAY,Color.GRAY,Color.GREEN,Color.LIGHT_GRAY,Color.MAGENTA,Color.ORANGE,Color.PINK,Color.RED,Color.WHITE,Color.YELLOW};
	private static Color for_color=colors[0];
	private JPanel colorcooser=new JPanel();
	private static int font;
	private static int fontSize=15;
	SideMenu() throws IOException {

		setPreferredSize(new Dimension(200,100));

		/*
		 * Color Picker Starts
		 */
		colorcooser.setPreferredSize(new Dimension(50, 50));
		colorcooser.addMouseListener(this);
		colorcooser.setName("C"+colors.length);
		colorcooser.setBorder(BorderFactory.createLineBorder(Color.pink,4));
		colorcooser.setBackground(Color.black);
		JPanel color=new JPanel(new GridLayout(1,1));
		JPanel foregroundColor=new JPanel(new GridLayout(3,1,5,5));
		foregroundColor.add(colorcooser);
		JLabel for_color_label=new JLabel("Foreground");
		for_color_label.setFont(new Font("Dialog",Font.PLAIN,20));
		foregroundColor.setBorder(BorderFactory.createLineBorder(Color.black,1));
		JPanel fore_color_panel[]=new JPanel[colors.length];		
		for (int i = 0; i < fore_color_panel.length; i++) {

			fore_color_panel[i]=new JPanel();
			fore_color_panel[i].setPreferredSize(new Dimension(50, 50));
			fore_color_panel[i].setBackground(colors[i]);
			fore_color_panel[i].addMouseListener(this);
			fore_color_panel[i].setName("F"+Integer.toString(i));
			foregroundColor.add(fore_color_panel[i]);
		}
		//color.add(for_color_label);

		color.add(foregroundColor);

		add(color);	
		/*
		 * 	Color Picker Ends
		 * 	Tool Picker Starts
		 */

		String tool_names[]={"pencil","line-tool","rectangle","oval","polygon","eraser","text","rectangle_fill","oval_fill","polygon_fill","bucket"};
		JPanel tool=new  JPanel(new GridLayout(2,1));
		JPanel tool_panel=new JPanel(new GridLayout(2,1));
		JPanel tools[]=new JPanel[tool_names.length];
		for (int i = 0; i < tools.length; i++) {
			tools[i]=new JPanel();
			BufferedImage myPicture = ImageIO.read(new File("images/"+tool_names[i]+".png"));
			Image dimg = myPicture.getScaledInstance(45, 45,
					Image.SCALE_SMOOTH);
			JLabel picLabel = new JLabel(new ImageIcon(dimg));
			picLabel.setSize(10, 10);
			tools[i].add(picLabel,"Center");
			tools[i].setName("T"+Integer.toString(i));
			tools[i].addMouseListener(this);
			tools[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			tool_panel.add(tools[i]);
		}
		JLabel pick_tools=new JLabel("Pick Tools");
		pick_tools.setFont(new Font("Dialog",Font.PLAIN,20));
		//tool.add(pick_tools);
		tool.add(tool_panel);


		//add(pick_tools,JLabel.CENTER);
		add(tool);

		/*
		 * Tool picker Ends
		 * Pencil Size Chooser starts		
		 */
		JLabel sliderLabel = new JLabel("Stroke Size", JLabel.CENTER);

		JSlider stroke_size=new JSlider(JSlider.HORIZONTAL,1,20,2);
		add(sliderLabel);
		add(stroke_size);
		stroke_size.addChangeListener(this);
		stroke_size.setMajorTickSpacing(19);
		stroke_size.setMinorTickSpacing(1);
		stroke_size.setPaintTicks(true);
		stroke_size.setPaintLabels(true);
		//stroke_size.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		Font font = new Font("Serif", Font.ITALIC, 15);
		stroke_size.setFont(font);

		/*
		 * Pencil Size Chooser Ends
		 * 		Import Export
		 */
		JPanel i_e=new JPanel();
		BufferedImage myPicture = ImageIO.read(new File("images/Save.png"));
		Image dimg = myPicture.getScaledInstance(50, 50,
				Image.SCALE_SMOOTH);
		JLabel picLabel = new JLabel(new ImageIcon(dimg));
		picLabel.setSize(10, 10);
		picLabel.addMouseListener(this);
		picLabel.setName("SAVE");
		i_e.add(picLabel);
		myPicture = ImageIO.read(new File("images/upload.jpeg"));
		dimg = myPicture.getScaledInstance(50, 50,
				Image.SCALE_SMOOTH);
		JLabel upload = new JLabel(new ImageIcon(dimg));
		upload.setSize(10, 10);
		upload.addMouseListener(this);
		upload.setName("UPLOAD");
		//i_e.add(upload);
		add(i_e);

	}

	private void changeForeColor(int color){
		for_color=colors[color];
		System.out.println("Color Changed :"+color);
		colorcooser.setBackground(for_color);
	}
	private void changeTool(int tool) {
		if(tool==5) changeForeColor(11); // Erase Tool
		draw_tool=tool;
		System.out.println("Tool Changed "+tool);
		if(tool==6 ) {  // Text 
			GraphicsEnvironment ge = GraphicsEnvironment.
					getLocalGraphicsEnvironment();
			final String[] fonts = ge.getAvailableFontFamilyNames();
			final String fontSize[]={"6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
			final JComboBox fontSizeChooser = new JComboBox(fontSize);
			final JComboBox fontChooser = new JComboBox(fonts);
			fontSizeChooser.setSelectedIndex(9);
			final JTextArea textArea=new JTextArea(4,10);
			fontSizeChooser.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					SideMenu.fontSize=Integer.parseInt((String) fontSizeChooser.getSelectedItem());
					textArea.setFont(new Font(fonts[SideMenu.font], Font.PLAIN, SideMenu.fontSize));
				}
			});
			fontChooser.setRenderer(new FontCellRenderer());
			
			JPanel pan=new JPanel(new GridLayout(3,1));
			
			textArea.setText("Your Text Here");
			pan.add(textArea);
			
			pan.add(fontSizeChooser);
			pan.add(fontChooser);
			fontChooser.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//System.out.println(fontChooser.getSelectedIndex());
					textArea.setFont(new Font(fonts[fontChooser.getSelectedIndex()], Font.PLAIN, SideMenu.fontSize));
					font=fontChooser.getSelectedIndex();
				}
			});
			textArea.setCursor(new Cursor(Cursor.TEXT_CURSOR));
			int okCxl = JOptionPane.showConfirmDialog(SwingUtilities.getWindowAncestor(this),
					pan,
					"Enter Text",
					JOptionPane.OK_CANCEL_OPTION);
			if (okCxl == JOptionPane.OK_OPTION) {
				text = textArea.getText();

			}


		}

	}
	@Override
	public void mouseClicked(MouseEvent ev) {

		if(ev.getComponent().getName()!=null && ev.getComponent().getName().charAt(0)=='F') // foreground color
		{
			changeForeColor(Integer.parseInt(ev.getComponent().getName().substring(1))); }
		else if(ev.getComponent().getName()!=null && ev.getComponent().getName().charAt(0)=='T') // Tools color
			changeTool(Integer.parseInt(ev.getComponent().getName().substring(1)));
		else if(ev.getComponent().getName()!=null && ev.getComponent().getName().charAt(0)=='P') // Pencil color
			;
		else if(ev.getComponent().getName()!=null && ev.getComponent().getName().charAt(0)=='S'){
			saveImage();
		}
		else if(ev.getComponent().getName()!=null && ev.getComponent().getName().charAt(0)=='U'){
			uploadImage();
		}
		else if(ev.getComponent().getName()!=null && ev.getComponent().getName().charAt(0)=='C'){
			colorchooser();
		}
	}

	private void colorchooser() {

		for_color=JColorChooser.showDialog(this, "Select a color", for_color);
		colorcooser.setBackground(for_color) ;
		System.out.println("Color changed");
	}

	private void uploadImage() {
		/*	BufferedImage myPicture = ImageIO.read(new File("path-to-file"));
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		add(picLabel);
		 */
	}

	private void saveImage() {

		try {
			JFileChooser jf=new JFileChooser();
			File file = new File("/deven.png");
			jf.setCurrentDirectory(file);
			int actionDialog = jf.showSaveDialog(this);

			if ( actionDialog == JFileChooser.APPROVE_OPTION )
			{

				File fileName = new File( jf.getSelectedFile( ) + ".png" );
				if(fileName == null)
					return;
				if(fileName.exists())
				{
					actionDialog = JOptionPane.showConfirmDialog(this,
							"Replace existing file?");
					// may need to check for cancel option as well
					if (actionDialog == JOptionPane.NO_OPTION)
						return;
				}	

				ImageIO.write(DrawArea.cache, "png",new File(jf.getSelectedFile( ) + ".png"));
				System.out.println("File Saved");

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	public static int getSelectedFont(){
		return font;
	}
	public static Color getSelectedForeColor(){
		return for_color;
	}
	public static String getInputText(){
		return text;
	}
	public static int getSelectedTool(){
		return draw_tool;
	}
	public static int getStrokeSize(){
		return pencil_size;
	}
	public static void setForeColor(Color c){
		for_color=c;
	}
	public static int getFontSize(){
		return fontSize;
	}

	@Override public void mouseEntered(MouseEvent arg0) {}
	@Override public void mouseExited(MouseEvent arg0) {}
	@Override public void mousePressed(MouseEvent ev) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent ev) {


	}

	@Override
	public void stateChanged(ChangeEvent e) {

		JSlider slider=(JSlider)e.getSource();
		System.out.println("Stroke Size Changed"+slider.getValue());
		pencil_size=slider.getValue();

	}

}
class FontCellRenderer extends DefaultListCellRenderer {

	public Component getListCellRendererComponent(
			JList list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {
		JLabel label = (JLabel)super.getListCellRendererComponent(
				list,value,index,isSelected,cellHasFocus);
		Font font = new Font((String)value, Font.PLAIN, 20);
		label.setFont(font);
		return label;
	}
}