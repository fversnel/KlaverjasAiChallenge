package org.klaverjasaichallenge.util;

public class AiLoaderException extends RuntimeException {
	private static final long serialVersionUID = -1569799736209874419L;

	public AiLoaderException(String message, Exception exception) {
		super(message, exception);
	}
}

