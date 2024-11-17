package org.example;

import org.example.gzip.CompressGZIP;
import org.example.gzip.DecompressGZIP;

public class TestStandart {
    public static void main(String[] args) {
        String[] links = {
                "/Users/egortanachev/Documents/GitHub/customZip/compress.java",
                "/Users/egortanachev/Documents/GitHub/customZip/compress.gz",
        };
        CompressGZIP.main(links);
        DecompressGZIP.main(links);
    }
}