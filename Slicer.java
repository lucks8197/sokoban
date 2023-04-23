import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Slicer {
   public static void main(String[] args) {
      String fileName;
      Scanner sc = new Scanner(System.in);
      System.out.println("Enter Filename");
      fileName = sc.nextLine();
      
      BufferedImage b = null;
      try {b = ImageIO.read(new File(fileName));} 
      // throw an absolute tantrum if file does not exist
      catch (Exception FileNotFoundException) {System.exit(-363);}
      
      int w = b.getWidth();
      int h = b.getHeight();
      if (w != h || w%4 != 0) System.exit(531);
      
      ArrayList<BufferedImage> bs = new ArrayList<BufferedImage>();
      
      try {
         for (int x=0; x<=3; x++) {
            for (int y=0; y<=3; y++) {

               File f = new File("output\\"+fileName.split("\\.")[0]+(x+y*4)+".png");

               f.createNewFile();
               ImageIO.write(b.getSubimage(x*w/4, y*h/4, w/4, h/4), "png",f);
            }
         }
      }
      catch (Exception e) {e.printStackTrace();}
          
     
   }
   
   
}