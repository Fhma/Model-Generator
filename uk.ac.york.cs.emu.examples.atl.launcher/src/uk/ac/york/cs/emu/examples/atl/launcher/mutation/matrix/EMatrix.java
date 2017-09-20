package uk.ac.york.cs.emu.examples.atl.launcher.mutation.matrix;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

import uk.ac.york.cs.emu.examples.atl.launcher.Oracle;

public class EMatrix implements Serializable {

	private static final long serialVersionUID = 1L;
	private String path;
	private HashMap<String, List<Oracle>> content = new HashMap<String, List<Oracle>>();

	public EMatrix(String path) {
		this.path = path;
	}

	public boolean saveMatrix() throws IOException {
		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(path + getFileExtension())))
		{
			os.writeObject(content);
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public boolean loadMatrix() throws ClassNotFoundException, IOException {
		try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(path + getFileExtension())))
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

	private String getFileExtension() {
		return ".emtr";
	}

	public void toJson() throws IOException {
		if (content == null)
			return;
		JSONObject json = new JSONObject();
		Iterator<Map.Entry<String, List<Oracle>>> it = content.entrySet().iterator();
		while (it.hasNext())
		{
			Map.Entry<String, List<Oracle>> pair = it.next();
			List<Oracle> values = pair.getValue();
			JSONArray list = new JSONArray();
			list.put(values);
			json.put(pair.getKey(), list);
		}
		
		try (FileWriter file = new FileWriter(path + ".json"))
		{
			file.write(json.toString(3));
		}
	}
}