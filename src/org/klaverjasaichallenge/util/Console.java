package org.klaverjasaichallenge.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * 
 * @author Frank Versnel
 */
public class Console {
	
	private Scanner input;
	private PrintStream output;
	
	public Console(InputStream inputStream, PrintStream output) {
		input = new Scanner(inputStream);
		this.output = output;
	}
	
	public void printf(String text, Object... args) {
		output.printf(text, args);
	}
	
	public String readLine(String prefix, Object... args) {
		printf(prefix, args);
		return input.next();
	}

}
