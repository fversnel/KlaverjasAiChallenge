package org.klaverjasaichallenge.util;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author Jason Fagan
 * @author Frank Versnel
 */
public class Console {
	
	private Scanner input;
	private PrintStream output;
	
	public Console(InputStream inputStream, PrintStream output) {
		this.input = new Scanner(inputStream);
		this.output = output;
	}
	
	public void printf(String text, Object... args) {
		this.output.printf(text, args);
	}
	
	public String readLine(String prefix, Object... args) {
		printf(prefix, args);
		return this.input.next();
	}
	
    /**
     * Simple Yes/No prompt
     *
     * @param prompt - String to prompt user with
     *
     * @return true if answered yes, else false
     */
    public boolean yesNoPrompt(String prompt) {
        String result = multiPrompt(String.format("%s (y/n)? ", prompt), "(Y|y|N|n)");
        return result.equalsIgnoreCase("y");
    }

    /**
     * Multi-choice prompt
     *
     * @param prompt - String to prompt user with
     *
     * @param regex - Regular expression of choices e.g. ([1-5]|0) allows 1 through 5 and 0
     *
     * @return char of the matched input
     */
    public String multiPrompt(String prompt, String regex) {
        this.printf(prompt);
        Pattern pattern = Pattern.compile(regex);
        while (true) {
            if (this.input.hasNext(pattern)) {
                return this.input.next(pattern);
            }
        }
    }

}
