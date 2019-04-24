package com.neuroid.etickets;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONObject;

import java.io.IOException;

public class Scanning extends AppCompatActivity {
  SurfaceView surfaceView;
  CameraSource cameraSource;
  TextView textView;
  BarcodeDetector barcodeDetector;
  int i =0;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scanning);

    surfaceView = findViewById(R.id.cameraPreview);
    textView = findViewById(R.id.txtvew);
    barcodeDetector = new BarcodeDetector.Builder(this)
      .setBarcodeFormats(Barcode.QR_CODE)
      .build();
    cameraSource = new CameraSource.Builder(this, barcodeDetector)
      .setRequestedPreviewSize(640, 480)
      .build();

    surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
      @Override
      public void surfaceCreated(SurfaceHolder holder) {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
          return;
        }

        try{
          cameraSource.start(holder);

        }catch (IOException e){
          e.printStackTrace();
        }
      }

      @Override
      public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

      }

      @Override
      public void surfaceDestroyed(SurfaceHolder holder) {
        cameraSource.stop();
      }
    });

    barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
      @Override
      public void release() {

      }

      @Override
      public void receiveDetections(Detector.Detections<Barcode> detections) {

        final SparseArray<Barcode> qrcode= detections.getDetectedItems();

        if(qrcode.size() !=0){
          textView.post(new Runnable() {
            @Override
            public void run() {
              textView.setText(qrcode.valueAt(0).displayValue);
              SharedPreferences pref = getApplicationContext().getSharedPreferences("data", 0);
              SharedPreferences.Editor editor = pref.edit();
              editor.clear();
              editor.putString("obj", qrcode.valueAt(0).displayValue);
              editor.commit();
              try {
                JSONObject obj = new JSONObject(qrcode.valueAt(0).displayValue);
                Log.d("My App suceess", obj.toString());

              } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + qrcode.valueAt(0).displayValue + "\"");
              }
              i = i+1;
              if(i==1) {
                finish();
                startActivity(new Intent(Scanning.this, Payout.class));
              }
            }
          });
        }
      }
    });
  }
}
