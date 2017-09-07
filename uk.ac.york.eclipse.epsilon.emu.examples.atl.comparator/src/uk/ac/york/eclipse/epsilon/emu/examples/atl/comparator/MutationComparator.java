package uk.ac.york.eclipse.epsilon.emu.examples.atl.comparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.net.URI;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.emf.compare.AttributeChange;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.ReferenceChange;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.epsilon.eunit.dt.cmp.emf.v3.EMFModelComparator;

public class MutationComparator {

	@SuppressWarnings({ "resource", "unchecked" })
	public static void main(String[] args) {
		EMFModelComparator comparator = null;
		BufferedReader read = null;
		String line;
		List<String> transformations = new ArrayList<String>();
		try {
			read = new BufferedReader(
					new FileReader(MutationComparator.class.getResource("files/transformation.list").getPath()));
			while ((line = read.readLine()) != null) {
				if (!line.startsWith("#"))
					transformations.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		Class<?> clazz;
		String _package = MutationComparator.class.getPackage().getName() + ".files";
		Method method;
		String original_model = null;
		File mutations_dir = null;
		Map<String, String> config = null;
		File logger = null;
		BufferedWriter wr;

		System.out.println("Start of Modules Mutation Comparator:");
		System.out.println("- - - - - - - - - - - - - -");
		for (int i = 0; i < transformations.size(); i++) {
			try {
				clazz = Class.forName(_package + "." + transformations.get(i));
				method = clazz.getMethod("properties");
				config = (Map<String, String>) method.invoke(clazz);
				original_model = config.get("TRANSFORMATION_DIR") + config.get("TRANSFORMATION_MODULE") + ".xmi";
				mutations_dir = new File(config.get("TRANSFORMATION_DIR") + "/mutation_programs/");
				comparator = new EMFModelComparator();

				String metamodel = "/Users/AFADF_F/Git/Fhma/runtime-EclipseApplication/uk.ac.york.eclipse.epsilon.emu.examples.atl/ATL.ecore";
				IModel model1 = createEmfModel(getModelName(original_model), original_model, metamodel, true, false);

				System.out.println(
						"Module: " + transformations.get(i) + ", original model: " + model1.getName() + "\n\t|");

				final File mutations_folders[] = mutations_dir.listFiles();
				IModel model2 = null;

				logger = new File(mutations_dir + "/comparison_result.txt");
				wr = new BufferedWriter(new FileWriter(logger));
				String log = "Module: " + transformations.get(i) + "\n";
				log += "Original Model: " + original_model + "\n";
				log += "\t|" + "\n";
				for (File entry : mutations_folders) {
					if (entry.isDirectory()) {

						// obtain all mutants outputs of one mutation program
						File[] mutants = entry.listFiles();
						for (int j = 0; j < mutants.length; j++) {
							if (!mutants[j].isDirectory()) {
								String path = mutants[j].getAbsolutePath();
								model2 = createEmfModel(getModelName(path), path, metamodel, true, false);
								Comparison result = null;
								if (comparator.canCompare(model1, model2)) {
									result = (Comparison) comparator.compare(model1, model2);
									System.out.println("   -----> mutant : " + model2.getName() + ", differences = "
											+ result.getDifferences().size());
									log += "\t|----> mutant: " + path + "\n";
									log += "\t|--------> differences (" + result.getDifferences().size() + "): \n";
									for (Diff diff : result.getDifferences()) {
										String str = diffToString(diff);
										System.out.println("\t  " + str);
										log += "\t|\t\t " + str + "\n";
									}
								}
							}
						}
					}
				}
				log += "\t- - - - - - - - - - - - - - - - - - - - - -\n";
				wr.write(log);
				wr.flush();
				wr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // end each transformation module

	}// end main

	private static String getModelName(String path) {
		return path.substring(path.lastIndexOf("/") + 1, path.length());
	}

	private static EmfModel createEmfModel(String name, String model, String metamodel, boolean readOnLoad,
			boolean storeOnDisposal) {
		EmfModel emfModel = null;
		try {
			emfModel = new EmfModel();
			StringProperties properties = new StringProperties();
			properties.put(EmfModel.PROPERTY_NAME, name);
			properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, new URI(metamodel).toString());
			properties.put(EmfModel.PROPERTY_MODEL_URI, new URI(model).toString());
			properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
			properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal + "");
			emfModel.load(properties, (IRelativePathResolver) null);

		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (EolModelLoadingException e) {
			e.printStackTrace();
		}
		return emfModel;
	}

	private static String diffToString(Diff diff) {
		if (diff instanceof AttributeChange) {
			AttributeChange change = (AttributeChange) diff;
			return "[Attribute Change {feature= " + change.getAttribute().getEContainingClass().getName() + "."
					+ change.getAttribute().getName() + ", kind=" + diff.getKind().getName() + "}]";
		}
		if (diff instanceof ReferenceChange) {
			ReferenceChange change = (ReferenceChange) diff;
			return "[Reference Change {feature= " + change.getReference().getEContainingClass().getName() + "."
					+ change.getReference().getName() + ", kind=" + diff.getKind().getName() + "}]";
		}
		return null;
	}
}
