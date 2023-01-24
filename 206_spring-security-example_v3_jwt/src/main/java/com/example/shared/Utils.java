package com.example.shared;

import java.util.UUID;

public class Utils {

	public static String generateUserId() {
		return UUID.randomUUID().toString();
	}
}
