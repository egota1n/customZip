package org.example.custom;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class DecompressCustom {
    // Reverse RLE
    public static int[] reverseRLE(FileInputStream fis) throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        int value;
        while ((value = fis.read()) != -1) {
            int count = fis.read();
            for (int i = 0; i < count; i++) {
                list.add(value);
            }
        }
        return list.stream().mapToInt(i -> i).toArray();
    }

    // Reverse MTF
    public static String reverseMTF(int[] input) {
        ArrayList<Character> alphabet = new ArrayList<>();
        for (char c = 0; c < 256; c++) {
            alphabet.add(c);
        }
        StringBuilder result = new StringBuilder();
        for (int index : input) {
            char c = alphabet.get(index);
            result.append(c);
            alphabet.remove(index);
            alphabet.add(0, c);
        }
        return result.toString();
    }

    // Reverse BWT
    public static String reverseBWT(String input) {
        int n = input.length();

        ArrayList<String> table = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            table.add("");
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                table.set(j, input.charAt(j) + table.get(j));
            }
            Collections.sort(table);
        }

        for (String row : table) {
            if (row.endsWith("$")) {
                return row.substring(0, n - 1);
            }
        }
        throw new IllegalArgumentException("Invalid BWT input: no ending symbol '$'");
    }

    public static void decompress(String compressedFile, String outputFile) throws IOException {
        try (
                FileInputStream fis = new FileInputStream(compressedFile);
                FileOutputStream fos = new FileOutputStream(outputFile)
        ) {
            // Reverse RLE
            int[] rleResult = reverseRLE(fis);

            // Reverse MTF
            String mtfResult = reverseMTF(rleResult);

            // Reverse BWT
            String bwtResult = reverseBWT(mtfResult);

            fos.write(bwtResult.getBytes());
        }
    }
}