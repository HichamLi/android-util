package com.itql.module.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JsonUtil {
	private static final Gson GSON = new Gson();

	public static String beanToJson(Object o) {
		return GSON.toJson(o);
	}

	public static Map<String, Object> beanToMap(Object o) {
		return jsonToMap(beanToJson(o));
	}

	public static <T> List<T> jsonToList(String s, TypeToken<T> typeToken) {
		return jsonToBean(s, typeToken.getType());
	}

	public static Map<String, Object> jsonToMap(String s) {
		return jsonToBean(s, new TypeToken<TreeMap<String, Object>>() {}.getType());
	}

	public static <T> T jsonToBean(String s, Class<T> tClass) {
		return GSON.fromJson(s, tClass);
	}

	public static <T> T mapToBean(Map<String, Object> map, Class<T> tClass) {
		return jsonToBean(beanToJson(map), tClass);
	}


	private static <T> T jsonToBean(String s, TypeToken<T> typeToken) {
		return jsonToBean(s, typeToken.getType());
	}

	private static <T> T jsonToBean(String s, Type type) {
		return GSON.fromJson(s, type);
	}
}
