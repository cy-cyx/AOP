package com.plugin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Time:2022/6/13 17:48
 * Author:
 * Description:
 */
public class FileUtil {

    public static byte[] getBytesByFile(String filePath) {
        try {
            File file = new File(filePath);
            //获取输入流
            FileInputStream fis = new FileInputStream(file);

            //新的 byte 数组输出流，缓冲区容量1024byte
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            //缓存
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            //改变为byte[]
            byte[] data = bos.toByteArray();

            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
