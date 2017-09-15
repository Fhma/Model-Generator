package uk.ac.york.cs.emu.examples.atl.model2code.converter.files;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration of the 'Book2Publication' transformation module.
 */
public class Book2Publication {

	static private Map<String, String> properties;

	public static Map<String, String> properties() {
		properties = new HashMap<String, String>();
		// meta-variables
		String module_name = "Book2Publication";
		String path = "/Users/AFADF_F/Git/Fhma/runtime-EclipseApplication/uk.ac.york.cs.emu.examples.atl/transformations/";
		path += module_name + "/";

		properties.put("TRANSFORMATION_MODULE", module_name);
		properties.put("TRANSFORMATION_DIR", path);
		return properties;
	}

	private Book2Publication() {
	}
}