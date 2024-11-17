package org.example.gzip;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class CompressGZIP
{
    static final String OUTPUT_FILE
            = "/Users/egortanachev/Documents/GitHub/customZip/dataGZIP.gz";
    static final String INPUT_FILE
            = "/Users/egortanachev/Documents/GitHub/customZip/dataGZIP.java";

    static void compress()
    {
        byte[] buffer = new byte[1024];
        try
        {
            GZIPOutputStream os =
                    new GZIPOutputStream(new FileOutputStream(OUTPUT_FILE));

            FileInputStream in =
                    new FileInputStream(INPUT_FILE);

            int totalSize;
            while((totalSize = in.read(buffer)) > 0 )
            {
                os.write(buffer, 0, totalSize);
            }

            in.close();
            os.finish();
            os.close();

            System.out.println("File Successfully compressed");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main (String[] args)
    {
        compress();
    }
}