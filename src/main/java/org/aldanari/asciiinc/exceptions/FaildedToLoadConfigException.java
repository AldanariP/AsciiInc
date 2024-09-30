package org.aldanari.asciiinc.exceptions;

import java.io.IOException;

public class FaildedToLoadConfigException extends RuntimeException {
	public FaildedToLoadConfigException(IOException cause) {
		super(cause);
	}
}
