package uk.ac.york.cs.emu.examples.atl.launcher.qmatrix;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import uk.ac.york.cs.emu.examples.atl.launcher.Oracle;

public class QMatrix implements Serializable {

	private static final long serialVersionUID = -1029244743792316979L;
	private String path;
	private HashMap<String, List<Oracle>> content = new HashMap<String, List<Oracle>>();

	private HashMap<String, List<Oracle>> killedMutantsCache;
	private HashMap<String, List<Oracle>> liveMutantsCache;

	public QMatrix(String path) {
		this.path = path;
	}

	private QMatrix() {
	}

	public boolean saveQMatrix() throws IOException {
		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path)))
		{
			os.writeObject(content);
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean loadQMatrix() throws ClassNotFoundException, IOException {
		try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(path)))
		{
			content = (HashMap<String, List<Oracle>>) is.readObject();
			return true;
		}
	}

	public HashMap<String, List<Oracle>> getContent() {
		return content;
	}

	public List<Oracle> getValue(String key) {
		if (content.get(key) == null)
			content.put(key, new ArrayList<Oracle>());
		return content.get(key);
	}

	public void setPath(String path) {
		this.path = path;
	}

	public HashMap<String, List<Oracle>> getLiveMutants() {
		if (liveMutantsCache == null)
		{
			liveMutantsCache = new HashMap<String, List<Oracle>>();
			Iterator it = content.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry pair = (Map.Entry) it.next();
				List<Oracle> values = (List<Oracle>) pair.getValue();
				for (Oracle oracle : values)
				{
					if (oracle.getResult() == 1)
						liveMutantsCache.put((String) pair.getKey(), (List<Oracle>) pair.getValue());
				}
				it.remove();
			}
		}
		return liveMutantsCache;
	}

	public HashMap<String, List<Oracle>> getKilledMutants() {
		if (killedMutantsCache == null)
		{
			killedMutantsCache = new HashMap<String, List<Oracle>>();
			Iterator it = content.entrySet().iterator();
			while (it.hasNext())
			{
				Map.Entry pair = (Map.Entry) it.next();
				List<Oracle> values = (List<Oracle>) pair.getValue();
				for (Oracle oracle : values)
				{
					if (oracle.getResult() == 0)
						killedMutantsCache.put((String) pair.getKey(), (List<Oracle>) pair.getValue());
				}
				it.remove();
			}
		}
		return killedMutantsCache;
	}

	public void printToConsole() {
		Iterator it = content.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println("Mutant: " + pair.getKey());
			List<Oracle> values = (List<Oracle>) pair.getValue();
			for (Oracle oracle : values)
			{
				System.out.println("------> expected output: " + oracle.getExpectedModel());
				System.out.println("------> actual output: " + oracle.getActualModel());

				if (oracle.getResult() == 0)
					System.out.println("------> Result: killed mutant");
				if (oracle.getResult() == 1)
					System.out.println("------> Result: live mutant");
				System.out.println();
			}
			it.remove();
		}
	}
}
