package com.funsnap.myopengl.ui;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.funsnap.myopengl.render.Colours3DRender;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mGlSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGlSurfaceView = new GLSurfaceView(this);
        mGlSurfaceView.setEGLContextClientVersion(2);
        mGlSurfaceView.setRenderer(new Colours3DRender(this));
        setContentView(mGlSurfaceView);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mGlSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mGlSurfaceView.onPause();
    }
}
