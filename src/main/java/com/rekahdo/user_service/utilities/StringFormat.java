package com.rekahdo.user_service.utilities;

public class StringFormat {

	private static String replaceWith(String str, String find, String replace) {
		if(str == null || str.trim().isEmpty()) return "";
		return str.trim().replaceAll(find, replace);
	}

	public static String removeWhiteSpace(String str) {
		return replaceWith(str, "\\s", "");
	}

	public static String removeUnderscore(String str) {
		return replaceWith(str, "_", " ");
	}

	public static String[] splitByComma(String str) {
		return splitBy(str, ",");
	}

	public static String[] splitByHyphen(String str) {
		return splitBy(str, "-");
	}

	private static String[] splitBy(String str, String by) {
		return removeWhiteSpace(str).split(by);
	}

	public static String join(String[] array) {
		return String.join(",", array);
	}

	public static String join(String str) {
		return join(splitByComma(str));
	}

	public static boolean isLowercase(String str) {
		return str.chars().allMatch(Character::isLowerCase);
	}

	public static boolean isUppercase(String str) {
		return str.chars().allMatch(Character::isUpperCase);
	}

	public static boolean hasValue(String str) {
		return str != null && !str.trim().isEmpty();
	}


}
