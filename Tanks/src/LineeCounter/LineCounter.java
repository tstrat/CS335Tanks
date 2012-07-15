package LineeCounter;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class LineCounter {

	private HashSet<String> files = new HashSet<String>();
	int blankLines = 0;
	int codeLines = 0;
	int commentLines = 0;
	int characters = 0;

	public LineCounter() {
		populateList();
		createStatistics();
	}

	public LineCounter(String source) {
		populateList(source + "/");
		createStatistics();
	}

	private void createStatistics() {
		Scanner scan = new Scanner("");
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
						|| lineToInspect.charAt(0) == '*') {
					commentLines++;
				} else {
					codeLines++;
				}
				characters += lineToInspect.length();
			}
		}
	}

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

	public void populateList() {
		populateList("src/");
	}

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

	private void print() {
		// TODO Auto-generated method stub
		for(String str: files) {
			System.out.println(str);
		}
	}
	
	public static void main(String[] args) {

		LineCounter lc = new LineCounter();
		lc.printStats();
		//lc.find("System.out.print");
		//lc.print();
	}


}
