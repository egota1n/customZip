package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

import org.example.custom.CompressCustomGZIP;
import org.example.custom.DecompressCustomGZIP;

public class TestCustom {
    public static void main(String[] args) throws IOException {
        Path input =  Path.of("dataCustom.java");
        Path output =  Path.of("dataCustom.cgz");

        CompressCustomGZIP.compress(Files.readString(input), output.toString());
        System.out.println("File Successfully compressed");

        DecompressCustomGZIP.decompress(output.toString());
        System.out.println("File Successfully decompressed");
    }
}