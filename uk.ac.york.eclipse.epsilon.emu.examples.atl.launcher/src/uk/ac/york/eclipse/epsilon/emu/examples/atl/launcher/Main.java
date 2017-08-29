package uk.ac.york.eclipse.epsilon.emu.examples.atl.launcher;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import org.eclipse.m2m.atl.core.ATLCoreException;

public class Main {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		String transformation[] = {
				"Book2Publication",
				"HTML2XML",
				// "SimpleClass2SimpleRDBMS"
		};
		String inModelsFolder = "inModels/";
		String outModelsFolder = "outModels/";
		TransformationLauncher exe;
		Class<?> clazz;
		Method method;
		File folder = null;
		String _package = "uk.ac.york.eclipse.epsilon.emu.examples.atl.launcher.files";
		Map<String, String> config;

		for (int i = 0; i < transformation.length; i++) {
			try {
				clazz = Class.forName(_package + "." + transformation[i]);
				method = clazz.getMethod("properties");
				config = (Map<String, String>) method.invoke(clazz);
				folder = new File(inModelsFolder + transformation[i]);
				System.out.println("- - - - - - - - - - - - - -");
				System.out.println("Module: " + transformation[i]);
				System.out.println("   |");
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}

			for (File entry : folder.listFiles()) {
				System.out.println("   -----> " + entry);
				String input_file = entry.getName();
				String output_file = input_file.substring(0, input_file.length() - 4);
				output_file = outModelsFolder + transformation[i] + "/" + output_file + "_result.xmi";
				exe = new TransformationLauncher();
				try {
					exe.run(config.get("IN_METAMODEL"), config.get("IN_METAMODEL_NAME"), entry.getPath(),
							config.get("OUT_METAMODEL"), config.get("OUT_METAMODEL_NAME"), output_file,
							config.get("TRANSFORMATION_DIR"), config.get("TRANSFORMATION_MODULE"));
				} catch (ATLCoreException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
