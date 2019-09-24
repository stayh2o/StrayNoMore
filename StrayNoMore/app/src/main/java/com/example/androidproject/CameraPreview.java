package com.example.androidproject;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private android.hardware.Camera camera;
    private SurfaceHolder mHolder;
    public CameraPreview(Context context, Camera camera){
        super(context);
        this.camera = camera;
         mHolder = getHolder();
         mHolder.addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
        Camera.Size mSize = null;

        for(Camera.Size size:sizes){
            mSize = size;
        }

        if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){
            parameters.set("orientation","portrait");
            camera.setDisplayOrientation(90);
            parameters.setRotation(90);
        }
        else{
            parameters.set("orientation","landscape");
            camera.setDisplayOrientation(0);
            parameters.setRotation(0);
        }
        parameters.setPictureSize(mSize.width,mSize.height);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        List<String> focusModes = parameters.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE))
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        camera.setParameters(parameters);
        try {
            camera.setPreviewDisplay(mHolder);
            camera.startPreview();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void refreshCamera(Camera camera) {
        if (mHolder.getSurface() == null) {
            return;
        }
        try {
            camera.stopPreview();
        } catch (Exception e) {

        }
        setCamera(camera);
        try {
            camera.setPreviewDisplay(mHolder);
            camera.startPreview();
        } catch (Exception e) {
            Log.d(VIEW_LOG_TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        refreshCamera(camera);
        FragmentCam cam = new FragmentCam();
    }
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        //camera.stopPreview();
        //camera.release();
    }
}
