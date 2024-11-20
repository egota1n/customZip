package org.example;

import org.example.lzw.useInteger.CompressLZW;
import org.example.lzw.useInteger.DecompressLZW;

import java.io.File;
import java.io.IOException;

public class TestLZW {
    public static void main(String[] args) {
        String inputFile = "/Users/egortanachev/Documents/GitHub/customZip/data/input1000.txt";
        String compressedFile = "/Users/egortanachev/Documents/GitHub/customZip/data/compress/dataStandart.lzw";
        String decompressedFile = "/Users/egortanachev/Documents/GitHub/customZip/data/decompress/dataStandart.txt";

        try {
            long startCompressionTime = System.nanoTime();
            CompressLZW.compress(inputFile, compressedFile);
            long endCompressionTime = System.nanoTime();
            long compressionDuration = endCompressionTime - startCompressionTime;
            System.out.println("Compression time: " + compressionDuration / 1_000_000 + " ms");

            File compressed = new File(compressedFile);
            System.out.println("Compressed file size: " + compressed.length() + " bytes");

            long startDecompressionTime = System.nanoTime();
            DecompressLZW.decompress(compressedFile, decompressedFile);
            long endDecompressionTime = System.nanoTime();
            long decompressionDuration = endDecompressionTime - startDecompressionTime;
            System.out.println("Decompression time: " + decompressionDuration / 1_000_000 + " ms");

            File decompressed = new File(decompressedFile);
            System.out.println("Decompressed file size: " + decompressed.length() + " bytes");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}