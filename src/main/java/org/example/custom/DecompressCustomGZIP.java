package org.example.custom;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.zip.CRC32;
import java.util.zip.Inflater;
import java.util.zip.DataFormatException;

public class DecompressCustomGZIP {
    public static String decompress(String inputFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputFile)) {
            if (fis.read() != 'T' || fis.read() != 'E' || fis.read() != 'A' || fis.read() != 'A') {
                throw new IllegalArgumentException("Invalid file format");
            }

            byte[] compressedData = fis.readAllBytes();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Inflater inflater = new Inflater();
            inflater.setInput(compressedData, 0, compressedData.length - 4);

            try {
                while (!inflater.finished()) {
                    int count = inflater.inflate(buffer);
                    outputStream.write(buffer, 0, count);
                }
            } catch (DataFormatException e) {
                throw new IOException("Data format error during decompression", e);
            } finally {
                inflater.end();
            }

            byte[] decompressedData = outputStream.toByteArray();
            CRC32 crc = new CRC32();
            crc.update(decompressedData);

            long calculatedChecksum = crc.getValue();
            long originalChecksum = ((compressedData[compressedData.length - 4] & 0xFFL) << 24)
                    | ((compressedData[compressedData.length - 3] & 0xFFL) << 16)
                    | ((compressedData[compressedData.length - 2] & 0xFFL) << 8)
                    | ((compressedData[compressedData.length - 1] & 0xFFL));

            if (calculatedChecksum != originalChecksum) {
                throw new IllegalArgumentException("Checksum mismatch");
            }

            return new String(decompressedData, "UTF-8");
        }
    }
}