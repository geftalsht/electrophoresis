package org.gefsu;

import org.gefsu.http.HttpResponse;

import java.io.*;

public class Responder {
    public static void writeResponseToOutput(
        OutputStream outputStream, HttpResponse response)
    {
        try {
            outputStream.write(response.metaToBytes());
            if (response.getUri() != null) {
                final var fis = new FileInputStream(new File(response.getUri()));
                writeInStreamToOutStream(fis, outputStream);
            }
        } catch (IOException e) {
            System.out.println("Error writing to the outputStream");
        }
    }

    private static void writeInStreamToOutStream(InputStream in, OutputStream out)
        throws IOException
    {
        byte[] buf = new byte[1024];
        int len;

        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
    }
}
