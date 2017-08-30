package uk.ac.york.eclipse.epsilon.emu.examples.atl.comparator.files;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration of the 'Make2Ant' transformation module.
 */
public class Make2Ant {

	static private Map<String, String> properties;

	public static Map<String, String> properties() {
		properties = new HashMap<String, String>();
		// meta-variables
		String module_name = "Make2Ant";
		String path = "/Users/AFADF_F/Git/Fhma/runtime-EclipseApplication/uk.ac.york.eclipse.epsilon.emu.examples.atl/transformations/";
		path += module_name + "/";

		properties.put("TRANSFORMATION_DIR", path);
		return properties;
	}

	private Make2Ant() {
	}
}