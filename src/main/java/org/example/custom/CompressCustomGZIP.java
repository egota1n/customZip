package org.example.custom;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Deflater;

public class CompressCustomGZIP {
    public static void compress(String input, String outputFile) throws IOException {
        byte[] inputData = input.getBytes("UTF-8");
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(new byte[] {'T', 'E', 'A', 'A'});

            Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
            deflater.setInput(inputData);
            deflater.finish();

            byte[] buffer = new byte[1024];
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                fos.write(buffer, 0, count);
            }
            deflater.end();

            CRC32 crc = new CRC32();
            crc.update(inputData);
            long checksum = crc.getValue();

            fos.write((byte) (checksum >> 24));
            fos.write((byte) (checksum >> 16));
            fos.write((byte) (checksum >> 8));
            fos.write((byte) (checksum));
        }
    }
}
