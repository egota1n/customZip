package org.example.lzw.useInteger;

import java.io.*;
import java.util.*;

public class DecompressLZW {

    public static void decompress(String compressedFilePath, String decompressedFilePath) throws IOException {
        List<Integer> compressed = new ArrayList<>();
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(compressedFilePath))) {
            while (inputStream.available() > 0) {
                compressed.add(inputStream.readInt());
            }
        }

        byte[] decompressed = decompressBytes(compressed);

        try (FileOutputStream outputStream = new FileOutputStream(decompressedFilePath)) {
            outputStream.write(decompressed);
        }
    }

    private static byte[] decompressBytes(List<Integer> compressed) {
        int dictSize = 256;
        Map<Integer, List<Byte>> dictionary = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dictionary.put(i, Collections.singletonList((byte) i));
        }

        List<Byte> current = new ArrayList<>(dictionary.get(compressed.remove(0)));
        List<Byte> result = new ArrayList<>(current);

        for (int code : compressed) {
            List<Byte> entry;
            if (dictionary.containsKey(code)) {
                entry = dictionary.get(code);
            } else if (code == dictSize) {
                entry = new ArrayList<>(current);
                entry.add(current.get(0));
            } else {
                throw new IllegalArgumentException("Invalid compressed code: " + code);
            }

            result.addAll(entry);
            List<Byte> next = new ArrayList<>(current);
            next.add(entry.get(0));
            dictionary.put(dictSize++, next);
            current = entry;
        }

        byte[] decompressedBytes = new byte[result.size()];
        for (int i = 0; i < result.size(); i++) {
            decompressedBytes[i] = result.get(i);
        }

        return decompressedBytes;
    }
}
