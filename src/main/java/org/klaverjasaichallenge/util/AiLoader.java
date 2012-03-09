package org.klaverjasaichallenge.util;

import org.klaverjasaichallenge.shared.KlaverJasAI;

public class AiLoader {
	private static final String AI_PACKAGE_NAME = "org.klaverjasaichallenge.ai.";
	private static final ClassLoader aiClassLoader = KlaverJasAI.class.getClassLoader();

	public static KlaverJasAI loadAi(final String aiClassName) {
		try {
			@SuppressWarnings("unchecked")
			final Class<KlaverJasAI> ai = (Class<KlaverJasAI>) aiClassLoader.loadClass(AI_PACKAGE_NAME + aiClassName);
			return ai.newInstance();
		} catch (InstantiationException e) {
			throw new AiLoaderException("Unable to instantiate AI " + aiClassName, e);
		} catch (IllegalAccessException e) {
			throw new AiLoaderException("Illegal access to " + aiClassName, e);
		} catch (ClassNotFoundException e) {
			throw new AiLoaderException("AI " + aiClassName + " not found on classpath", e);
		}
	}
}
