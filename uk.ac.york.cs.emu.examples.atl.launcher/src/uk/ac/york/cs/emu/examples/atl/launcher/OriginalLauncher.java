package uk.ac.york.cs.emu.examples.atl.launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.m2m.atl.core.IExtractor;
import org.eclipse.m2m.atl.core.IInjector;
import org.eclipse.m2m.atl.core.IModel;
import org.eclipse.m2m.atl.core.IReferenceModel;
import org.eclipse.m2m.atl.core.ModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFExtractor;
import org.eclipse.m2m.atl.core.emf.EMFInjector;
import org.eclipse.m2m.atl.core.emf.EMFModelFactory;
import org.eclipse.m2m.atl.core.launch.ILauncher;
import org.eclipse.m2m.atl.engine.emfvm.launch.EMFVMUILauncher;

public class OriginalLauncher {

	protected IModel inModel;
	protected IModel outModel;

	public static void main(String[] args) {
		BufferedReader read = null;
		String line;
		List<String> transformations = new ArrayList<String>();
		try
		{
			read = new BufferedReader(new InputStreamReader(OriginalLauncher.class.getResource("files/transformation.list").openStream()));
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

		String inModelsFolder = "inModels/";
		String outModelsFolder = "expectedModels/";
		OriginalLauncher exe;
		Class<?> clazz;
		Method method;
		File folder = null;
		String _package = OriginalLauncher.class.getPackage().getName() + ".files";
		Map<String, String> config;

		System.out.println("Start of Modules Transformation Launcher:");
		System.out.println("- - - - - - - - - - - - - -");
		for (int i = 0; i < transformations.size(); i++)
		{
			try
			{
				clazz = Class.forName(_package + "." + transformations.get(i));
				method = clazz.getMethod("properties");
				config = (Map<String, String>) method.invoke(clazz);
				folder = new File(inModelsFolder + transformations.get(i));
				System.out.println("Module: " + transformations.get(i));
				System.out.println("   |");
			} catch (Exception e)
			{
				e.printStackTrace();
				break;
			}

			for (File entry : folder.listFiles())
			{
				System.out.println("   -----> " + entry);
				String input_file = entry.getName();
				String output_file = input_file.substring(0, input_file.length() - 4);
				output_file = outModelsFolder + transformations.get(i) + "/" + output_file + "_result2" + config.get("OUT_METAMODEL_NAME") + ".xmi";
				exe = new OriginalLauncher();
				try
				{
					new OriginalLauncher().run(config.get("IN_METAMODEL"), config.get("IN_METAMODEL_NAME"), entry.getPath(), config.get("OUT_METAMODEL"),
							config.get("OUT_METAMODEL_NAME"), output_file, config.get("TRANSFORMATION_DIR"), config.get("TRANSFORMATION_MODULE"),
							config.get("TRANSFORMATION_HELPERS"));
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			System.out.println("   - - - - - - - - - - - - - -");
		}
		System.out.println("End of Modules Transformation Launcher.");
	}

	/**
	 * Executes a given ATL module programmatically
	 * 
	 * @param inMMPath
	 *            path of input metamodel (*.ecore)
	 * @param inMMName
	 *            name alias to input metamodel
	 * @param inMPath
	 *            path of input model that conforms to input metamodel
	 * @param outMMPath
	 *            path of output metamodel (*.ecore)
	 * @param outMMName
	 *            name alias to output metamodel
	 * @param outMPath
	 *            path of output model that conforms to output metamodel
	 * @param transDir
	 *            path to the transformation directory that has ATL nature
	 * @param transModule
	 *            name of main transformation module
	 * @param helpers
	 *            list of names of helpers (seprated by commas) needed by the main transformation
	 *            module
	 * @throws ATLCoreException
	 * @throws IOException
	 */
	public void run(String inMMPath, String inMMName, String inMPath, String outMMPath, String outMMName, String outMPath, String transDir, String transModule, String helpers)
			throws ATLCoreException, IOException {

		// load models
		ModelFactory factory = new EMFModelFactory();
		IInjector injector = new EMFInjector();
		IReferenceModel inMetamodel = factory.newReferenceModel();
		injector.inject(inMetamodel, inMMPath);
		IReferenceModel outMetamodel = factory.newReferenceModel();
		injector.inject(outMetamodel, outMMPath);
		this.inModel = factory.newModel(inMetamodel);
		injector.inject(inModel, inMPath);
		this.outModel = factory.newModel(outMetamodel);

		// run transformation
		ILauncher launcher = (ILauncher) new EMFVMUILauncher();
		launcher.initialize(Collections.emptyMap());
		launcher.addInModel(inModel, "IN", inMMName);
		launcher.addOutModel(outModel, "OUT", outMMName);
		launcher.launch(ILauncher.RUN_MODE, new NullProgressMonitor(), Collections.emptyMap(), (Object[]) getModulesStream(transDir, transModule, helpers));

		// save models
		IExtractor extractor = new EMFExtractor();
		extractor.extract(outModel, outMPath);
	}

	/**
	 * Returns a list of InputStream that is needed to run this transformation module
	 * 
	 * @param trans_dir
	 *            path to transformation directory
	 * @param module_name
	 *            name of main transformation module
	 * @param helpers
	 *            list of helpers of main transformation module
	 * 
	 * @return an array list of InputStream needed by the ATL launcher
	 * @throws IOException
	 *             if a module cannot be read
	 */
	private InputStream[] getModulesStream(String trans_dir, String module_name, String helpers) throws IOException {

		List<InputStream> modules = new ArrayList<InputStream>();
		// fetch the compiled file of main module
		if (module_name != null)
		{
			modules.add(getFileURL(new Path(trans_dir + module_name.trim()).addFileExtension("asm").toString()).openStream());
		}

		// fetch compiled files of associated helpers
		if (helpers != null)
		{
			String helpers_list[] = helpers.split(",");
			for (int i = 0; i < helpers_list.length; i++)
			{
				modules.add(getFileURL(new Path(trans_dir + helpers_list[i].trim()).addFileExtension("asm").toString()).openStream());
			}
		}
		InputStream returned_streams[] = new InputStream[modules.size()];
		for (int i = 0; i < returned_streams.length; i++)
		{
			returned_streams[i] = modules.get(i);
		}
		return returned_streams;
	}

	/**
	 * Returns the file URL name representation of a given path
	 * 
	 * @param fileName
	 *            the file name
	 * @return the file URL
	 * @throws IOException
	 *             if the file doesn't exist
	 */
	private static URL getFileURL(String fileName) throws IOException {
		final URL fileURL = new URL("file://" + fileName);
		return fileURL;
	}
}
