import javax.swing.JFrame;
import java.awt.*;

public class ImageTesting {
   public static void main(String[] args) {
      JFrame f = new JFrame("Image Testing");
      f.resize(new Dimension(500,500));
      Scene sc = new ImageScene();
      // f.addMouseListener((MouseListener) sc);
      f.add(sc);
      if (true) {
         f.setExtendedState(JFrame.MAXIMIZED_BOTH);
         f.setUndecorated(true);
         GraphicsDevice device = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getScreenDevices()[0];
         device.setFullScreenWindow(f); 
      }
      f.setVisible(true);
      while (true) {
         sc.getScreenResizeInfo(f);
         f.repaint();  
      }
   }
   
   public void simg(Graphics2D g2d, double x, double y, double xs, double ys) {
      // kind of like a lerp
      if (ww>wh && wh*screenRatio <= ww) g2d.fill(new Rectangle2D.Double(
            ((x/(100*screenRatio))*w)+lw, ((y/100)*h)+lh, (xs/100)*(w/screenRatio), (ys/100)*(h)
            ));
      else g2d.fill(new Rectangle2D.Double(
            ((x/(100*screenRatio))*w)+lw, ((y/100)*h)+lh, (xs/100)*(w/screenRatio), (ys/100)*(h)
            ));
   }
   
   public void img2(Graphics2D g2d, double x1, double y1, double x2, double y2) {
      srect(g2d, x1, y1, x2-x1, y2-y1);
   }
   
   public void cimg(Graphics2D g2d, double x, double y, double xs, double ys) {
      xs /= 2;
      ys /= 2;
      rect2(g2d, x-xs, y-ys, x+xs, y+ys); 
   }
   

   
   
}

class ImageScene extends Scene {

}