package com.funsnap.myopengl.util;


import android.opengl.GLES20;
import android.util.Log;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;

/**
 * TODO
 * version: V1.0 <着色器编译>
 * fileName: com.funsnap.myopengl.util.ShaderHelper
 * author: liuping
 * date: 2018/11/10 9:45
 */
public class ShaderHelper {

    /**
     * 创建定点着色器
     *
     * @param code
     * @return
     */
    public static int compileVertexShader(String code) {
        //创建着色器对象
        int shader = glCreateShader(GL_VERTEX_SHADER);
        if (shader != 0) {
            //填充代码
            glShaderSource(shader, code);
            //编译
            glCompileShader(shader);
            //判断编译是否成功
            int[] compileStatus = new int[1];
            glGetShaderiv(shader, GL_COMPILE_STATUS, compileStatus, 0);
            if (compileStatus[0] == 0) {
                glDeleteShader(shader);
                Log.d("liuping", ":" + "顶点着色器编译失败");
                shader = 0;
            }
        }

        return shader;
    }

    /**
     * 创建片元着色器
     *
     * @param code
     * @return
     */
    public static int compileFragmentShader(String code) {
        int shader = glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        if (shader != 0) {
            glShaderSource(shader, code);
            glCompileShader(shader);

            int[] status = new int[1];
            glGetShaderiv(shader, GL_COMPILE_STATUS, status, 0);
            if (status[0] == 0) {
                glDeleteShader(shader);
                Log.d("liuping", ":" + "片元着色器编译失败");
                shader = 0;
            }
        }

        return shader;
    }


    /**
     * 根据定点着色器和片元着色器，创建opengl程序对象，
     * 定点着色器和片元着色器是一起工作的
     *
     * @param vertexShader
     * @param fragmentShader
     * @return
     */
    public static int linkProgram(int vertexShader, int fragmentShader) {
        int program = glCreateProgram();
        if (program != 0) {
            glAttachShader(program, vertexShader);
            glAttachShader(program, fragmentShader);
            glLinkProgram(program);

            int[] status = new int[1];
            GLES20.glGetProgramiv(program, GL_LINK_STATUS, status, 0);
            if (status[0] == 0) {
                glDeleteProgram(program);
                program = 0;
                Log.d("liuping", ":" + "着色器链接失败");
            }
        }

        return program;
    }
}
