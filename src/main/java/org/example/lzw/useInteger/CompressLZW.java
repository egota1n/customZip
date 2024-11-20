package org.example.lzw.useInteger;

import java.io.*;
import java.util.*;

public class CompressLZW {

    public static void compress(String inputFilePath, String compressedFilePath) throws IOException {
        byte[] inputBytes;
        try (FileInputStream inputStream = new FileInputStream(inputFilePath)) {
            inputBytes = inputStream.readAllBytes();
        }

        List<Integer> compressed = compressBytes(inputBytes);

        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(compressedFilePath))) {
            for (int code : compressed) {
                outputStream.writeInt(code);
            }
        }
    }

    private static List<Integer> compressBytes(byte[] input) {
        int dictSize = 256;
        Map<List<Byte>, Integer> dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put(Collections.singletonList((byte) i), i);
        }

        List<Byte> current = new ArrayList<>();
        List<Integer> result = new ArrayList<>();

        for (byte symbol : input) {
            List<Byte> next = new ArrayList<>(current);
            next.add(symbol);
            if (dictionary.containsKey(next)) {
                current = next;
            } else {
                result.add(dictionary.get(current));
                dictionary.put(next, dictSize++);
                current = Collections.singletonList(symbol);
            }
        }

        if (!current.isEmpty()) {
            result.add(dictionary.get(current));
        }

        return result;
    }
}