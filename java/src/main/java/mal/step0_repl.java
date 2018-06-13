package mal;

import java.util.Scanner;

public class step0_repl {
	// read
	public static String READ(String str) {
		return str;
	}

	// eval
	public static String EVAL(String str) {
		return str;
	}

	// print
	public static String PRINT(String str) {
		return str;
	}

	// repl
	public static String RE(String str) {
		return PRINT(EVAL(READ(str)));
	}

	public static void main(String[] args) {
		String prompt = "user> ";

		Scanner reader = new Scanner(System.in);
		while (true) {
			String line;
			try {
				System.out.print(prompt);
				line = reader.nextLine();
				if (line == null || line.isEmpty()) { continue; }
			} catch (java.util.NoSuchElementException e) {
				System.out.println("\nBye bye");
				break;
			}
			System.out.println(RE(line));
		}
	}
}
