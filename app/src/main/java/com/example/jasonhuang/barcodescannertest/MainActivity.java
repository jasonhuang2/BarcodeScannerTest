package com.example.jasonhuang.barcodescannertest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private ZXingScannerView zXingScannerView;
    Button scanButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        scanButton = (Button) findViewById(R.id.scanButton);
        //Button open scan camera
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        });
    }

    public void scan(View view){
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                zXingScannerView =new ZXingScannerView(getApplicationContext());
                setContentView(zXingScannerView);
                zXingScannerView.setResultHandler(this);
                zXingScannerView.startCamera();
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();

            } else {

//                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }


    @Override
    public void handleResult(Result result) {
        Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_SHORT).show();
        zXingScannerView.resumeCameraPreview(this);

    }

}
