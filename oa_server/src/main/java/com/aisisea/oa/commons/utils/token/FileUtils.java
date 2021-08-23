package com.aisisea.oa.commons.utils.token;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtils {

    public static void writeStringToFile(File file, String data) throws Exception {
        writeStringToFile(file, data.getBytes());
    }
    public static void writeStringToFile(File file, byte[] data) throws Exception {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
    }

    public static String readFileToString(File file) throws Exception {
        FileInputStream fis = new FileInputStream(file);

        String readFile = "";
        byte[] data = new byte[1024];
        for (int length = 0; length != -1; length = fis.read(data))
            readFile += new String(data);

        return readFile;
    }

}
