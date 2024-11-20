package org.example.lzw.useShort;

import java.io.*;
import java.util.*;

public class CompressLZW {

    public static void compress(String inputFilePath, String compressedFilePath) throws IOException {
        byte[] inputBytes;
        try (FileInputStream inputStream = new FileInputStream(inputFilePath)) {
            inputBytes = inputStream.readAllBytes();
        }

        List<Short> compressed = compressBytes(inputBytes);

        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(compressedFilePath))) {
            for (short code : compressed) {
                outputStream.writeShort(code);
            }
        }
    }

    private static List<Short> compressBytes(byte[] input) {
        short dictSize = 256;
        Map<List<Byte>, Short> dictionary = new HashMap<>();
        for (short i = 0; i < 256; i++) {
            dictionary.put(Collections.singletonList((byte) i), i);
        }

        List<Byte> current = new ArrayList<>();

        List<Short> result = new ArrayList<>();

        for (byte symbol : input) {
            List<Byte> next = new ArrayList<>(current);
            next.add(symbol);
            if (dictionary.containsKey(next)) {
                current = next;
            } else {
                result.add(dictionary.get(current));
                if (dictSize < Short.MAX_VALUE) {
                    dictionary.put(next, dictSize++);
                }
                current = Collections.singletonList(symbol);
            }
        }

        if (!current.isEmpty()) {
            result.add(dictionary.get(current));
        }

        return result;
    }
}