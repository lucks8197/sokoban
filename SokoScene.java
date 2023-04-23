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
import java.util.ArrayList;
import java.util.Scanner;

public class SokoScene extends Scene {
   public static void main(String[] args) {SokobanRunner.main(new String[0]);}
   
   ArrayList<Grid> gs;
   ArrayList<BufferedImage> bs;
   double cx   = 0;
   double cy   = 0;
   double zoom = 1;
   
   public SokoScene() {
      gs = new ArrayList<Grid>();
      bs = new ArrayList<BufferedImage>();
      bs.add(Static.bfile("square.png"));
      initializeGS();
      sizeCamera();
      
   }
   
   public void initializeGS() {
      int x = 0;
      int y = 0;
      String st = "";
      Scanner s = new Scanner(System.in);
      try {s = new Scanner(new File("a.txt"));}
      catch (Exception FileNotFoundException) {System.out.println("oh no");}
      
      while (s.hasNextLine()) {
         st = s.nextLine();
         for (char c: st.toCharArray()) {
            if (c=='.') gs.add(new Wall(x,y,bs.get(0)));
            x++;
         }
         x=0;
         y++;
      }
   }
   public void sizeCamera() {
      int off = 2;
      double w = getGSw()+1+off;
      double h = getGSh()+1+off;
      if (w >= h*screenRatio) {
         zoom = 100*screenRatio/w;
         cy = (100-cy(1)*(h-off))/-2;
         cx = -cx(1);
      }
      else {
         zoom = 100/h;
         cx = (100*screenRatio-cx(1)*(w-off))/-2;
         cy = cy(1);
      }

      
   }
   
   public int getGSw() {
      int hx = Integer.MIN_VALUE;
      int lx = Integer.MAX_VALUE;
      for (Grid g: gs) {
         if (g.x > hx) hx = g.x;
         if (g.x < lx) lx = g.x;
      }
      return Math.abs(hx-lx);
   }
   
   public int getGSh() {
      int hy = Integer.MIN_VALUE;
      int ly = Integer.MAX_VALUE;
      for (Grid g: gs) {
         if (g.y > hy) hy = g.y;
         if (g.y < ly) ly = g.y;
      }
      return Math.abs(hy-ly);
   }
   
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2d = (Graphics2D)g;
      
      for (Grid grid:gs) {
         grid.draw(g2d, this);
      }
      super.makeLetterbox(g2d);
   }
   
   public double cx(double x) {return (double)x*zoom-cx;}
   public double cy(double y) {return (double)y*zoom-cy;}
   
}



class Grid {
   int x;
   int y;
   boolean draw = false;
   public Grid() {
      x=0; y=0;
   }
   public Grid(int x, int y) {
      this.x=x; this.y=y;
   }
   public void draw(Graphics2D g2d, SokoScene s) {}
   
}



class Wall extends Grid {
   double size = 1;
   BufferedImage ref;
   
   public Wall() {
      super();
   }  
   public Wall(int x, int y, BufferedImage ref) {
      super(x,y);
      this.ref = ref;
   }
   
   @Override
   public void draw(Graphics2D g2d, SokoScene s) {
      s.simg(g2d, ref, s.cx(x), s.cy(y), s.zoom*size, s.zoom*size);
   }

}