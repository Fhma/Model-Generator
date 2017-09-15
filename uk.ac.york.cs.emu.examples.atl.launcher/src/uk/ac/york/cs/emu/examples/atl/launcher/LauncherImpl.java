package uk.ac.york.cs.emu.examples.atl.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

public class LauncherImpl {

	protected IModel inModel;
	protected IModel outModel;

	public LauncherImpl() {
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
	 *            path to the transformation directory that has ATL project nature
	 * @param transModule
	 *            name of main transformation module
	 * @param helpers
	 *            list of names of helpers (separated by commas) needed by the main transformation
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

		// save out models
		IExtractor extractor = new EMFExtractor();
		extractor.extract(outModel, outMPath);
	}

	public void run(String inMMPath, String inMMName, String inMPath, String outMMPath, String outMMName, String outMPath, String transFile, String helpers)
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
		launcher.launch(ILauncher.RUN_MODE, new NullProgressMonitor(), Collections.emptyMap(), (Object[]) getModulesStream(transFile, helpers));

		// save out models
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

	private InputStream[] getModulesStream(String trans_file, String helpers) throws IOException {

		List<InputStream> modules = new ArrayList<InputStream>();
		// fetch the compiled file of main module
		if (trans_file != null && trans_file.endsWith(".atl"))
		{
			String asmFile = trans_file.substring(0, trans_file.length() - 4);
			modules.add(getFileURL(new Path(asmFile).addFileExtension("asm").toString()).openStream());
		}

		String path = trans_file.substring(0, trans_file.lastIndexOf("/"));

		// fetch compiled files of associated helpers
		if (helpers != null)
		{
			String helpers_list[] = helpers.split(",");
			for (int i = 0; i < helpers_list.length; i++)
			{
				modules.add(getFileURL(new Path(path + helpers_list[i].trim()).addFileExtension("asm").toString()).openStream());
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
	private URL getFileURL(String fileName) throws IOException {
		final URL fileURL = new URL("file://" + fileName);
		return fileURL;
	}

	public IModel getInModel() {
		return inModel;
	}

	public IModel getOutModel() {
		return outModel;
	}
}
