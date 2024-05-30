package com.example.morse_code.domain;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Morse {

    public static final String[] englishCode = {
            ".-", "-...", "-.-.", "-..", ".",
            "..-.", "--.", "....", "..", ".---",
            "-.-", ".-..", "--", "-.", "---",
            ".--.", "--.-", ".-.", "...", "-",
            "..-", "...-", ".--", "-..-", "-.--",
            "--..", "|"
    };

    public static final String[] russianCode = {
            ".-", "-...", ".--", "--.", "-..",
            ".", "...-", "--..", "..", ".---",
            "-.-", ".-..", "--", "-.", "---",
            ".--.", "...", "-", "..-", "..-.",
            "....", "-.-.", "---.", "----", "--.-",
            "--.--", "-.--", "-..-", "..-..", "..--",
            ".-.-"
    };

    public static final String TAG = "myLogs";

    private static String getMorseCode(char c) {
        if (97 <= c && c <= 122) {
            return englishCode[c - 97];
        }
        if (65 <= c && c <= 90) {
            return englishCode[c - 65];
        }
        if (1072 <= c && c <= 1103) {
            if (c <= 1098) {
                return russianCode[c - 1072];
            }
            if (c == 'ь') {
                return russianCode[26];
            }
            return russianCode[c - 1073];
        }
        if (1040 <= c && c <= 1071) {
            if (c <= 1066) {
                return russianCode[c - 1040];
            }
            if (c == 'Ь') {
                return russianCode[26];
            }
            return russianCode[c - 1041];
        }
        if (c == 'ё' || c == 'Ё') {
            return russianCode[5];
        }
        return null;
    }

    public static String wordToMorse(String text) {
        String res = "";
        for (int i = 0; i < text.length(); i++) {
            String code = getMorseCode(text.charAt(i));
            if (code == null) {
                return String.format("Error symbol '%c' at position %s", text.charAt(i), i);
            }
            res += (code + ' ');
        }
        return res.strip();
    }

    public static String textToMorse(String text) {
        String res = "";
        for (String word : text.split(" ")) {
            res += (wordToMorse(word) + "  ");
        }
        return res.strip();
    }

    public static char morseToChar(String code, String mode) throws Exception {
        Log.d(TAG, "morseToChar: " + code + " " + mode);
        if (mode.equals("russian")) {
            Log.d(TAG, "morseToChar: Russian");
            int index = Arrays.asList(russianCode).indexOf(code);
            if (index == -1) {
                throw new Exception();
            }
            return (char) (1072 + index);
        }
        if (mode.equals("english")) {
            Log.d(TAG, "morseToChar: English");
            int index = Arrays.asList(englishCode).indexOf(code);
            if (index == -1) {
                throw new Exception();
            }
            return (char) (97 + index);
        }
        return '?';
    }

    public static String morseToWord(String text, String mode) throws Exception {
        String res = "";
        List<String> symbols = Arrays.asList(text.split(" "));
        for (int i = 0; i < symbols.size(); i++) {
            Log.d(TAG, symbols.get(i));
            try {
                res += morseToChar(symbols.get(i), mode);
            } catch (Exception e) {
                throw new Exception(String.valueOf(i + 1));
            }
        }
        return res;
    }

    public static String morseToText(String text, String mode) throws Exception {
        String res = "";
        List<String> words = Arrays.asList(text.split("   "));
        Log.d(TAG, words.toString());
        for (int i = 0; i < words.size(); i++) {
            try {
                res += morseToWord(words.get(i), mode) + " ";
            } catch (Exception e) {
                throw new Exception(String.valueOf(i + 1) + " " + e.getMessage());
            }
        }
        return res;
    }

    public static int checkMorse(String code) {
        for (int i = 0; i < code.length(); i++) {
            if (code.charAt(i) != '.' && code.charAt(i) != '-' && code.charAt(i) != ' ') {
                return i;
            }
        }
        return -1;
    }
}
