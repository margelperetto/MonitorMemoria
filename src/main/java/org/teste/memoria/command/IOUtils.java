package org.teste.memoria.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IOUtils {

	public static String readLine(final Process process) throws Exception{
		StringBuilder ret = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

		String line = "";
		while ( (line = br.readLine()) != null) {
			ret.append(line);
			ret.append("\n");
		}

		return ret.toString();
	}

	public static Process executeProcess(String cmd, final List<String> args) {
		try {
			if (args != null && args.size() > 0) {
				for (final String arg : args) {
					cmd += " " + arg;
				}
			}
			return Runtime.getRuntime().exec(parse(cmd));
		} catch (final IOException e) {
			return null;
		}
	}

	private static String[] parse(final String command) {
		List<String> commandList = new ArrayList<String>();
		boolean startQuote = false;
		String word = "";
		for (int i = 0; i < command.length(); i++) {
			char letter = command.charAt(i);
			switch (letter) {
			case ' ': {
				if (startQuote) {
					word += letter;
				} else {
					if (word.length() > 0) {
						commandList.add(word);
					}
					word = "";
				}
				break;
			} case '\"': {
				if (startQuote) {
					commandList.add(word);
					word = "";
					startQuote = false;
				} else {
					startQuote = true;
				}
				break;
			} default:
				word += letter;
			}
		}
		if (word.length() > 0) {
			commandList.add(word);
		}
		return commandList.toArray(new String[0]);
	}

}
