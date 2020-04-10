package io.youcham.common.utils;

public class NumToString {
	public static String numToString(int num) {
		String str = "";
		if ((num + str).length() == 1) {
			str = "000" + num;
		} else if ((num + str).length() == 2) {
			str = "00" + num;
		} else if ((num + str).length() == 3) {
			str = "0" + num;
		} else {
			str = num + str;
		}
		return str;
	}
}
