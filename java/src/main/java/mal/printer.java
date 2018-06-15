package mal;

import java.util.List;
import java.util.ArrayList;

public class printer {

	private static String decode(String str) {
		String output = str;
		output = output.replace("\\", "\\\\");
		output = output.replace("\"", "\\\"");
		output = output.replace("\n", "\\n");

		return "\"" + output + "\"";
	}
	
	public static String pr_str(types.MalType type, boolean printReadably) {
		if (type instanceof types.MalSymbol) {
			return ((types.MalSymbol) type).value;
		} else if (type instanceof types.MalInt) {
			return Integer.toString(((types.MalInt) type).value);
		} else if (type instanceof types.MalString) {
			if (printReadably) {
				return decode(((types.MalString) type).value);
			} else {
				return ((types.MalString) type).value;
			}
		} else if (type instanceof types.MalFalse) {
			return "false";
		} else if (type instanceof types.MalTrue) {
			return "true";
		} else if (type instanceof types.MalNil) {
			return "nil";
		} else if (type instanceof types.MalList) {
			List<String> output = new ArrayList<>();

			for (types.MalType t : ((types.MalList) type).value) {
				output.add(pr_str(t, printReadably));
			}
			return "(" + String.join(" ", output) + ")";
		} else {
			return null; //this indicates an error
		}
	}
}
