package es.sendit2us.wastetracker.server.lib;

import com.google.gson.Gson;

public class JSon {

	public static String serialize(Object o) {
		Gson gson = new Gson();
		return gson.toJson(o);
	}

	@SuppressWarnings("unchecked")
	public static Object unserialize(String s, Class z) {
		Gson gson = new Gson();
		return gson.fromJson(s, z);		
	}
}
