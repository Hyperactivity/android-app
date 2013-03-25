package com.hyperactivity.android_app.core;

public class Utils {
	public static boolean sameId(RemoteObject obj1, RemoteObject obj2) {
		if(obj1.getId().equals(obj2.getId())) {
			return true;
		}
		
		return false;
	}
}
