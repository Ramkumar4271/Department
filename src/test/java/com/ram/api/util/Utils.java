package com.ram.api.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

	public static String convertJsonFileToString(String file) {
		try {
			return new String(Files.readAllBytes(Paths.get(file)));
		} catch (IOException e) {
			return null;
		}
	}
}
