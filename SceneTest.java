import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


public class SceneTest extends Scene {
   public static void main(String[] args) {sokobanRunner.main(new String[0]);}
   
   ArrayList<Grid> gs;
   double cx   = 0;
   double cy   = 0;
   double zoom = 1;
   
   public SceneTest() {
      gs = new ArrayList<Grid>();
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
            if (c=='.') gs.add(new Wall(x,y));
            x++;
         }
         x=0;
         y++;
      }
   }
   public void sizeCamera() {
      double w = getGSw()+1;
      double h = getGSh()+1;
      if (w >= h*screenRatio) {
         zoom = 100*screenRatio/w;
         cy = (100-cy(1)*h)/-2;
      }
      else {
         zoom = 100/h;
         cx = (100*screenRatio-cx(1)*w)/-2;
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
   public void draw(Graphics2D g2d, SceneTest s) {}
   
}



class Wall extends Grid {
   double size = 1;
   
   public Wall() {
      super();
   }  
   public Wall(int x, int y) {
      super(x,y);
   }
   
   @Override
   public void draw(Graphics2D g2d, SceneTest s) {
      s.srect(g2d, s.cx(x), s.cy(y), s.zoom*size, s.zoom*size);
   }

}