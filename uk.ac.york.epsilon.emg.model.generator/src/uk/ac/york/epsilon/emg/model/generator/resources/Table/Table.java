package uk.ac.york.epsilon.emg.model.generator.resources.Table;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Table {
	static private Map<String, Object> properties;

	public static Map<String, Object> properties() {
		properties = new HashMap<String, Object>();

		// meta-variables
		String metamodel_name = "Table";
		String destination = "generated-models";

		properties.put("METAMODEL_NAME", metamodel_name);
		properties.put("METAMODEL", Table.class.getResource(metamodel_name + ".ecore").getPath());
		properties.put("EMG_FILE", Table.class.getResource("generator.emg"));
		properties.put("MODEL_BASE_NAME", destination + File.separatorChar + metamodel_name + File.separatorChar + metamodel_name + "_input_");
		properties.put("MODEL_NAME", metamodel_name);

		// Model size (number of instances)
		properties.put("MIN_MODEL_SIZE", 4);

		return properties;
	}
}
