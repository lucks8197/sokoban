import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.RenderingHints;
import javax.swing.*;

public class SokobanRunner {
   public static void main(String args[]) {
      JFrame f = new JFrame("Base");
      ImageIcon img = new ImageIcon("C:\\Users\\lucas\\OneDrive\\Desktop\\mp4\\vsauce.jpg");
      f.setIconImage(img.getImage());
      f.setSize(500,500);
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      Scene sc = new SceneTest();
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
}

class Scene extends JComponent implements MouseListener {
   
   double ww;
   double wh;
   double lw;
   double lh;
   double w;
   double h;
   double screenRatio = 16.0/10;
   
   public Scene() {}
      protected void paintComponent(Graphics g) {
      Graphics2D g2d = (Graphics2D)g;
      g2d.setColor(new Color(0,0,0));
      makeBackground(g2d);
      g2d.setColor(new Color(200,200,200));
      
   }
   
   public void srect(Graphics2D g2d, double x, double y, double xs, double ys) {
      // kind of like a lerp
      if (ww>wh && wh*screenRatio <= ww) g2d.fill(new Rectangle2D.Double(
            ((x/(100*screenRatio))*w)+lw, ((y/100)*h)+lh, (xs/100)*(w/screenRatio), (ys/100)*(h)
            ));
      else g2d.fill(new Rectangle2D.Double(
            ((x/(100*screenRatio))*w)+lw, ((y/100)*h)+lh, (xs/100)*(w/screenRatio), (ys/100)*(h)
            ));
   }
   
   public void rect2(Graphics2D g2d, double x1, double y1, double x2, double y2) {
      srect(g2d, x1, y1, x2-x1, y2-y1);
   }
   
   public void crect(Graphics2D g2d, double x, double y, double xs, double ys) {
      xs /= 2;
      ys /= 2;
      rect2(g2d, x-xs, y-ys, x+xs, y+ys); 
   }
   
   private void makeBackground(Graphics2D g2d) {
      g2d.fill(new Rectangle2D.Double(0,0,ww, wh));
   }
   public void makeLetterbox(Graphics2D g2d) {
      g2d.setColor(new Color(0,0,0));
      g2d.fill(new Rectangle2D.Double(0,0,lw,wh));
      g2d.fill(new Rectangle2D.Double(lw+w,0,lw,wh));
      g2d.fill(new Rectangle2D.Double(0,0,ww,lh));
      g2d.fill(new Rectangle2D.Double(0,lh+h,ww,lh));
   }
   
   public void getScreenResizeInfo(JFrame f) {
      ww = f.getContentPane().getSize().getWidth();
      wh = f.getContentPane().getSize().getHeight();
      
      if (ww>wh && wh*screenRatio <= ww) {
         h = wh;
         w = wh*screenRatio;
         lh = 0;
         lw = (ww-w)/2;
      }
      else {
         h = ww/screenRatio;
         w = ww;
         lh = (wh-h)/2;
         lw = 0;         
      }
   }
      
   
    @Override
    public void mousePressed(MouseEvent e) {
      
      if (e.getButton()!=1) return;
      
      Point m = getMousePosition();
      if (m==null) return;
      double mx = m.getX(); double my = m.getY();
    }
    @Override
    public void mouseReleased(MouseEvent e) {
      if (e.getButton()!=1) return;
      
      Point m = getMousePosition();
      if (m==null) return;
      double mx = m.getX(); double my = m.getY();
      
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}