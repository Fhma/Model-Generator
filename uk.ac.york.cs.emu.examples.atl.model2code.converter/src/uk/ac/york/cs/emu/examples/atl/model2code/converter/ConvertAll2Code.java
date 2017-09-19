package uk.ac.york.cs.emu.examples.atl.model2code.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uk.ac.york.cs.m2m.atl.model2code.converter.AtlModel2Code;

public class ConvertAll2Code {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		BufferedReader read = null;
		String line;
		List<String> transformations = new ArrayList<String>();
		try
		{
			read = new BufferedReader(new InputStreamReader(ConvertAll2Code.class.getResource("files/transformation.list").openStream()));
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

		Class<?> clazz;
		String _package = ConvertAll2Code.class.getPackage().getName() + ".files";
		Method method;
		File mutations_dir = null;
		Map<String, String> config = null;

		for (int i = 0; i < transformations.size(); i++)
		{
			try
			{
				clazz = Class.forName(_package + "." + transformations.get(i));
				method = clazz.getMethod("properties");
				config = (Map<String, String>) method.invoke(clazz);
				mutations_dir = new File(config.get("TRANSFORMATION_DIR") + "/mutation_programs/");
			} catch (Exception e)
			{
				e.printStackTrace();
				break;
			}

			final File mutation_programs[] = mutations_dir.listFiles();
			for (File entry : mutation_programs)
			{
				convertAll(entry);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private static void convertAll(final File entry) {
		if (!entry.isDirectory() && entry.getAbsolutePath().endsWith(".xmi"))
		{
			try
			{
				AtlModel2Code.run(entry.toURL());
				return;
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		} else if (entry.isDirectory())
		{
			for (final File fileEntry : entry.listFiles())
			{
				convertAll(fileEntry);
			}
		}
	}
}
