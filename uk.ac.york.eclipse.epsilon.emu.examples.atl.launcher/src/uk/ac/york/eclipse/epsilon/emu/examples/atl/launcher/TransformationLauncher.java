package uk.ac.york.eclipse.epsilon.emu.examples.atl.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
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

public class TransformationLauncher {

	/**
	 * The IN model.
	 * 
	 * @generated
	 */
	protected IModel inModel;

	/**
	 * The OUT model.
	 * 
	 * @generated
	 */
	protected IModel outModel;

	public void run(String inMMPath, String inMMName, String inMPath, String outMMPath, String outMMName,
			String outMPath, String transDir, String transModule) throws ATLCoreException, IOException {

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
		launcher.initialize(Collections.EMPTY_MAP);
		launcher.addInModel(inModel, "IN", "Book");
		launcher.addOutModel(outModel, "OUT", "Publication");
		launcher.launch("run", new NullProgressMonitor(), Collections.EMPTY_MAP,
				getModulesStream(transDir + transModule));

		// save models
		IExtractor extractor = new EMFExtractor();
		extractor.extract(outModel, outMPath);
	}

	/**
	 * Returns an Array of the module input streams, parameterized by the
	 * property file.
	 * 
	 * @return an Array of the module input streams
	 * @throws IOException
	 *             if a module cannot be read
	 *
	 * @generated
	 */
	protected InputStream getModulesStream(String modulePath) throws IOException {
		InputStream module = null;
		String asmModulePath = modulePath + ".asm";
		module = getFileURL(asmModulePath).openStream();
		return module;
	}

	/**
	 * Finds the file in the plug-in. Returns the file URL.
	 * 
	 * @param fileName
	 *            the file name
	 * @return the file URL
	 * @throws IOException
	 *             if the file doesn't exist
	 * 
	 * @generated
	 */
	protected static URL getFileURL(String fileName) throws IOException {
		final URL fileURL = new URL("file://" + fileName);
		return fileURL;
	}

	/**
	 * Tests if eclipse is running.
	 * 
	 * @return <code>true</code> if eclipse is running
	 *
	 * @generated
	 */
	public static boolean isEclipseRunning() {
		try {
			return Platform.isRunning();
		} catch (Throwable exception) {
			// Assume that we aren't running.
		}
		return false;
	}

}
