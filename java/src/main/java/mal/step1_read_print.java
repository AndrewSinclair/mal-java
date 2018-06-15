package mal;

import java.util.Scanner;

public class step1_read_print {
	// read
	public static types.MalType READ(String str) throws reader.EofException {
		return reader.read_str(str);
	}

	// eval
	public static types.MalType EVAL(types.MalType form) {
		return form;
	}

	// print
	public static String PRINT(types.MalType form) {
		return printer.pr_str(form, true);
	}

	// repl
	public static String RE(String str) throws reader.EofException {
		return PRINT(EVAL(READ(str)));
	}

	public static void main(String[] args) {
		String prompt = "user> ";

		Scanner scanner = new Scanner(System.in);
		while (true) {
			String line;
			try {
				System.out.print(prompt);
				line = scanner.nextLine();
				if (line == null || line.isEmpty()) { continue; }
				System.out.println(RE(line));
			} catch (reader.EofException e) {
				System.out.println(e.getMessage());
			} catch (java.util.NoSuchElementException e) {
				System.out.println("\nBye bye");
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
