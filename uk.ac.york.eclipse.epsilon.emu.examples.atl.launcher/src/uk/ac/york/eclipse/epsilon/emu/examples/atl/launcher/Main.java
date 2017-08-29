package uk.ac.york.eclipse.epsilon.emu.examples.atl.launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		BufferedReader read = null;
		String line;
		List<String> transformations = new ArrayList<String>();
		try {
			read = new BufferedReader(new FileReader(Main.class.getResource("files/transformation.list").getPath()));
			while ((line = read.readLine()) != null) {
				if (!line.startsWith("#"))
					transformations.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		String inModelsFolder = "inModels/";
		String outModelsFolder = "outModels/";
		TransformationLauncher exe;
		Class<?> clazz;
		Method method;
		File folder = null;
		String _package = "uk.ac.york.eclipse.epsilon.emu.examples.atl.launcher.files";
		Map<String, String> config;

		for (int i = 0; i < transformations.size(); i++) {
			try {
				clazz = Class.forName(_package + "." + transformations.get(i));
				method = clazz.getMethod("properties");
				config = (Map<String, String>) method.invoke(clazz);
				folder = new File(inModelsFolder + transformations.get(i));
				System.out.println("- - - - - - - - - - - - - -");
				System.out.println("Module: " + transformations.get(i));
				System.out.println("   |");
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}

			for (File entry : folder.listFiles()) {
				System.out.println("   -----> " + entry);
				String input_file = entry.getName();
				String output_file = input_file.substring(0, input_file.length() - 4);
				output_file = outModelsFolder + transformations.get(i) + "/" + output_file + "_result2"
						+ config.get("OUT_METAMODEL_NAME") + ".xmi";
				exe = new TransformationLauncher();
				try {
					exe.run(config.get("IN_METAMODEL"), config.get("IN_METAMODEL_NAME"), entry.getPath(),
							config.get("OUT_METAMODEL"), config.get("OUT_METAMODEL_NAME"), output_file,
							config.get("TRANSFORMATION_DIR"), config.get("TRANSFORMATION_MODULE"),
							config.get("TRANSFORMATION_HELPERS"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
