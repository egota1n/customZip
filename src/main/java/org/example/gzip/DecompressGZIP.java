package org.example.gzip;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class DecompressGZIP {
    static final String INPUT_FILE
            = "/Users/egortanachev/Documents/GitHub/customZip/dataGZIP.gz";
    static final String OUTPUT_FILE
            = "/Users/egortanachev/Documents/GitHub/customZip/dataGZIP.java";

    static void decompress()
    {
        byte[] buffer = new byte[1024];
        try
        {
            GZIPInputStream is =
                    new GZIPInputStream(new FileInputStream(INPUT_FILE));

            FileOutputStream out =
                    new FileOutputStream(OUTPUT_FILE);

            int totalSize;
            while((totalSize = is.read(buffer)) > 0 )
            {
                out.write(buffer, 0, totalSize);
            }

            out.close();
            is.close();

            System.out.println("File Successfully decompressed");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main (String[] args)
    {
        decompress();
    }
}