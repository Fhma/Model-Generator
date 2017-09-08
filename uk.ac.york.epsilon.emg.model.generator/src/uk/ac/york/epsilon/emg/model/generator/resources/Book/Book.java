package uk.ac.york.epsilon.emg.model.generator.resources.Book;

import java.util.HashMap;
import java.util.Map;

public class Book {
	static private Map<String, Object> properties;

	public static Map<String, Object> properties() {
		properties = new HashMap<String, Object>();
		
		// meta-variables
		String module_name = "Book2Publication";
		String metamodel_name = "Book";
		String modelNameBase = "/Users/AFADF_F/Git/Fhma/runtime-EclipseApplication/uk.ac.york.eclipse.epsilon.emu.examples.atl.launcher/inModels/"
				+ module_name + "/" + metamodel_name + "_input_";

		// Metamodel and module details
		properties.put("TRANSFORMATION_MODULE", module_name);
		properties.put("METAMODEL_NAME", metamodel_name);
		properties.put("METAMODEL", Book.class.getResource(metamodel_name + ".ecore"));

		// Do not change
		properties.put("EMG_FILE", Book.class.getResource("generator.emg"));

		// Output model name base
		properties.put("MODEL_BASE_NAME", modelNameBase);

		// Model size (number of instances)
		properties.put("MIN_MODEL_SIZE", 2);
		properties.put("MAX_MODEL_SIZE", 200);

		return properties;
	}

	private Book() {
	}
}
