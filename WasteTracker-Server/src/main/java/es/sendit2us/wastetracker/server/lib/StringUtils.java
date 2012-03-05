package es.sendit2us.wastetracker.server.lib;

public class StringUtils {

	public static Integer[] stringToArray(String pStr) {
		if(pStr == null) {
			return new Integer[] {};
		}
		pStr = pStr.trim();
		if("".equals(pStr)) {
			return new Integer[] {};
		}
		String[] ps = pStr.trim().split(",");
		Integer[] ints = new Integer[ps.length];
		for(int i = 0; i < ps.length; i++) {
			ints[i] = Integer.valueOf(ps[i].trim());
		}
		return ints;
	}

}
