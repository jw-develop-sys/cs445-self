package geo;

import java.awt.FlowLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import hmgraphics.PaintPanel;

/**
 * Program to drive the implementation of Graham's Scan
 * 
 * @author Thomas VanDrunen
 * CSCI 445, Wheaton College
 * Oct 19, 2016
 */
public class ConvexHull {

    private static int numDots = 48;
    private static boolean pre = false;
    private static Random randy = new Random();
    
    public static void main(String[] args) {
        
        String filename = null;
        if (args.length > 0) 
            if (args[0] .equals("-pre"))
                pre = true;
            else if (args[0].equals("-f")) {
                try {
                    filename = args[1];
                }catch (Exception e) {
                    usage();
                }
            }
            else if (args[0].equals("-n"))
                try {
                    numDots = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    usage();
                }
            else
                usage();

        final HashSet<Point> allPoints = new HashSet<Point>();

        
        if (pre) {
            
            allPoints.add(new Point(10,40));
            allPoints.add(new Point(20,110));
            allPoints.add(new Point(50,140));
            allPoints.add(new Point(60,110));
            allPoints.add(new Point(80,90));
            allPoints.add(new Point(90,130));
            allPoints.add(new Point(110,60));
            allPoints.add(new Point(130,100));

        }
        else if (filename != null) {

            Scanner file;
            try {
                file = new Scanner(new File(filename));
                Pattern pointPat = Pattern.compile("\\((\\d+),(\\d+)\\)");
                while (file.hasNext()) {
                    String line = file.nextLine();
                    Matcher matchy = pointPat.matcher(line);
                    matchy.matches();
                    allPoints.add(new Point(Integer.parseInt(matchy.group(1)),
                            Integer.parseInt(matchy.group(2))));
                }
                file.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                System.exit(-1);
            }

        }
        else {
            for (int i = 0; i < numDots; i++) {
                allPoints.add(new Point(200 + (int) (75 * randy.nextGaussian()),
                        200 + (int) (75 * randy.nextGaussian())));
            }
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                PaintPanel p = new PaintPanel(400, 400);   // area for graphics
                JFrame window = new JFrame("Convex Hull");  // window itself
                window.setSize(425, 475);
                window.setLocation(100, 100);
                window.setLayout(new FlowLayout());
                
                window.add(p);



                GrahamScanner gs = new GrahamScanner(allPoints, p);
                p.setPainter(gs);
                JButton next = new JButton("Next");
                next.addActionListener(gs);
                window.add(next);

                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setVisible(true);
               
                
            }
        });
    }

    private static void usage() {
        System.out.println("Usage:");
        System.out.println("java ConvexHull [-pre|-n NUM]");
        System.out.println("\t-pre produce 8 specific pre-defined points");
        System.out.println("\t-n NUM produce NUM random points");
        System.out.println("\tdefault: produce 12 random points");
        System.exit(-1);
    }    
}
