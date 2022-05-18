package instAwarePlanning.translator.utils;

public class SupportedNorms {

	// Supported Norms Types
	public static enum Norms {
		// 
		Must("must"),
		// Temporal Norms
		Starts("starts"), Equals("equals"), Overlaps("overlaps"), Before("before"), After("after"),
		// Spatial Norms
		// ...
		// Other Norms
		Use("use");

		private final String value;

		Norms(String value) {
			this.value = value;
		}

		public String getStr() {
			return value;
		}

		public static Norms fromString(String value) {

			for (Norms n : Norms.values()) {
				if (n.value.equalsIgnoreCase(value)) {
					return n;
				}
			}
			return null;
		}
	}

}
