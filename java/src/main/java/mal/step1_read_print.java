package mal;

import java.util.Scanner;

public class step1_read_print {
	// read
	public static types.MalType READ(String str) {
		return reader.read_str(str);
	}

	// eval
	public static types.MalType EVAL(types.MalType form) {
		return form;
	}

	// print
	public static String PRINT(types.MalType form) {
		return printer.pr_str(form);
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
