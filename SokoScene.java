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
   ArrayList<Tileset> ts;
   double cx   = 0;
   double cy   = 0;
   double zoom = 1;
   
   double startCOffset;
   
   public SokoScene() {
      gs = new ArrayList<Grid>();
      bs = new ArrayList<BufferedImage>();
      bs.add(Static.bfile("pinkbg.png"));
      bs.add(Static.bfile("pinkbgtop.png"));
      initializeGS();
      sizeCamera();
      startCOffset = 50;
      
   }
   
   public void initializeGS() {
      int x = 0;
      int y = 0;
      String st = "";
      Scanner s = new Scanner(System.in);
      try {s = new Scanner(new File("a.txt"));}
      catch (Exception FileNotFoundException) {System.exit(-669);}
      
      // add from a.txt
      while (s.hasNextLine()) {
         st = s.nextLine();
         for (char c: st.toCharArray()) {
            if (c=='.') gs.add(new Wall(x,y,bs.get(0)));
            if (c=='b') {
               gs.add(new Background(x,y,bs.get(0)));
               //for (Grid g: gs) {
                 // if (g.isWallTile && g.x == x && g.y == y-1) gs.get(gs.size()-1).ref = bs.get(1);
               //}
            }
            x++;
         }
         x=0;
         y++;
      }
      
      // tileset gaming
      Tileset t = new Tileset("pink");
      for (Grid g: gs) {
         if (!g.isWallTile) continue;
         boolean r, u, l, d;
         r=u=l=d = false;
         for (Grid o:gs) {
            if (!o.isWallTile) continue;
            if (r&&u&&l&&d) break;
            if      (g.x - o.x == -1 && g.y == o.y) {r = true; continue;}
            else if (g.y - o.y ==  1 && g.x == o.x) {u = true; continue;}
            else if (g.x - o.x ==  1 && g.y == o.y) {l = true; continue;}
            else if (g.y - o.y == -1 && g.x == o.x) {d = true; continue;}
         }
         g.ref = t.getTile(r, u, l, d);
      }
      
   }
   
   public void makeBackground(Graphics2D g2d) {
      Color temp = g2d.getColor();
      g2d.setColor(new Color(255, 200, 200));
      g2d.fill(new Rectangle2D.Double(0,0,ww, wh));
      g2d.setColor(temp);
   }
   
   public void sizeCamera() {
      double h = getGSh();
      double w = getGSw();
      double size = 10+(double)Math.max(w,h)/50;
      if (100/(h+size) > 100*screenRatio/(w+size)) {
         zoom = 100*screenRatio/(w+size);
         cx = -(size-1)/2;
         // how many tiles can fit vertically? 100/zoom
         // how many tiles are there vertically? height
         // how much unused space is there? 100/zoom - height
         // how much should we tack on to the bottom? (100/zoom - height) / 2
         cy = -(100/zoom-h)/2;
      }
      else {
         zoom = 100/(h+size);
         cy = -(size-1)/2;
         // how many tiles can fit horizontally? 100*screenRatio/zoom
         // how many tiles are there horizontally? width
         // how much unused space is there? 100*screenRatio/zoom - width
         // how much should we tack on to the left? (100*screenRatio/zoom - width) / 2
         cx = -(100*screenRatio/zoom-w)/2;
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
      startCOffset *= 0.9;
      if (Math.abs(startCOffset) < 0.1) startCOffset = 0;
      for (Grid grid:gs) {
         grid.draw(g2d, this);
      }
      super.makeLetterbox(g2d);
   }
   
   public double cx(double x) {return (double)x*zoom-(cx-startCOffset)*zoom;}
   public double cy(double y) {return (double)y*zoom-(cy)*zoom;}
   
}



class Grid {
   int x;
   int y;
   BufferedImage ref;
   boolean isWallTile = false;
   
   boolean draw = false;
   public Grid() {
      x=0; y=0; ref=null;
   }
   public Grid(int x, int y) {
      this.x=x; this.y=y;
   }
   public void draw(Graphics2D g2d, SokoScene s) {}
   
}



class Wall extends Grid {
   double size = 1;
   
   public Wall() {
      super(); 
      isWallTile = true;
   }  
   public Wall(int x, int y, BufferedImage ref) {
      super(x,y);
      this.ref = ref;
      isWallTile = true;
   }
   
   @Override
   public void draw(Graphics2D g2d, SokoScene s) {
      s.simg(g2d, ref, s.cx(x), s.cy(y), s.zoom*size, s.zoom*size);
   }

}
class Background extends Grid {
   double size = 1;
   
   public Background() {
      super();
   }  
   public Background(int x, int y, BufferedImage ref) {
      super(x,y);
      this.ref = ref;
   }
   
   @Override
   public void draw(Graphics2D g2d, SokoScene s) {
      s.simg(g2d, ref, s.cx(x), s.cy(y), s.zoom*size, s.zoom*size);
   }

}