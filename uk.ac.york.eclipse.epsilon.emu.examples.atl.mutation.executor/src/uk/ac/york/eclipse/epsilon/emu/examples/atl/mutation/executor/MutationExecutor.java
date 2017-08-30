package uk.ac.york.eclipse.epsilon.emu.examples.atl.mutation.executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.emu.EmuModule;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import java.net.URISyntaxException;

public class MutationExecutor {

	public static void main(String[] args) {
		BufferedReader read = null;
		String line;
		List<String> transformations = new ArrayList<String>();
		try {
			read = new BufferedReader(
					new FileReader(MutationExecutor.class.getResource("files/transformation.list").getPath()));
			while ((line = read.readLine()) != null) {
				if (!line.startsWith("#"))
					transformations.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		Class<?> clazz;
		String _package = MutationExecutor.class.getPackage().getName() + ".files";
		Method method;
		String metamodel = "/Users/AFADF_F/Git/Fhma/runtime-EclipseApplication/uk.ac.york.eclipse.epsilon.emu.examples.atl/ATL.ecore";
		String model = null;
		File mutations_dir = null;
		Map<String, String> config = null;
		EmuModule module = null;
		IModel emfModel = null;

		System.out.println("Start of Modules Mutation Executor:");
		System.out.println("- - - - - - - - - - - - - -");
		for (int i = 0; i < transformations.size(); i++) {
			try {
				clazz = Class.forName(_package + "." + transformations.get(i));
				method = clazz.getMethod("properties");
				config = (Map<String, String>) method.invoke(clazz);
				model = config.get("TRANSFORMATION_DIR") + config.get("TRANSFORMATION_MODULE") + ".xmi";
				mutations_dir = new File(config.get("TRANSFORMATION_DIR") + "/mutation_programs/");
				System.out.println("Module: " + transformations.get(i));
				System.out.println("   |");
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}

			final File mutation_programs[] = mutations_dir.listFiles();
			for (File entry : mutation_programs) {
				try {
					if (!entry.isDirectory()) {
						System.out.println("   -----> " + entry);
						module = new EmuModule();
						module.parse(entry);
						emfModel = createEmfModel("Model", model, metamodel, true, false);
						module.getContext().getModelRepository().addModel(emfModel);
						module.setRepeatWhileMatches(false);
						module.setMaxLoops(0);
						if (module.getParseProblems().size() >= 1) {
							System.out.println("   Parsing Problems: " + module.getParseProblems().toString());
						}
						module.execute();
						module.getContext().getModelRepository().dispose();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("End of Modules Mutation Executor:");

	}

	private static EmfModel createEmfModel(String name, String model, String metamodel, boolean readOnLoad,
			boolean storeOnDisposal) throws EolModelLoadingException, URISyntaxException {
		EmfModel emfModel = new EmfModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name);
		properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, new URI(metamodel).toString());
		properties.put(EmfModel.PROPERTY_MODEL_URI, new URI(model).toString());
		properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal + "");
		emfModel.load(properties, (IRelativePathResolver) null);
		return emfModel;
	}
}
