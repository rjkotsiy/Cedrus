package com.cedrus.utils;

public class StringsUtils {

	private StringsUtils() {
	}

	private static String capitalize(final String line) {
		return Character.toUpperCase(line.charAt(0)) + line.substring(1);
	}

	public static String arrayToString(String[] stringArray, String string) {
		StringBuilder builder = new StringBuilder();

		for (String str : stringArray) {
			builder.append(str + string);
		}
		return builder.toString();
	}
}
