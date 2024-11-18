package org.example.standart;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DecompressBzip2 {
    public static void decompress(String compressedFile, String outputFile) throws IOException {
        try (
                FileInputStream fis = new FileInputStream(compressedFile);
                BZip2CompressorInputStream bzip2In = new BZip2CompressorInputStream(fis);
                FileOutputStream fos = new FileOutputStream(outputFile)
        ) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = bzip2In.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }
}