package com.itql.module.util;

import java.io.File;

public class FileUtil {
    public static void checkFilePath(String path) {
        try {
            File file = new File(path);
            if (file.isDirectory() && !file.exists()) {
                file.mkdirs();
            } else {
                if (!file.getParentFile().exists()) {
                    file.mkdirs();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
