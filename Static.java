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

public class Static {
    
    public static BufferedImage bfile(String fileName) {
       try {return ImageIO.read(new File(fileName));} 
       catch (Exception FileNotFoundException) {System.exit(-1);}
       return null;
   }
   
   public static void simpleImage(Graphics2D g2d, BufferedImage img, double x, double y, double xsize, double ysize) {
      AffineTransform at = new AffineTransform();
      at.translate(x,y);
      at.scale((double)xsize/(img.getWidth()),(double)ysize/(img.getHeight()));
      BufferedImageOp bio = (BufferedImageOp)new AffineTransformOp(at, g2d.getRenderingHints());
      g2d.drawImage(img,bio,0,0);      
   }  
   
}