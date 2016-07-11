package com.example.souvik.flashlight;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    Camera camera;
    Camera.Parameters parameters;
    boolean isflash = false;
    boolean isOn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageButton = (ImageButton) findViewById(R.id.btnon);
        if (getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH))
        {
            camera = Camera.open();
            parameters = camera.getParameters();
            isflash = true;
        }

        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
                public void onClick(View v) {
                if (isflash){
                    if (!isOn){
                        imageButton.setImageResource(R.drawable.onscreen);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        camera.setParameters(parameters);
                        camera.startPreview();
                        isOn=true;
                    }
                    else {
                        imageButton.setImageResource(R.drawable.offscreen);
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                        camera.setParameters(parameters);
                        camera.stopPreview();
                        isOn=false;
                    }

                }
                else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error!");
                    builder.setMessage("Flash is not available on the device");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
                       @Override
                        public void onClick(DialogInterface dialog,int which){
                           dialog.dismiss();
                           finish();
                       }
                    });
                    AlertDialog alertDialog=builder.create();
                    alertDialog.show();
                }
        }
        });
}
    @Override
    protected void onStop(){
        super.onStop();
        if (camera!=null){
            camera.release();
            camera=null;
        }
    }

}
