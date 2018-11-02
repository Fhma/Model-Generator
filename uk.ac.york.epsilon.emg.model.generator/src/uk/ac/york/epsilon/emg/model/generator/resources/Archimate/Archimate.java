package uk.ac.york.epsilon.emg.model.generator.resources.Archimate;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Archimate {
	static private Map<String, Object> properties;

	public static Map<String, Object> properties() {
		properties = new HashMap<String, Object>();

		// meta-variables
		String metamodel_name = "Archimate";
		String destination = "generated-models";

		properties.put("METAMODEL_NAME", metamodel_name);
		properties.put("METAMODEL_URI", "http://www.archimatetool.com/archimate");
		properties.put("METAMODEL", Archimate.class.getResource("archimate.ecore").getPath());
		properties.put("EMG_FILE", Archimate.class.getResource("generator.emg"));
		properties.put("MODEL_BASE_NAME", destination + File.separatorChar + metamodel_name + File.separatorChar + metamodel_name + "_");

		// Model size (number of instances)
		properties.put("MIN_MODEL_SIZE", 6);

		return properties;
	}
}
