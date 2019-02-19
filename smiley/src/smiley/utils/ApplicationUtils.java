package smiley.utils;

import java.util.HashMap;
import java.util.Map;

public class ApplicationUtils {
	
	
	private static Map<Object, Object> attributes = new HashMap<>();
	
	
	public static void add(String key, Object object) {
		attributes.put(key, object);
	}
	
	public static Object getObject(String key) {
		return attributes.get(key);
	}
	
	public static void remove(String key) {
		attributes.remove(key);
	}
	
	public static Object take(String key) {
		Object object = attributes.get(key);
		if (object != null) {
			attributes.remove(key);
		}
		return object;
	}

}
