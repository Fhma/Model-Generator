package uk.ac.york.epsilon.emg.model.generator.resources.Graph;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Graph {
	static private Map<String, Object> properties;

	public static Map<String, Object> properties() {
		properties = new HashMap<String, Object>();

		// meta-variables
		String metamodel_name = "Graph";
		String destination = "generated-models";

		properties.put("METAMODEL_NAME", metamodel_name);
		properties.put("METAMODEL", Graph.class.getResource("graph.ecore").getPath());
		properties.put("EMG_FILE", Graph.class.getResource("generator.emg"));
		properties.put("MODEL_BASE_NAME", destination + File.separatorChar + metamodel_name + File.separatorChar + metamodel_name + "_");
		properties.put("MODEL_NAME", metamodel_name);

		// Model size (number of instances)
		properties.put("MIN_MODEL_SIZE", 2);

		return properties;
	}
}
