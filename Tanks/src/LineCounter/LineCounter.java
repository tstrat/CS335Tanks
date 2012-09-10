package LineCounter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

/**
 * 
 * LineCounter is a console-run utility program that provides the user with data
 * regarding the size of the program and where to find certain user-inputed
 * Strings. These are useful to gauge the scale of the program and for cleaning
 * up print statements used for bug-fixing.
 * 
 * @author Seungwoo Sun
 * 
 */
public class LineCounter {

        private HashSet<String> files = new HashSet<String>();
        int blankLines = 0;
        int codeLines = 0;
        int commentLines = 0;
        int characters = 0;

        /**
         * The default constructor. This will find data on the source folder that
         * LineCounter is in. The data will not include that of LineCounter.java.
         */
        public LineCounter() {
                populateList("src/");
                createStatistics();
        }

        /**
         * This constructor allows for the user to select which folder to find data
         * from.
         * 
         * @param source
         *            The name of the folder that contains the java files to
         *            analyze.
         */
        public LineCounter(String source) {
                populateList(source + "/");
                createStatistics();
        }

        /**
         * Populates the HashSet with the file names of the java files found within
         * a particular directory within the project.
         * 
         * @param source
         *            The sub-directory whose contents are used to populate the list
         *            of java files to analyze.
         */
        public void populateList(String source) {
                File temp = new File(source + "tmp.tmp");

                File folder = new File(temp.getAbsolutePath().substring(0,
                                temp.getAbsolutePath().length() - 8));
                for (File file : folder.listFiles()) {
                        if (file.getName().equals("LineCounter.java")) {
                        } else if (file.getName().endsWith(".java")) {
                                files.add(source + file.getName());
                        } else if (file.isDirectory()) {
                                populateList(source + file.getName() + "/");
                        }
                }
        }

        /**
         * Takes every file whose name was stored by the populateList method and
         * counts the number of lines. It identifies the lines primarily by the
         * first non-whitespace character. If there are no non-whitespace
         * characters, it is considered an empty line; if it starts with "//" or
         * "/*", or '*' if the previous lines were part of a block comment, it is
         * considered a comment line; otherwise it is considered a code line.
         */
        private void createStatistics() {
                Scanner scan = new Scanner("");
                boolean javadoc = false;
                for (String file : files) {
                        try {
                                scan = new Scanner(new File(file));
                        } catch (FileNotFoundException e) {
                                e.printStackTrace();
                        }
                        while (scan.hasNextLine()) {
                                String lineToInspect = scan.nextLine().trim();
                                if (lineToInspect.equals("")) {
                                        blankLines++;
                                } else if (lineToInspect.charAt(0) == '/'
                                                && lineToInspect.charAt(1) == '*') {
                                        commentLines++;
                                        javadoc = true;
                                } else if (javadoc) {
                                        if (lineToInspect.charAt(0) == '*') {
                                                commentLines++;
                                        } else {
                                                javadoc = false;
                                                if (lineToInspect.equals("")) {
                                                        blankLines++;
                                                } else {
                                                        codeLines++;
                                                }
                                        }
                                } else {
                                        codeLines++;
                                }
                                characters += lineToInspect.length();
                        }
                }
        }

        /**
         * Finds a particular string in the program, and prints out info about the
         * location, such as line number and the path of the java file that contains
         * the String.
         * 
         * @param str
         *            The String to search for. It is caps sensitive.
         */
        public void find(String str) {
                Scanner scan = new Scanner("");
                for (String file : files) {
                        try {
                                scan = new Scanner(new File(file));
                        } catch (FileNotFoundException e) {
                                e.printStackTrace();
                        }
                        int line = 0;
                        while (scan.hasNextLine()) {
                                line++;
                                String lineToInspect = scan.nextLine().trim();
                                if (lineToInspect.contains(str)) {
                                        System.out.println(file + " at line " + line);
                                }
                        }
                }

        }

        /**
         * Prints the statistics found by the createStatistics() method.
         */
        public void printStats() {
                File temp = new File("tmp.tmp");
                File folder = new File(temp.getAbsolutePath().substring(0,
                                temp.getAbsolutePath().length() - 8));
                System.out.println("These results are approximate:");
                System.out.println("These are the stats for: " + folder.getName());
                System.out.println("Number of classes: " + files.size());
                System.out.println("Number of total lines: "
                                + (codeLines + commentLines + blankLines));
                System.out.println("Number of blank lines: " + blankLines);
                System.out.println("Number of comment lines: " + commentLines);
                System.out.println("Number of code lines: " + codeLines);
        }

        /**
         * Main method. Takes user input to determine which methods of the
         * LineCounter to use.
         * 
         * @param args
         *            Unused in this program.
         */
        public static void main(String[] args) {

                LineCounter lc = new LineCounter();
                Scanner keyScan = new Scanner(System.in);
                mainBody(lc, keyScan);
                keyScan.close();
        }

        /**
         * This is the main block of code executed by the main method. It is in a
         * private helper method so that it is easier to repeatedly use the
         * LineCounter program, should the user desire.
         * 
         * @param lc
         *            The LineCounter instance that provides the data
         * @param keyScan
         *            The Scanner taking user input
         */
        private static void mainBody(LineCounter lc, Scanner keyScan) {
                System.out.println("Would you like to (type a or b): ");
                System.out.println("(a) see program word-count data?");
                System.out.println("(b) find a particular String?");
                System.out.print("Response: ");
                String response = keyScan.next();
                System.out.println("-----------------");
                if (response.equals("a")) {
                        lc.printStats();
                } else if (response.equals("b")) {
                        System.out.println("Which String would you like to find?");
                        String strToFind = keyScan.next();
                        System.out.println("String found at following locations:");
                        lc.find(strToFind);
                } else {
                        System.out.println("Invalid response. Program will exit.");
                }
                System.out.println("-----------------");
                System.out
                                .println("Would you like to use LineCounter again? (type y or n): ");
                System.out.print("Response: ");
                String yesNoResponse = keyScan.next();
                System.out.println("-----------------");
                if (yesNoResponse.equals("y")) {
                        mainBody(lc, keyScan);
                } else if (yesNoResponse.equals("n")) {
                        System.out.println("Program will exit.");
                } else {
                        System.out.println("Invalid response. Program will exit.");
                }

        }

}