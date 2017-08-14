package com.jpmc.trade.engine.util.json;

public class JSONArray {

	private Object[] array;

	public JSONArray(Object[] array) {
		this.array = array;
	}

	public int length() {
		return array.length;
	}

	public Object get(int index) {
		return array[index];
	}

	public String getString(int index) {
		return (String) array[index];
	}

	public Boolean getBoolean(int index) {
		return (Boolean) array[index];
	}

	public Integer getInteger(int index) {
		return (Integer) array[index];
	}

	public Float getFloat(int index) {
		return (Float) array[index];
	}

	// public Double getDouble(int index) {
	// return (Double) array[index];
	// }

	public JSONObject getObject(int index) {
		return new JSONObject(array[index]);
	}

	public JSONArray getArray(int index) {
		return new JSONArray((Object[]) array[index]);
	}

}
