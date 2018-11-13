package com.funsnap.myopengl.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * TODO
 * version: V1.0 <描述当前版本功能>
 * fileName: com.funsnap.myopengl.util.TextReader
 * author: liuping
 * date: 2018/11/9 17:16
 */
public class TextReader {

    public static String readTextFromResource(Context context, int resourceID) {
        StringBuilder builder = new StringBuilder();

        InputStream inputStream = context.getResources().openRawResource(resourceID);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String s = null;
        try {
            while ((s = bufferedReader.readLine()) != null) {
                builder.append(s);
                builder.append("\n");
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
}
