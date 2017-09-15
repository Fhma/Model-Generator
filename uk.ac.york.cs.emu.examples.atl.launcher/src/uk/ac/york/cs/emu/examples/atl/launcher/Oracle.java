package uk.ac.york.cs.emu.examples.atl.launcher;

import java.io.Serializable;

public class Oracle implements Serializable {
	private static final long serialVersionUID = -1739347007332109476L;
	private String expectedModel;
	private String actualModel;
	private int result;

	public Oracle(String expected_output, String actual_output, int _result) {
		expectedModel = expected_output;
		actualModel = actual_output;
		result = _result;
	}

	public boolean isExpectedModelDefined() {
		return expectedModel == null;
	}

	public boolean isActualModelDefined() {
		return actualModel == null;
	}

	public String getExpectedModel() {
		return expectedModel;
	}

	public String getActualModel() {
		return actualModel;
	}

	public String getOracle() {
		return "<" + expectedModel + "," + actualModel + ">";
	}

	public int getResult() {
		return result;
	}
}