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
    // a buncha static methods    
    public static BufferedImage bfile(String fileName) {
       try {return ImageIO.read(new File("assets/"+fileName));} 
       // throw an absolute tantrum if file does not exist
       catch (Exception FileNotFoundException) {System.out.println(fileName+" does not exist");System.exit(-363);}
       
       return null; // why do i have to put this, its hard to return something when it has already crashed
   }
   
   // """"""""""""""""""""""simple"""""""""""""""""""""""""
   public static void simpleImage(Graphics2D g2d, BufferedImage img, double x, double y, double xsize, double ysize) {
      // linear transformation that is better than it looks
      AffineTransform at = new AffineTransform();
      // why move the image when you can move the pixels
      at.translate(x,y);
      at.scale((double)xsize/(img.getWidth()),(double)ysize/(img.getHeight()));
      BufferedImageOp bio = (BufferedImageOp)new AffineTransformOp(at, g2d.getRenderingHints());
      g2d.drawImage(img,bio,0,0);      
   }  
      
}