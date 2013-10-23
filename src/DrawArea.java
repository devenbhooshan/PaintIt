import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class DrawArea extends JPanel implements MouseListener,MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	public int x1,x2,y1,y2;
	public  boolean ispressed=false;
	public  boolean isdragged=false;
	static BufferedImage cache;
	DrawArea(){
		//setLayout(new BorderLayout());
		setBackground(Color.white);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(700,100));
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
	}
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		if(cache==null){
			int w = this.getWidth();
            int h = this.getHeight()+100;
            cache = (BufferedImage)this.createImage(w, h);
            Graphics2D gc = cache.createGraphics();
            gc.setColor(Color.WHITE);
            gc.fillRect(0, 0, w, h); // fill in background
		}
		g2.drawImage(cache, null, 0, 0);
        if(isdragged){
        	drawShape(g2);
        	
        	
        }
		
	}
	private void drawShape(Graphics2D g2) {
		g2.setColor(SideMenu.getSelectedForeColor());
		g2.setStroke(new BasicStroke(SideMenu.getStrokeSize()));
		switch(SideMenu.getSelectedTool()){
			case 0:
				g2.drawLine(x1, y1, x2, y2);
				x1=x2;
				y1=y2;
				break;
			case 1:
				g2.drawLine(x1, y1, x2, y2);
				break;
			case 2:
			case 4:
			case 7:	
			case 9:
			case 3:
			case 8:	
				int x=x1,y=y1;
				if(x2>x1 &&y2>y1) ;
				else  if(x2>x1 && y2 <y1)
				  y=y2;
				else if(x2<x1 && y2 >y1)
					x=x2;
				else
					{x=x2;y=y2;}
				 if(SideMenu.getSelectedTool()==2)
					 g2.drawRect(x, y, Math.abs(x1-x2), Math.abs(y2-y1));
				 if(SideMenu.getSelectedTool()==7)
					 g2.fillRect(x, y, Math.abs(x1-x2), Math.abs(y2-y1));
				 if(SideMenu.getSelectedTool()==9)
					 g2.fillRoundRect(x, y,Math.abs(x2-x1), Math.abs(y2-y1), 10, 10);
				 if(SideMenu.getSelectedTool()==4) 
					 g2.drawRoundRect(x, y,Math.abs(x2-x1), Math.abs(y2-y1), 10, 10);

				 if(SideMenu.getSelectedTool()==3)
					 g2.drawOval(x, y, Math.abs(x2-x1), Math.abs(y2-y1));
				 if(SideMenu.getSelectedTool()==8)
					 g2.fillOval(x, y, Math.abs(x2-x1), Math.abs(y2-y1));
				 
			break;
				
			
			case 5:
				SideMenu.setForeColor(Color.white);
				g2.setColor(Color.WHITE);
				g2.drawLine(x1, y1, x2, y2);
				x1=x2;
				y1=y2;
				break;
			case 6:
				GraphicsEnvironment ge = GraphicsEnvironment.
                getLocalGraphicsEnvironment();
				final String[] fonts = ge.getAvailableFontFamilyNames();
				g2.setFont(new Font(fonts[SideMenu.getSelectedFont()], Font.PLAIN, SideMenu.getFontSize()));
				g2.drawString(SideMenu.getInputText(), x1, y1);
				break;
			case 10:
				cache=fill(cache, x1, y1, SideMenu.getSelectedForeColor());
				break;
		}
		
		
	}
		@Override
	public void mouseDragged(MouseEvent ev) {
		isdragged=true;
		x2=ev.getX();
		y2=ev.getY();
		if(SideMenu.getSelectedTool()==0 || SideMenu.getSelectedTool()==5)
		drawShape(cache.createGraphics());
		this.repaint();
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent ev) {
		x1=ev.getX();
		y1=ev.getY();
		ispressed=true;

		
	}

	@Override
	public void mouseReleased(MouseEvent ev) {
		isdragged=false;
		ispressed=false;
		x2=ev.getX();
		y2=ev.getY();
		
		drawShape(cache.createGraphics());

		this.repaint();

		
	}
	public BufferedImage fill(Image img, int xSeed, int ySeed, Color col)
	  {
	    BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    bi.getGraphics().drawImage(img, 0, 0, null);
	    int x = xSeed;
	    int y = ySeed;
	    int width = bi.getWidth();
	    int height = bi.getHeight();

	    DataBufferInt data = (DataBufferInt) (bi.getRaster().getDataBuffer());
	    int[] pixels = data.getData();

	    if (x >= 0 && x < width && y >= 0 && y < height)
	    {

	      int oldColor = pixels[y * width + x];
	      int fillColor = col.getRGB();

	      if (oldColor != fillColor)
	      {
	        floodIt(pixels, x, y, width, height, oldColor, fillColor);
	      }
	    }
	    return bi;
	  }


	  private void floodIt(int[] pixels, int x, int y, int width, int height, int oldColor, int fillColor)
	  {

	    int[] point = new int[] { x, y };
	    LinkedList<int[]> points = new LinkedList<int[]>();
	    points.addFirst(point);

	    while (!points.isEmpty())
	    {
	      point = points.remove();

	      x = point[0];
	      y = point[1];
	      int xr = x;

	      int yp = y * width;
	      int ypp = yp + width;
	      int ypm = yp - width;

	      do
	      {
	        pixels[xr + yp] = fillColor;
	        xr++;
	      }
	      while (xr < width && pixels[xr + y * width] == oldColor);

	      int xl = x;
	      do
	      {
	        pixels[xl + yp] = fillColor;
	        xl--;
	      }
	      while (xl >= 0 && pixels[xl + y * width] == oldColor);

	      xr--;
	      xl++;

	      boolean upLine = false;
	      boolean downLine = false;

	      for (int xi = xl; xi <= xr; xi++)
	      {
	        if (y > 0 && pixels[xi + ypm] == oldColor && !upLine)
	        {
	          points.addFirst(new int[] { xi, y - 1 });
	          upLine = true;
	        }
	        else
	        {
	          upLine = false;
	        }
	        if (y < height - 1 && pixels[xi + ypp] == oldColor && !downLine)
	        {
	          points.addFirst(new int[] { xi, y + 1 });
	          downLine = true;
	        }
	        else
	        {
	          downLine = false;
	        }
	      }
	    }
	  }
	

}
