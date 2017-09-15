package uk.ac.york.cs.emu.examples.atl.mutation.executor.files;

import java.util.HashMap;
import java.util.Map;

public class SimpleClass2SimpleRDBMS {
	static private Map<String, String> properties;

	public static Map<String, String> properties() {
		properties = new HashMap<String, String>();
		// meta-variables
		String module_name = "SimpleClass2SimpleRDBMS";
		String path = "/Users/AFADF_F/Git/Fhma/runtime-EclipseApplication/uk.ac.york.cs.emu.examples.atl/transformations/";
		path += module_name + "/";

		properties.put("TRANSFORMATION_MODULE", module_name);
		properties.put("TRANSFORMATION_DIR", path);
		return properties;
	}

	private SimpleClass2SimpleRDBMS() {
	}
}
