package com.xuhuawei.miwificutdemo.utils;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class FileUtils {

    public static void writeSD(String content, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(content.getBytes("utf-8"));
            fos.close();
        } catch (Exception e) {

        }
    }   public static void writeSD2(String content, File file) {
        try {



            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(content.getBytes("utf-8"));
            fos.close();
        } catch (Exception e) {

        }
    }

    public static File getRootFile(String name) {
        File file = new File(android.os.Environment.getExternalStorageDirectory(), name);
        if (file.exists()) {
            file.delete();
        }
        return file;
    }
}
