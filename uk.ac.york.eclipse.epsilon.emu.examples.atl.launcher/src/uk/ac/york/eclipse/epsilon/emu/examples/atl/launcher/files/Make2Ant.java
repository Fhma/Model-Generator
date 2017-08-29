package uk.ac.york.eclipse.epsilon.emu.examples.atl.launcher.files;

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
		String helpers_list = null;
		String inMM_name = "Make";
		String outMM_name = "Ant";
		String path = "/Users/AFADF_F/Git/Fhma/runtime-EclipseApplication/uk.ac.york.eclipse.epsilon.emu.examples.atl/transformations/";
		path += module_name + "/";

		properties.put("TRANSFORMATION_DIR", path);
		properties.put("IN_METAMODEL", path + inMM_name + ".ecore");
		properties.put("IN_METAMODEL_NAME", inMM_name);
		properties.put("OUT_METAMODEL", path + outMM_name + ".ecore");
		properties.put("OUT_METAMODEL_NAME", outMM_name);
		properties.put("TRANSFORMATION_MODULE", module_name);
		properties.put("TRANSFORMATION_HELPERS", helpers_list);
		return properties;
	}

	private Make2Ant() {
	}
}