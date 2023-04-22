import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.RenderingHints;
import javax.swing.*;
import javax.swing.JFrame;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class ImageTesting extends Scene {

   static BufferedImage picture;
   
   public static void main(String[] args) {
      JFrame f = new JFrame("Image Testing");
      f.resize(new Dimension(500,500));
      Scene sc = new ImageTesting();
      f.add(sc);
      if (true) {
         f.setExtendedState(JFrame.MAXIMIZED_BOTH);
         f.setUndecorated(true);
         GraphicsDevice device = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getScreenDevices()[0];
         device.setFullScreenWindow(f); 
      }
      f.setVisible(true);
      
      try {picture=ImageIO.read(new File("square.png"));} catch (Exception FileNotFoundException) {System.out.println("PP");}
      
      while (true) {
         sc.getScreenResizeInfo(f);
         f.repaint();  
      }
   }
   
   public void simg(Graphics2D g2d, BufferedImage img, double x, double y, double xs, double ys) {
      // kind of like a lerp
      if (ww>wh && wh*screenRatio <= ww) imageSuffer(g2d, img,
            ((x/(100*screenRatio))*w)+lw, ((y/100)*h)+lh, (xs/100)*(w/screenRatio), (ys/100)*(h)
            );
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
   
   static double xs = 100;
   @Override
   protected void paintComponent(Graphics g) {
      Graphics2D g2d = (Graphics2D)g;
      g2d.setColor(new Color(255,255,0));
      
      srect(g2d,0,0,50,50);simg(g2d,picture,0,0,50,50);
   }   
   
   public void imageSuffer(Graphics2D g2d, BufferedImage img, double x, double y, double xsize, double ysize) {
      AffineTransform at = new AffineTransform();
      at.scale((double)xsize/(img.getWidth()),(double)ysize/(img.getHeight()));
      at.translate(x,y);
      BufferedImageOp bio = (BufferedImageOp)new AffineTransformOp(at, g2d.getRenderingHints());
      g2d.drawImage(img,bio,0,0);      
   }   
}
