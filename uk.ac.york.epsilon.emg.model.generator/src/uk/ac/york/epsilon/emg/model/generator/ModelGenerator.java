package uk.ac.york.epsilon.emg.model.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.csv.CsvModel;
import org.eclipse.epsilon.emc.emf.EmfMetaModel;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import uk.ac.york.epsilon.emg.model.generator.engine.EmgEngine;
import uk.ac.york.epsilon.emg.model.generator.engine.EpsilonException;

public class ModelGenerator {

	/**
	 * Use the Serial Version ID as seed
	 */
	private static final long serialVersionUID = 4449535323017681626L;
	private static final int MAX_MODELS = 20;
	private static final String OUTPUT_DIR = "generated-models";
	private static Map<String, Object> config = null;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		EmgEngine engine = new EmgEngine();

		// Read the metamodels files list
		BufferedReader read = null;
		String line;
		List<String> metamodels = new ArrayList<String>();
		try {
			read = new BufferedReader(new InputStreamReader(ModelGenerator.class.getResource("resources/metamodels.list").openStream()));
			while ((line = read.readLine()) != null) {
				if (!line.startsWith("#"))
					metamodels.add(line);
			}

			Class<?> clazz;
			String _package = ModelGenerator.class.getPackage().getName() + ".resources";
			Method method;

			// go through all metamodels
			for (int i = 0; i < metamodels.size(); i++) {
				clazz = Class.forName(_package + "." + metamodels.get(i) + "." + metamodels.get(i));
				method = clazz.getMethod("properties");
				config = (Map<String, Object>) method.invoke(clazz);

				int min = (int) config.get(PropertyKey.MIN_MODEL_SIZE);
				int model_size = min;
				int numbering = 1;
				int[] power = { 0, 2, 4, 8, 16, 32 };

				int pick;

				Random rand = new Random(serialVersionUID);
				String base_path = OUTPUT_DIR + File.separatorChar + (String) config.get(PropertyKey.OUT_MODEL_FOLDER) + File.separatorChar;
				// check();
				do {
					String model_path = base_path + (String) config.get(PropertyKey.OUT_MODEL_FILE) + "_" + numbering + ".xmi";
					executeGeneration(engine, (String) model_path, model_size, serialVersionUID);
					numbering++;
					pick = power[rand.nextInt(power.length)];
					model_size = pick == 0 ? min : min * pick;
				} while (numbering <= MAX_MODELS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}

	/**
	 * 
	 * @param engine
	 * @param emgPath
	 * @param mm_uri
	 * @param aliase
	 * @param mpath
	 * @param modelsize
	 * @param seed
	 * @throws Exception
	 */
	private static void executeGeneration(EmgEngine engine, String mpath, int modelsize, long seed) throws Exception {
		engine.setSeed(seed);
		URL rEmg = (URL) config.get(PropertyKey.EMG_FILE);
		engine.setSourceURI(java.net.URI.create(rEmg.toString()));
		String aliase = (String) config.get(PropertyKey.METAMODEL_ALIASE);

		if (config.get(PropertyKey.MAIN_METAMODEL_FILE) != null)
			registerMetamodels((String) config.get(PropertyKey.MAIN_METAMODEL_FILE));

		EmfModel genmodel = createEmfModel(aliase, aliase, mpath, (String) config.get(PropertyKey.MAIN_METAMODEL_URI), false, true, false);
		genmodel.setupContainmentChangeListeners();
		engine.addModel(genmodel);
		CsvModel csv_model = createCSVModel("StringDB", ModelGenerator.class.getResource("resources/StringDB.csv").getPath());
		engine.addModel(csv_model);

		if (config.get(PropertyKey.HELP_METAMODELS) != null) {
			registerMetamodels((String[]) config.get(PropertyKey.HELP_METAMODELS_PATH));

			EmfMetaModel[] mms = (EmfMetaModel[]) config.get(PropertyKey.HELP_METAMODELS);
			String[] ms = (String[]) config.get(PropertyKey.HELP_MODELS_PATH);
			for (int j = 0; j < mms.length; j++) {
				if (mms[j].getMetamodelUri().equals(EcorePackage.eNS_URI)) {
					mms[j].load();
					engine.addModel(mms[j]);
				} else {
					IModel m = createEmfModel(mms[j].getName(), mms[j].getName(), ms[j], mms[j].getMetamodelUri(), true, false, false);
					engine.addModel(m);
				}
			}
		}

		engine.addGlobalVariable("total", modelsize);
		try {
			engine.execute();
		} catch (EpsilonException e) {
			e.printStackTrace();
		}
		engine.disposeModels();
		engine.reset();
	}

	private static EmfModel createEmfModel(String name, String aliase, String m_path, String mm_uri, boolean readOnLoad, boolean storeOnDisposal, boolean cached) throws URISyntaxException, EolModelLoadingException {
		EmfModel emfModel = new EmfModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name);
		properties.put(EmfModel.PROPERTY_ALIASES, aliase);
		properties.put(EmfModel.PROPERTY_METAMODEL_URI, mm_uri);
		properties.put(EmfModel.PROPERTY_MODEL_URI, m_path);
		properties.put(EmfModel.PROPERTY_READONLOAD, (Object) readOnLoad);
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, (Object) storeOnDisposal);
		properties.put(EmfModel.PROPERTY_CACHED, (Object) cached);
		emfModel.load(properties, (IRelativePathResolver) null);
		return emfModel;
	}

	private static CsvModel createCSVModel(String label, Object stringDBFile) throws URISyntaxException, EolModelLoadingException {
		CsvModel model = new CsvModel();
		StringProperties properties = new StringProperties();
		properties.put(CsvModel.PROPERTY_NAME, label);
		properties.put(CsvModel.PROPERTY_FILE, stringDBFile);
		properties.put(CsvModel.PROPERTY_FIELD_SEPARATOR, ",");
		properties.put(CsvModel.PROPERTY_HAS_KNOWN_HEADERS, true);
		properties.put(CsvModel.PROPERTY_READONLOAD, true);
		properties.put(CsvModel.PROPERTY_STOREONDISPOSAL, false);
		model.load(properties);
		return model;
	}

	private static void registerMetamodels(String path) throws Exception {
		String[] paths = { path };
		registerMetamodels(paths);
	}

	private static void registerMetamodels(String[] paths) throws Exception {
		EcorePackage.eINSTANCE.eClass();
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		ResourceSet rs = new ResourceSetImpl();
		Resource r = null;
		EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		org.eclipse.emf.common.util.URI uri = null;
		if (paths != null) {
			List<EObject> ps = new ArrayList<EObject>();
			for (int i = 0; i < paths.length; i++) {
				uri = org.eclipse.emf.common.util.URI.createURI(new File(paths[i]).getAbsolutePath());
				r = rs.createResource(uri);
				r.load(null);
				getAllEPackages(r.getContents().get(0), ps);
				if (ps.size() == 0)
					throw new Exception("Unable to find EPackage in resource " + r.getURI());
				for (Object o : ps) {
					if (!(o instanceof EPackage))
						throw new Exception("Invalid EPackage object" + o);
					EPackage p = (EPackage) o;
					EPackage.Registry.INSTANCE.put(p.getNsURI(), p);
				}
			}
		}
	}

	private static void getAllEPackages(EObject o, List<EObject> ps) {
		boolean contain = false;
		if (o instanceof EPackage) {
			for (EObject eo : o.eContents()) {
				if (eo instanceof EPackage) {
					contain = true;
					getAllEPackages(eo, ps);
				}
			}
			if (!contain && !ps.contains(o))
				ps.add(o);
		}
	}
}
