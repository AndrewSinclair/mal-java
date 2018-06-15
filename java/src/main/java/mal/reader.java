package mal;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.DecimalFormatSymbols;

public class reader {
	public static types.MalType read_str(String str) throws EofException {
		List<Token> tokens = tokenizer(str);

		Reader rdr = new Reader();
		rdr.tokens = tokens;

		return read_form(rdr);
	}

	public static List<Token> tokenizer(String str) {
		List<Token> tokens = new ArrayList<>();
		Pattern pattern = Pattern.compile("[\\s,]*(~@|[\\[\\]{}()'`~@]|\"(?:[\\\\].|[^\\\\\"])*\"|;.*|[^\\s \\[\\]{}()'\"`~@,;]*)");
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			for (int i = 1; i <= matcher.groupCount(); i++) {
				tokens.add(new Token(matcher.group(i)));
			}
		}
		return tokens;
	}

	public static types.MalType read_form(Reader rdr) throws EofException {
		types.MalType output;
		Token firstToken = rdr.peek();

		if (firstToken.value.isEmpty()) throw new EofException("expected ')', got EOF");

		char firstChar = firstToken.value.charAt(0);

		switch (firstChar) {
			case '(': output = read_list(rdr);
				break;
			default: output = read_atom(rdr);
				break;
		}

		return output;
	}

	public static types.MalType read_list(Reader rdr) throws EofException {
		List<types.MalType> listForms = new ArrayList<>();

		rdr.next(); //discard the top left-paren

		while(!rdr.peek().value.equals(")")) {
			listForms.add(read_form(rdr));
		}

		return new types.MalList(listForms);
	}

	public static boolean isStringNumeric(String str) {
		DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
		char localeMinusSign = currentLocaleSymbols.getMinusSign();

		if (!Character.isDigit(str.charAt(0)) && str.charAt(0) != localeMinusSign) return false;

		boolean isDecimalSeparatorFound = false;
		char localeDecimalSeparator = currentLocaleSymbols.getDecimalSeparator();

		for (char c : str.substring(1).toCharArray()) {
			if (!Character.isDigit(c)) {
				if (c == localeDecimalSeparator && !isDecimalSeparatorFound) {
					isDecimalSeparatorFound = true;
					continue;
				}
				return false;
			}
		}
		return true;
	}

	public static String encode(String str) {
		int length = str.length();
		String output = str.substring(1, length - 1);

		output = output.replace("\\\"", "\"");
		output = output.replace("\\n", "\n");
		output = output.replace("\\\\", "\\");

		return output;
	}

	public static types.MalType read_atom(Reader rdr) throws EofException  {
		types.MalType output;
		Token token = rdr.next();

		if (isStringNumeric(token.value)) {
			int num = Integer.parseInt(token.value);
			output = new types.MalInt(num);
		} else if (token.value.charAt(0) == '"') {
			output = new types.MalString(encode(token.value));
		} else if (token.value.equals("false")) {
			output = new types.MalFalse();
		} else if (token.value.equals("true")) {
			output = new types.MalTrue();
		} else if (token.value.equals("nil")) {
			output = new types.MalNil();
		} else {
			output = new types.MalSymbol(token.value);
		}
		return output;
	}
	public static class Token {
		public String value;

		public Token(String value) {
			this.value = value;
		}
	}

	public static class Reader {
		public int position;
		public List<Token> tokens;
	
		public Reader() {
			this.tokens = null;
			this.position = 0;
		}

		public Token next() {
			Token token = this.tokens.get(this.position);

			this.position++;

			return token;
		}

		public Token peek() {
			return this.tokens.get(this.position);
		}
	}

	@SuppressWarnings("serial")
	public static class EofException extends Exception {
		public EofException(String message) {
			super(message);
		}
	}
}

