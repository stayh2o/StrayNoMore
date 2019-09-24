package com.example.androidproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Rational;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraX;
import androidx.camera.core.FlashMode;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FragmentCam extends Fragment {
    private static final int flash_auto = 0;
    private static final int flash_on = 1;
    private static final int flash_off = 2;
    ImageButton sendButton;
    ImageButton sendServer;
    TextureView imageView;
    ImageButton flash;
    ImageView captured;
    private int flash_mode;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_cam,container,false);
        sendButton = (ImageButton) rootView.findViewById(R.id.maincamera);
        imageView = (TextureView) rootView.findViewById(R.id.imageView);
        sendServer = (ImageButton) rootView.findViewById(R.id.cam_send);
        flash = (ImageButton) rootView.findViewById(R.id.flash);
        captured = (ImageView) rootView.findViewById(R.id.capturedimg);
        flash_mode = flash_auto;
        flash.setBackgroundResource(R.drawable.flash_auto);
        return rootView;
    }


    @Override
    public  void onStart(){
        super.onStart();
        startCamera();
        sendServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Pic sent to server", Toast.LENGTH_SHORT).show();
                captured.setVisibility(View.INVISIBLE);
                startCamera();
            }
        });
    }

    private void startCamera(){
        CameraX.unbindAll();

        Rational aspectRatio = new Rational(imageView.getWidth(),imageView.getHeight());
        Size screen = new Size(imageView.getWidth(),imageView.getHeight());

        PreviewConfig pConfig = new
                PreviewConfig.Builder().setTargetAspectRatio(aspectRatio).setTargetResolution(screen).build();
        final Preview preview = new Preview(pConfig);
        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(Preview.PreviewOutput output) {
                ViewGroup parent = (ViewGroup) imageView.getParent();
                parent.removeView(imageView);
                parent.addView(imageView,0);

                imageView.setSurfaceTexture(output.getSurfaceTexture());
                updateTransform();
            }
        });

        ImageCaptureConfig imageCaptureConfig = new
                ImageCaptureConfig.Builder().setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(getActivity().getWindowManager().getDefaultDisplay().getRotation()).setFlashMode(FlashMode.AUTO).build();
        final ImageCapture imgCap = new ImageCapture(imageCaptureConfig);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp + "_";
                File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File file = null;
                try {
                    file = File.createTempFile(
                            imageFileName,  /* prefix */
                            ".jpg",         /* suffix */
                            storageDir      /* directory */
                    );
                }
                catch (IOException e){
                    e.printStackTrace();;
                }
                Log.d("writeerror",file.toString());
                imgCap.takePicture(file, new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        String msg = "Pic Captured at "+file.getAbsolutePath();
                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        captured.setImageBitmap(bitmap);
                        //captured.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
                        CameraX.unbindAll();
                    }
                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                        String msg = "Pic Captured failed : "+message;
                        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
                        if(cause!=null){
                            cause.printStackTrace();
                        }
                    }
                });
            }
        });

        CameraX.bindToLifecycle((LifecycleOwner)getActivity(),preview,imgCap);

        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (flash_mode){
                    case flash_auto:
                        flash_mode = flash_on;
                        flash.setBackgroundResource(R.drawable.flash_on);
                        imgCap.setFlashMode(FlashMode.ON);
                        break;
                    case flash_on:
                        flash_mode = flash_off;
                        flash.setBackgroundResource(R.drawable.flash_off);
                        imgCap.setFlashMode(FlashMode.OFF);
                        break;
                    case flash_off:
                    default:
                        flash_mode=flash_auto;
                        flash.setBackgroundResource(R.drawable.flash_auto);
                        imgCap.setFlashMode(FlashMode.AUTO);
                        break;

                }
            }
        });
    }

    private void updateTransform(){
        Matrix mx = new Matrix();
        float w = imageView.getWidth();
        float h = imageView.getHeight();

        float cX = w/2f;
        float cY = h/2f;

        int rotationDgr=0;
        int rotation = (int)imageView.getRotation();

        if(rotation==Surface.ROTATION_0){
            rotationDgr = 0;
        }
        else if(rotation == Surface.ROTATION_90){
            rotationDgr = 90;
        }
        else  if(rotation==Surface.ROTATION_180){
            rotationDgr = 180;
        }
        else if(rotation==Surface.ROTATION_270){
            rotationDgr = 270;
        }

        mx.postRotate((float)rotationDgr,cX,cY);
        imageView.setTransform(mx);
    }

}