package uk.ac.york.eclipse.epsilon.emu.examples.atl.mutation.executor.files;

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
		String path = "/Users/AFADF_F/Git/Fhma/runtime-EclipseApplication/uk.ac.york.eclipse.epsilon.emu.examples.atl/transformations/";
		path += module_name + "/";

		properties.put("TRANSFORMATION_DIR", path);
		return properties;
	}

	private Table2TabularHTML() {
	}
}