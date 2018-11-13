package com.funsnap.myopengl.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.funsnap.myopengl.R;
import com.funsnap.myopengl.util.ShaderHelper;
import com.funsnap.myopengl.util.TextReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

/**
 * TODO
 * version: V1.0 <描述当前版本功能>
 * fileName: com.funsnap.myopengl.render.SingleColorRender
 * author: liuping
 * date: 2018/11/9 15:38
 */
public class SingleColorRender implements GLSurfaceView.Renderer {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private final Context mContext;

    private FloatBuffer vertexData;

    private float[] tableVertices = {
            //第一个三角形
            -0.51f, -0.51f,
            0.51f, 0.51f,
            -0.51f, 0.51f,

            //第二个三角形
            -0.51f, -0.51f,
            0.51f, -0.51f,
            0.51f, 0.51f,

            //桌面，三角形扇
            0f, 0f,
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f,
            -0.5f, -0.5f,

            //中间线
            -0.5f, 0f,
            0.5f, 0f,

            //球
            0f, -0.25f,
            0f, 0.25f
    };
    private int mVertexLocation;
    private int mFragmentLocation;

    public SingleColorRender(Context context) {
        mContext = context;
    }

    private void init(Context context) {
        //将顶点数据复制到本地内存
        vertexData = ByteBuffer.allocateDirect(tableVertices.length * 4)
                .order(ByteOrder.nativeOrder())     //不同机器上的字节序（大头，小头）不一样，这里保证跟机器的字节序一致
                .asFloatBuffer();
        vertexData.put(tableVertices);
        vertexData.position(0);

        //编译两个着色器并链接在一起
        int vertexShader = ShaderHelper.compileVertexShader(TextReader.readTextFromResource(context, R.raw.vertex_shader));
        int fragmentShader = ShaderHelper.compileFragmentShader(TextReader.readTextFromResource(context, R.raw.fragment_shader));
        int program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        //使用程序
        glUseProgram(program);

        //获得attribution和uniform的位置
        mVertexLocation = glGetAttribLocation(program, "a_Position");
        mFragmentLocation = glGetUniformLocation(program, "u_Color");

        //关联顶点数据,告诉opengl从这里去读取顶点数据
        glVertexAttribPointer(mVertexLocation, POSITION_COMPONENT_COUNT,
                GL_FLOAT, false, 0, vertexData);
        glEnableVertexAttribArray(mVertexLocation);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {   //创建时调用，GL10是遗留下来的借口，GL20采用静态的方式来调用
        //清空屏幕后默认颜色
        glClearColor(0, 0, 0, 0);
        init(mContext);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {  //尺寸变化时调用，比如横竖屏切换
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {      //单独的线程里渲染
        //清空屏幕
        glClear(GLES20.GL_COLOR_BUFFER_BIT);

        //绘制底色
        glUniform4f(mFragmentLocation, 0, 1, 1, 1);
        glDrawArrays(GL_TRIANGLES, 0, 6);

        //绘制三角形扇
        glUniform4f(mFragmentLocation, 1, 1, 1, 1);
        glDrawArrays(GL_TRIANGLE_FAN, 6, 6);

        //红色,划线
        glUniform4f(mFragmentLocation, 1, 0, 0, 1);
        glDrawArrays(GL_LINES, 12, 2);

        glUniform4f(mFragmentLocation, 0, 0, 1, 1);
        glDrawArrays(GL_POINTS, 14, 1);

        glUniform4f(mFragmentLocation, 0, 1, 0, 1);
        glDrawArrays(GL_POINTS, 15, 1);
    }
}
