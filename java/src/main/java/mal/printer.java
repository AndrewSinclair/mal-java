package mal;

import java.util.List;
import java.util.ArrayList;

public class printer {
	public static String pr_str(types.MalType type) {
		if (type instanceof types.MalSymbol) {
			return ((types.MalSymbol) type).value;
		} else if (type instanceof types.MalInt) {
			return Integer.toString(((types.MalInt) type).value);
		} else if (type instanceof types.MalList) {
			List<String> output = new ArrayList<>();

			for (types.MalType t : ((types.MalList) type).value) {
				output.add(pr_str(t));
			}
			return "(" + String.join(" ", output) + ")";
		} else {
			return null; //this indicates an error
		}
	}
}
