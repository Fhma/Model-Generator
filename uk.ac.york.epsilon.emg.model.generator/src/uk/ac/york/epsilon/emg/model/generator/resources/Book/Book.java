package uk.ac.york.epsilon.emg.model.generator.resources.Book;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Book {
	static private Map<String, Object> properties;

	public static Map<String, Object> properties() throws URISyntaxException {
		properties = new HashMap<String, Object>();

		// meta-variables
		String metamodel_name = "Book";
		String destination = "generated-models";
		
		properties.put("METAMODEL_NAME", metamodel_name);
		properties.put("METAMODEL", Book.class.getResource(metamodel_name + ".ecore").getPath());
		properties.put("EMG_FILE", Book.class.getResource("generator.emg"));
		properties.put("MODEL_BASE_NAME", destination + File.separatorChar + metamodel_name + File.separatorChar + metamodel_name + "_input_");
		properties.put("MODEL_NAME", metamodel_name);

		// Model size (min number of instances)
		properties.put("MIN_MODEL_SIZE", 2);

		return properties;
	}

	private Book() {
	}
}
