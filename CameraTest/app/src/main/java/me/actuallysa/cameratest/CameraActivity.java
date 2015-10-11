package me.actuallysa.cameratest;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;
import java.io.File;
import java.text.SimpleDateFormat;


public class CameraActivity extends AppCompatActivity {
    private Uri fileUri;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;





    private static File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        return mediaFile;
    }

    private static Uri getOutputMediaFileUri(){
        File file = getOutputMediaFile();
        return Uri.fromFile(file);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            // SD card must be mounted and accessible
            Log.i(getPackageName(), "Media is Writable");

            Log.i(getPackageName(), "Activity Initialized");
        } else {
            Log.e(getPackageName(), "Media is not writeable");
        }

        setContentView(R.layout.activity_camera);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);

        if(isSDPresent)
        {
            fileUri = getOutputMediaFileUri();
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
        else
        {
            Log.d("Camera", "Fuck");
        }


    }
}
