package org.example.bzip2;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

public class CompressBzip2 {
    public static void compress(String inputFile, String compressedFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(compressedFile);
             BZip2CompressorOutputStream bzip2Out = new BZip2CompressorOutputStream(fos)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bzip2Out.write(buffer, 0, bytesRead);
            }
        }
    }
}