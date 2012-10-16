package com.handicapable.asltutor.helper;

public class StringHelper {

	public static String getTableFromName(String tableName) {
		return tableName.toLowerCase().replaceAll(" ", "_");
	}
}
