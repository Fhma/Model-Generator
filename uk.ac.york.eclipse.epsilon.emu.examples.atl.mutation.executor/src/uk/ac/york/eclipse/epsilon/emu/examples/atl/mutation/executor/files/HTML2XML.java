package uk.ac.york.eclipse.epsilon.emu.examples.atl.mutation.executor.files;

import java.util.HashMap;
import java.util.Map;

public class HTML2XML {
	static private Map<String, String> properties;

	public static Map<String, String> properties() {
		properties = new HashMap<String, String>();
		// meta-variables
		String module_name = "HTML2XML";
		String path = "/Users/AFADF_F/Git/Fhma/runtime-EclipseApplication/uk.ac.york.eclipse.epsilon.emu.examples.atl/transformations/";
		path += module_name + "/";

		properties.put("TRANSFORMATION_DIR", path);
		return properties;
	}

	private HTML2XML() {
	}
}
