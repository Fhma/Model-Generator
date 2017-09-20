package uk.ac.york.cs.emu.examples.atl.launcher;

import java.io.Serializable;

public class Oracle implements Serializable {
	private static final long serialVersionUID = -1739347007332109476L;
	private String expectedModel;
	private String actualModel;
	private boolean killed;

	public Oracle(String expected_output, String actual_output, boolean _killed) {
		expectedModel = expected_output;
		actualModel = actual_output;
		killed = _killed;
	}

	public String getExpectedModel() {
		return expectedModel;
	}

	public String getActualModel() {
		return actualModel;
	}

	public boolean isKilled() {
		return killed;
	}
}