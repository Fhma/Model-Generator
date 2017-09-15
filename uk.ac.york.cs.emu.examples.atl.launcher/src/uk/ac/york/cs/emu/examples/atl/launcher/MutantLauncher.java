package uk.ac.york.cs.emu.examples.atl.launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.epsilon.eunit.dt.cmp.emf.v3.EMFModelComparator;
import uk.ac.york.cs.emu.examples.atl.launcher.Oracle;
import uk.ac.york.cs.emu.examples.atl.launcher.qmatrix.QMatrix;

public class MutantLauncher {

	public static void main(String[] args) {
		BufferedReader read = null;
		String line;
		List<String> transformations = new ArrayList<String>();
		try
		{
			read = new BufferedReader(new InputStreamReader(MutantLauncher.class.getResource("files/transformation.list").openStream()));
			while ((line = read.readLine()) != null)
			{
				if (!line.startsWith("#"))
					transformations.add(line);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}

		// mutant execution input models
		String inModelsFolder = "inModels/";
		// mutant execution output models (actual outputs)
		String actualOutModelsFolder = "actualModels/";
		// original transformation execution output models
		String expectedOutModelsFolder = "expectedModels/";

		Class<?> clazz;
		Method method;
		String _package = MutantLauncher.class.getPackage().getName() + ".files";
		Map<String, String> config;
		EMFModelComparator comparator = null;

		File main_mutants_folder = null;

		System.out.println("Start of Mutants Transformation Launcher:");
		System.out.println("- - - - - - - - - - - - - - - - - - - - -");

		LauncherImpl Atllauncher = null;

		for (int i = 0; i < transformations.size(); i++)
		{
			try
			{
				clazz = Class.forName(_package + "." + transformations.get(i));
				method = clazz.getMethod("properties");
				config = (Map<String, String>) method.invoke(clazz);
				main_mutants_folder = new File(config.get("TRANSFORMATION_DIR") + "mutation_programs/");

				System.out.println("Module: " + transformations.get(i));
				System.out.println("   |");
			} catch (Exception e)
			{
				e.printStackTrace();
				break;
			}

			// used for comparing between actual and expected outputs of mutant execution
			String metamodel = config.get("OUT_METAMODEL");

			// Initialise quality matrix
			QMatrix matrix = new QMatrix(actualOutModelsFolder + config.get("TRANSFORMATION_MODULE") + ".qmtr");

			File input_folder = new File(inModelsFolder + config.get("TRANSFORMATION_MODULE"));
			for (File entry : main_mutants_folder.listFiles())
			{
				if (!entry.isFile())
				{
					// each mutation program generate a folder with all mutants
					for (File mutant : entry.listFiles())
					{
						if (mutant.getName().endsWith(".atl"))
						{
							System.out.println("   -----> " + mutant);

							// read input models for transformation
							for (File input_model : input_folder.listFiles())
							{
								String input_model_name = input_model.getName();
								String expected_model_path = input_model_name.substring(0, input_model_name.length() - 4);
								expected_model_path = expectedOutModelsFolder + transformations.get(i) + "/" + expected_model_path + "_result2" + config.get("OUT_METAMODEL_NAME")
										+ ".xmi";
								String actual_model_path = input_model_name.substring(0, input_model_name.length() - 4);
								actual_model_path = actualOutModelsFolder + transformations.get(i) + "/" + mutant.getName() + "/" + actual_model_path + "_result2"
										+ config.get("OUT_METAMODEL_NAME") + ".xmi";

								Atllauncher = new LauncherImpl();
								try
								{
									// run mutant
									Atllauncher.run(config.get("IN_METAMODEL"), config.get("IN_METAMODEL_NAME"), input_model.getPath(), config.get("OUT_METAMODEL"),
											config.get("OUT_METAMODEL_NAME"), actual_model_path, mutant.getPath(), config.get("TRANSFORMATION_HELPERS"));
									// valid mutant compare actual and expected outputs
									IModel lhs_model = createEmfModel("lhs Model", expected_model_path, metamodel, true, false);
									IModel rhs_model = createEmfModel("rhs Model", actual_model_path, metamodel, true, false);
									comparator = new EMFModelComparator();
									Comparison result = null;

									if (comparator.canCompare(lhs_model, rhs_model))
									{
										result = (Comparison) comparator.compare(lhs_model, rhs_model);
										if (result != null)
										{
											// killed mutant
											matrix.getValue(mutant.getName()).add(new Oracle(expected_model_path, actual_model_path, 0));
										} else
										{
											// live mutant
											matrix.getValue(mutant.getName()).add(new Oracle(expected_model_path, actual_model_path, 1));
										}
									} else
									{
										throw new IllegalArgumentException("Not comparable [ " + expected_model_path + "," + actual_model_path + " ]");
									}
								} catch (IOException e)
								{
									e.printStackTrace();
								} catch (EolModelLoadingException e)
								{
									e.printStackTrace();
								} catch (URISyntaxException e)
								{
									e.printStackTrace();
								} catch (Exception e)
								{
									// killed mutant
									matrix.getValue(mutant.getName()).add(new Oracle(expected_model_path, null, 0));
								}
							}
						}
					}
				}
			}
			try
			{
				matrix.saveQMatrix();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			matrix.printToConsole();
			System.out.println("   - - - - - - - - - - - - - -");
		}
		System.out.println("End of Mutants Transformation Launcher.");
	}

	private static EmfModel createEmfModel(String name, String model, String metamodel, boolean readOnLoad, boolean storeOnDisposal)
			throws URISyntaxException, EolModelLoadingException {
		EmfModel emfModel = null;

		emfModel = new EmfModel();
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
