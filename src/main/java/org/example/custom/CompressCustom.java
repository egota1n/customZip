package org.example.custom;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class CompressCustom {

    // BWT
    public static String bwt(String input) {
        int n = input.length();
        ArrayList<String> rotations = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            rotations.add(input.substring(i) + input.substring(0, i));
        }
        Collections.sort(rotations);
        StringBuilder result = new StringBuilder();
        for (String rotation : rotations) {
            result.append(rotation.charAt(n - 1));
        }
        return result.toString();
    }

    // MTF
    public static int[] mtf(String input) {
        ArrayList<Character> alphabet = new ArrayList<>();
        for (char c = 0; c < 256; c++) {
            alphabet.add(c);
        }
        int[] result = new int[input.length()];
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int index = alphabet.indexOf(c);
            result[i] = index;
            alphabet.remove(index);
            alphabet.add(0, c);
        }
        return result;
    }

    // RLE
    public static void rle(int[] input, FileOutputStream fos) throws IOException {
        int count = 1;
        for (int i = 1; i < input.length; i++) {
            if (input[i] == input[i - 1]) {
                count++;
            } else {
                fos.write(input[i - 1]);
                fos.write(count);
                count = 1;
            }
        }
        fos.write(input[input.length - 1]);
        fos.write(count);
    }

    public static void compress(String inputFile, String compressedFile) throws IOException {
        try (
                FileInputStream fis = new FileInputStream(inputFile);
                FileOutputStream fos = new FileOutputStream(compressedFile)
        ) {
            byte[] inputData = fis.readAllBytes();
            String inputString = new String(inputData);

            // BWT
            String bwtResult = bwt(inputString + "$");

            // MTF
            int[] mtfResult = mtf(bwtResult);

            // RLE
            rle(mtfResult, fos);
        }
    }
}