package com.bjpygh.gzh.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * properties帮助类 默认加载config.properties
 */
public class PropertyUtils {


	private static final String config = "conf.properties";

	private static Map<String, String> config_map = new HashMap<String, String>();

	static {
		load(config);
	}

	public static String getProperty(String key) {
		if (key==null||key=="") {
			return null;
		}
		return config_map.get(key);
	}

	private static void load(String name) {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
		Properties p = new Properties();
		try {
			p.load(is);
			if (config.equals(name)) {

				for (Map.Entry<Object,Object> e : p.entrySet()) {
					config_map.put((String) e.getKey(), (String) e.getValue());
				}
			}

		} catch (IOException e) {

		}
	}

}
