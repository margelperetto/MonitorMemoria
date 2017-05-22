package org.teste.memoria.command;

import java.util.ArrayList;

public class CommandParser {
    public static String[] parse(final String command) {
        final java.util.List<String> commandList = new ArrayList<String>();

        boolean startQuote = false;
        String word = "";
        for (int i = 0; i < command.length(); i++) {
            final char letter = command.charAt(i);
            switch (letter) {
            case ' ':
                if (startQuote) {
                    word += letter;
                } else {
                    if (word.length() > 0) {
                        commandList.add(word);
                    }
                    word = "";
                }
                break;
            case '\"':
                if (startQuote) {
                    commandList.add(word);
                    word = "";
                    startQuote = false;
                } else {
                    startQuote = true;
                }
                break;
            default:
                word += letter;
            }
        }
        if (word.length() > 0) {
            commandList.add(word);
        }
        final String[] comandArray = commandList.toArray(new String[0]);
        return comandArray;
    }
}
