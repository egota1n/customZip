package org.example.lzw.useShort;

import java.io.*;
import java.util.*;

public class DecompressLZW {

    public static void decompress(String compressedFilePath, String decompressedFilePath) throws IOException {
        List<Short> compressed = new ArrayList<>();
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(compressedFilePath))) {
            while (inputStream.available() > 0) {
                compressed.add(inputStream.readShort()); // Чтение 2 байт вместо 4
            }
        }

        byte[] decompressed = decompressBytes(compressed);

        try (FileOutputStream outputStream = new FileOutputStream(decompressedFilePath)) {
            outputStream.write(decompressed);
        }
    }

    private static byte[] decompressBytes(List<Short> compressed) {
        short dictSize = 256;
        Map<Short, List<Byte>> dictionary = new HashMap<>();
        for (short i = 0; i < 256; i++) {
            dictionary.put(i, Collections.singletonList((byte) i));
        }

        List<Byte> current = new ArrayList<>(dictionary.get(compressed.remove(0)));
        List<Byte> result = new ArrayList<>(current);

        for (short code : compressed) {
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
            if (dictSize < Short.MAX_VALUE) { // Ограничиваем размер словаря
                dictionary.put(dictSize++, next);
            }
            current = entry;
        }

        byte[] decompressedBytes = new byte[result.size()];
        for (int i = 0; i < result.size(); i++) {
            decompressedBytes[i] = result.get(i);
        }

        return decompressedBytes;
    }
}