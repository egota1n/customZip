package org.example.gzip;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class DecompressGZIP {
    public static void decompress(String compressedFilePath, String decompressedFilePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(compressedFilePath);
             GZIPInputStream gzipIS = new GZIPInputStream(fis);
             FileOutputStream fos = new FileOutputStream(decompressedFilePath)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = gzipIS.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        }
    }
}