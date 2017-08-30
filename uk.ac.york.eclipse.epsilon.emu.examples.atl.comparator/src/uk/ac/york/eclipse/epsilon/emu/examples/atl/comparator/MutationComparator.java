package uk.ac.york.eclipse.epsilon.emu.examples.atl.comparator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class MutationComparator {

	public static void main(String[] args) {
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
		File diff = null;
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
				diff = new File(mutations_dir + "differences.txt");
				wr = new BufferedWriter(new FileWriter(diff));
				System.out.println("Module: " + transformations.get(i));
				System.out.println("   |");
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
			ResourceSet resourceSet1 = new ResourceSetImpl();
			resourceSet1.getResource(URI.createFileURI(original_model), true);

			final File mutations_dirs[] = mutations_dir.listFiles();
			for (File entry : mutations_dirs) {
				try {
					if (entry.isDirectory()) {
						File[] mutations = entry.listFiles();
						for (int j = 0; j < mutations.length; j++) {
							if (!mutations[j].isDirectory()) {
								System.out.println("   -----> " + mutations[j]);
								ResourceSet resourceSet2 = new ResourceSetImpl();
								resourceSet2.getResource(URI.createFileURI(mutations[j].getAbsolutePath()), true);

								IComparisonScope scope = new DefaultComparisonScope(resourceSet1, resourceSet2, null);
								Comparison comparison = EMFCompare.builder().build().compare(scope);

								List<Diff> differences = comparison.getDifferences();
								System.out.println("          " + differences.size() + " differences exist");
								wr.write("original: " + original_model + "\n");
								wr.write("mutant: " + mutations[j] + "\n");
								wr.write("differences: " + differences.toString() + "\n");
								wr.write("- - - - - - - - - - - - - -\n");
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				wr.close();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		System.out.println("End of Modules Mutation Executor:");
	}
}
