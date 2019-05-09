package c15p4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * NeatPrint.java
 * 
 * Class to hold method for printing a long text neatly with a given
 * line length.
 * 
 * @author Thomas VanDrunen
 * CSCI 445
 * Sept 20, 2018
 */
public class NeatPrint {
    /**
     * Produce a version of a given string with new lines
     * placed so that no line is longer than the given 
     * line length and so that the penalty if minimized, 
     * where the penalty is the sum of the cubes of the
     * difference between the line length and each line
     * except the last.
     * @param text The given text
     * @param m The length of the line
     * @return A string with the given text in it plus
     * new lines
     */
    public static String neatPrint(String text, int m) {
        String[] words = text.split("\\s");
        int n = words.length;
         return null;
    }

    private static int cb(int i) {
        return i * i * i;
    }

    /**
     * Usage: java c15p4.NeatPrint (filename) (line length)
     * @param args
     */
    public static void main(String[] args) {
        try {
            int lineLength = Integer.parseInt(args[1]);
            Scanner file = new Scanner(new File(args[0]));
            String text = "";
            while (file.hasNext())
                text += file.nextLine();
            System.out.println(neatPrint(text, lineLength));
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
}
