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

public class Tileset {   
   
   ArrayList<BufferedImage> refs;
   
   public Tileset(String prefix) {
      refs = new ArrayList<BufferedImage>();
      for (int i=0; i<16; i++) {
         refs.add(Static.bfile(prefix+i+".png"));
      }
   }
   
   public BufferedImage getTile(boolean r, boolean u, boolean l, boolean d) {
      /*
      
      0  123
      
      4  567
      8  9AB
      C  DEF
      
      */
      
      if (!r&&!u&&!l&&!d) return refs.get(0);
      else if (r&&!u&&!l&&!d) return refs.get(1);
      else if (r&&!u&&l&&!d) return refs.get(2);
      else if (!r&&!u&&l&&!d) return refs.get(3);
      else if (!r&&!u&&!l&&d) return refs.get(4);
      else if (r&&!u&&!l&&d) return refs.get(5);
      else if (r&&!u&&l&&d) return refs.get(6);
      else if (!r&&!u&&l&&d) return refs.get(7);
      else if (!r&&u&&!l&&d) return refs.get(8);
      else if (r&&u&&!l&&d) return refs.get(9);
      else if (r&&u&&l&&d) return refs.get(10);
      else if (!r&&u&&l&&d) return refs.get(11);
      else if (!r&&u&&!l&&!d) return refs.get(12);
      else if (r&&u&&!l&&!d) return refs.get(13);
      else if (r&&u&&l&&!d) return refs.get(14);
      return refs.get(15);
   }
   
}