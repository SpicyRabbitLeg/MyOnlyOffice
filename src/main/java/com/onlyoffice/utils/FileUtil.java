package com.onlyoffice.utils;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {

    public static void writeFile(InputStream inputStream, OutputStream outputStream) {
        try {
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(inputStream);
            close(outputStream);
        }
    }


    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
