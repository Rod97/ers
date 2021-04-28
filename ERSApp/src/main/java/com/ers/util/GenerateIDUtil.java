package com.ers.util;

import java.util.UUID;

public class GenerateIDUtil {
	public static Long generateNewEmployeeID() {

		Long id = 0L;
		UUID temp = UUID.randomUUID();

		id = temp.getMostSignificantBits();
		if (id < 0) {
			id = Math.abs(id);
		}

		id = Long.parseLong(id.toString().substring(0, 9));

		return id;
	}
}
