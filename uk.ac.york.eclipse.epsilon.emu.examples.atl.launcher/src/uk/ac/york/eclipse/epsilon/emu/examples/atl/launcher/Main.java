package uk.ac.york.eclipse.epsilon.emu.examples.atl.launcher;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		String transformation[] = { "Book2Publication",
				// "HTML2XML",
				"SimpleClass2SimpleRDBMS" };
		String inModelsFolder = "inModels/";
		String outModelsFolder = "outModels/";
		TransformationLauncher exe;
		Class<?> clazz;
		Method method;
		String _package = "uk.ac.york.eclipse.epsilon.emu.examples.atl.launcher.files";
		Map<String, String> config;
		try {
			for (int i = 0; i < transformation.length; i++) {
				clazz = Class.forName(_package + "." + transformation[i]);
				method = clazz.getMethod("properties");
				config = (Map<String, String>) method.invoke(clazz);
				System.out.println("config" + config);
				final File folder = new File(inModelsFolder + transformation[i]);

				for (File entry : folder.listFiles()) {
					String input_file = entry.getName();
					String output_file = input_file.substring(0, input_file.length() - 4);
					output_file = outModelsFolder + transformation[i] + "/" + output_file + "_result.xmi";
					exe = new TransformationLauncher();
					exe.run(config.get("IN_METAMODEL"), config.get("IN_METAMODEL_NAME"), entry.getPath(),
							config.get("OUT_METAMODEL"), config.get("OUT_METAMODEL_NAME"), output_file,
							config.get("TRANSFORMATION_DIR"), config.get("TRANSFORMATION_MODULE"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
