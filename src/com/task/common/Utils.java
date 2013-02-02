package com.task.common;

import java.sql.Timestamp;

public class Utils {
	
	public static Timestamp convertToTimestamp(String s) {
		Timestamp timestamp = Timestamp.valueOf(s);
		return timestamp;
	}
}
