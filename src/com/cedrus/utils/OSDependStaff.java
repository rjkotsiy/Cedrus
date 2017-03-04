package com.cedrus.utils;

public class OSDependStaff {
	public static final String WINDOWS = "Windows";
	public static final String MAC = "Mac";
	public static final String LINUX = "Linux";

	private static String osName = System.getProperty("os.name");
	public static String getCloseHotKey() {
		
		if (osName.startsWith(WINDOWS)) {
			return "Alt+F4";
		}
		if (osName.startsWith(MAC)) {
			return "Command+W";
		}
		return "Alt+F4";
	}
	
	public static boolean isOsWindows() {
		return osName.startsWith(WINDOWS);
	}

	public static boolean isOsMacOSX() {
		return osName.startsWith(MAC);
	}
	
	public static boolean isOsLinux() {
		return osName.startsWith(LINUX);
	}
	
	public static double getDefaultButtonFontSize() {
		if (osName.startsWith(WINDOWS)) {
			return 12;
		} else if (osName.startsWith(LINUX)) {
			return 11;
		} else {
			return 12;
		}
	}
}
