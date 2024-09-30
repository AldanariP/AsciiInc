package org.aldanari.asciiinc;

import org.aldanari.asciiinc.exceptions.FaildedToLoadConfigException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class Config {
	private final Properties properties;
	private static Config instance = null;

	private Config() {
		String path = Objects.requireNonNull(getClass().getResource("config.properties")).getPath();
		this.properties = new Properties();
		try (FileInputStream f = new FileInputStream(path)) {
			this.properties.load(f);
		} catch (IOException e) {
			throw new FaildedToLoadConfigException(e);
		}
	}

	public static Config getConfig() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	public int getFrameRate() {
		return Integer.parseInt(this.properties.getProperty("frameRate"));
	}

	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}

	public void setProperty(String key, String value) {
		this.properties.setProperty(key, value);
	}
}
