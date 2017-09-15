package uk.ac.york.cs.emu.examples.atl.launcher.files;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration of the 'Table2TabularHTML' transformation module.
 */
public class Table2TabularHTML {

	static private Map<String, String> properties;

	public static Map<String, String> properties() {
		properties = new HashMap<String, String>();
		// meta-variables
		String module_name = "Table2TabularHTML";
		// This to hold all helpers files separated with commas
		String helpers_list = "TableHelpers";
		String inMM_name = "Table";
		String outMM_name = "HTML";
		String path = "/Users/AFADF_F/Git/Fhma/runtime-EclipseApplication/uk.ac.york.cs.emu.examples.atl/transformations/";
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

	private Table2TabularHTML() {
	}
}