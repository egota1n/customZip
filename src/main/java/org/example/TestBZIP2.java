package org.example;

import org.example.bzip2.CompressBzip2;
import org.example.bzip2.DecompressBzip2;

import java.io.File;
import java.io.IOException;

public class TestBZIP2 {
    public static void main(String[] args) {
        String inputFile = "/Users/egortanachev/Documents/GitHub/customZip/data/input1000.txt";
        String compressedFile = "/Users/egortanachev/Documents/GitHub/customZip/data/compress/dataBZIP2.bz2";
        String decompressedFile = "/Users/egortanachev/Documents/GitHub/customZip/data/decompress/dataBZIP2.txt";

        try {
            long startCompressionTime = System.nanoTime();
            CompressBzip2.compress(inputFile, compressedFile);
            long endCompressionTime = System.nanoTime();
            long compressionDuration = endCompressionTime - startCompressionTime;
            System.out.println("Compression time: " + compressionDuration / 1_000_000 + " ms");

            File compressed = new File(compressedFile);
            System.out.println("Compression size: " + compressed.length() + " bytes");

            long startDecompressionTime = System.nanoTime();
            DecompressBzip2.decompress(compressedFile, decompressedFile);
            long endDecompressionTime = System.nanoTime();
            long decompressionDuration = endDecompressionTime - startDecompressionTime;
            System.out.println("Decompression time: " + decompressionDuration / 1_000_000 + " ms");

            File decompressed = new File(decompressedFile);
            System.out.println("Decompression size: " + decompressed.length() + " bytes");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}