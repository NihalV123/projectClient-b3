package com.nihalvaidya123.foodcrunch;

import android.*;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class qrMainActivity extends AppCompatActivity  implements ZXingScannerView.ResultHandler{

    private ZXingScannerView scannerView;
    private static final int REQUEST_CAMERA=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                Toast.makeText(qrMainActivity.this,"Permission granted!!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                requestPermissions();
            }
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,new String[]{CAMERA},REQUEST_CAMERA);
    }

    public void onRequestPermissionsResult(int requestCode,String permission[],int grantResults[])
    {
       switch(requestCode)
       {
           case REQUEST_CAMERA:
               if(grantResults.length > 0)
               {
                   boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                   if(cameraAccepted)
                   {
                       Toast.makeText(qrMainActivity.this,"Permission granted!!",Toast.LENGTH_SHORT).show();
                   }
                   else
                   {
                       Toast.makeText(qrMainActivity.this,"Permission DENIED!!",Toast.LENGTH_SHORT).show();
                       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                       {
                           if(shouldShowRequestPermissionRationale(CAMERA))
                           {
                               displayAlertMessage("you need to give access to all permissions", new DialogInterface.OnClickListener(){
                                   @Override
                                   public void onClick(DialogInterface dialogInterface,int i) {
                                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                       {
                                           requestPermissions(new String[]{CAMERA},REQUEST_CAMERA);
                                       }
                                   }
                                       }


                               );

                                return;
                           }
                       }
                   }
               }
               break;
       }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                if(scannerView == null)
                {
                    scannerView= new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
            else
            {

            }
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        scannerView.stopCamera();
    }


    private void displayAlertMessage(String message,DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(qrMainActivity.this).setMessage(message).setPositiveButton("OK",listener)
                .setNegativeButton("OK",listener).create().show();
    }

    private boolean checkPermission() {
        return (ContextCompat.checkSelfPermission(qrMainActivity.this, CAMERA) == PackageManager.PERMISSION_GRANTED);
    }


    @Override
    public void handleResult(Result result) {
        final String scanResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ScanResult");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(qrMainActivity.this);
            }
        });
        builder.setNegativeButton("visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
                startActivity(intent);
            }
        });
        builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();

    }
}