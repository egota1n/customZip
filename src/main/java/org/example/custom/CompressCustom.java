package org.example.custom;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class CompressCustom {

    // Burrows-Wheeler Transform
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

    // Move-to-Front Encoding
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

    // Run-Length Encoding
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

            // Добавляем символ конца строки '$' для BWT
            String bwtResult = bwt(inputString + "$");

            // Применяем MTF
            int[] mtfResult = mtf(bwtResult);

            // Применяем RLE
            rle(mtfResult, fos);
        }
    }
}