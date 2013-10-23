import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.LinkedList;


public class FloodFiller
{

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