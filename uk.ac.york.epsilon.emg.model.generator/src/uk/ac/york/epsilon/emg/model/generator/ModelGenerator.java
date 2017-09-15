package uk.ac.york.epsilon.emg.model.generator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.csv.CsvModel;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import uk.ac.york.epsilon.emg.model.generator.engine.EmgEngine;
import uk.ac.york.epsilon.emg.model.generator.engine.EpsilonException;

/**
 * The Class ModelGenerator.
 */
public class ModelGenerator {

	/**
	 * Use the Serial Version ID as seed
	 */
	private static final long serialVersionUID = 4449535323017681626L;
	private static final int MAX_MODELS = 10;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		RandomDataGenerator rnd = new RandomDataGenerator();
		rnd.reSeed(serialVersionUID);
		EmgEngine engine = new EmgEngine();

		// Read the metamodels files list
		BufferedReader read = null;
		String line;
		List<String> metamodels = new ArrayList<String>();
		try
		{
			read = new BufferedReader(new InputStreamReader(ModelGenerator.class.getResource("resources/metamodels.list").openStream()));
			while ((line = read.readLine()) != null)
			{
				if (!line.startsWith("#"))
					metamodels.add(line);
			}

			Class<?> clazz;
			String _package = ModelGenerator.class.getPackage().getName() + ".resources";
			Method method;
			Map<String, Object> config = null;

			// go through all metamodels
			for (int i = 0; i < metamodels.size(); i++)
			{
				clazz = Class.forName(_package + "." + metamodels.get(i) + "." + metamodels.get(i));
				method = clazz.getMethod("properties");
				config = (Map<String, Object>) method.invoke(clazz);

				int model_size = (int) config.get("MIN_MODEL_SIZE");
				int max_iteration = model_size * 125;
				do
				{
					// create all models
					for (int j = 1; j <= MAX_MODELS; j++)
					{
						String model_path = (String) config.get("MODEL_BASE_NAME");
						model_path += (model_size / 10 == 0) ? "0" + model_size : model_size;
						model_path += "_";
						model_path += (j / 10 == 0) ? "0" + j : j;
						model_path += ".xmi";
						executeGeneration(engine, config.get("EMG_FILE"), config.get("METAMODEL"), model_path, model_size, rnd.nextLong(0, Long.MAX_VALUE));
					}
					model_size *= 5;
				} while (model_size <= max_iteration);

			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return;
		}
	}

	private static void executeGeneration(EmgEngine engine, Object emgPath, Object mmpath, String mpath, int modelsize, long seed)
			throws EolModelLoadingException, URISyntaxException {
		engine.setSeed(seed);
		// URL r = ModelGenerator.class.getResource("resources/Abstract.emg");
		URL r = (URL) emgPath;
		engine.setSourceURI(java.net.URI.create(r.toString()));
		EmfModel genmodel = createEmfModel("Model", mpath, mmpath, false, true);
		CsvModel csv_model = createCSVModel("StringDB", ModelGenerator.class.getResource("resources/StringDB.csv").getPath());
		genmodel.setupContainmentChangeListeners();
		engine.addModel(genmodel);
		engine.addModel(csv_model);
		engine.addGlobalVariable("total", modelsize);
		try
		{
			engine.execute();
		} catch (EpsilonException e)
		{
			e.printStackTrace();
		}
		System.out.println("Final size: " + engine.getResult());
		engine.disposeModels();
		engine.reset();
	}

	private static EmfModel createEmfModel(String name, String model, Object metamodel, boolean readOnLoad, boolean storeOnDisposal)
			throws URISyntaxException, EolModelLoadingException {
		EmfModel emfModel = new EmfModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name);
		properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, metamodel);
		properties.put(EmfModel.PROPERTY_MODEL_URI, new URI(model).toString());
		properties.put(EmfModel.PROPERTY_READONLOAD, readOnLoad + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, storeOnDisposal + "");
		emfModel.load(properties, (IRelativePathResolver) null);
		return emfModel;
	}

	private static CsvModel createCSVModel(String label, Object stringDBFile) throws URISyntaxException, EolModelLoadingException {
		CsvModel model = new CsvModel();
		StringProperties properties = new StringProperties();
		properties.put(CsvModel.PROPERTY_NAME, label);
		properties.put(CsvModel.PROPERTY_FILE, stringDBFile);
		properties.put(CsvModel.PROPERTY_FIELD_SEPARATOR, "#");
		properties.put(CsvModel.PROPERTY_HAS_KNOWN_HEADERS, true);
		properties.put(CsvModel.PROPERTY_READONLOAD, true);
		properties.put(CsvModel.PROPERTY_STOREONDISPOSAL, false);
		model.load(properties);
		return model;
	}
}
