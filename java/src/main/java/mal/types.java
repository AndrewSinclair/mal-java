package mal;

import java.util.List;

public class types {
	abstract public static class MalType {}

	public static class MalSymbol extends MalType {
		public String value;
		public MalSymbol(String value) {
			this.value = value;
		}
	}
	public static class MalInt extends MalType {
		public int value;
		public MalInt(int value) {
			this.value = value;
		}
	}
	public static class MalList extends MalType {
		public List<MalType> value;
		public MalList(List<MalType> value) {
			this.value = value;
		}
	}
}
